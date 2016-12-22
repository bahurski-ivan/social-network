package ru.bahurski.socialnetwork.core.util.encoder;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 22/12/2016.
 */
public class JsonContentEncoder {
    private final Type[] types;
    private final Gson gson = new Gson();

    public JsonContentEncoder(Type... types) {
        this.types = types;
    }

    public String encode(Object... values) {
        JsonArray jsonArray = new JsonArray();

        for (int i = 0; i < values.length; ++i) {
            JsonElement elementJson;
            Object object = values[i];
            final Type type = types[i];

            if (object != null) {
                Class<?> clazz = object.getClass();
                if (clazz != type)
                    throw new IllegalArgumentException(String.format("encoding error - expected class: %s, found: %s",
                            type.getTypeName(), clazz.getName()));
            }

            elementJson = gson.toJsonTree(new Argument(type.getTypeName(), object));

            jsonArray.add(elementJson);
        }

        return jsonArray.toString();
    }

    public Object[] decode(String values) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        List<Object> result = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(values);

        if (!jsonElement.isJsonArray())
            return null;

        JsonArray jsonArray = jsonElement.getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); ++i) {
            JsonElement element = jsonArray.get(i);

            if (element.isJsonObject()) {
                JsonObject jsonObject = element.getAsJsonObject();

                if (jsonObject.has("typeName") && jsonObject.has("objectValue")) {
                    String typeName = jsonObject.get("typeName").getAsString();
                    JsonElement objectValue = jsonObject.get("objectValue");

                    Class<?> targetClass;

                    try {
                        targetClass = classLoader.loadClass(typeName);
                        if (targetClass != types[i])
                            throw new ClassNotFoundException();
                    } catch (ClassNotFoundException e) {
                        return null;
                    }

                    if (objectValue == null)
                        result.add(null);
                    else
                        result.add(gson.fromJson(objectValue, targetClass));
                } else
                    return null;
            }
        }

        return result.toArray();
    }

    private static class Argument {
        private final String typeName;
        private final Object objectValue;

        Argument(String typeName, Object objectValue) {
            this.typeName = typeName;
            this.objectValue = objectValue;
        }
    }
}
