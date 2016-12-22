package ru.bahurski.socialnetwork.core.util.converter.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import ru.bahurski.socialnetwork.core.util.encoder.JsonContentEncoder;

/**
 * Created by Ivan on 22/12/2016.
 */
public class FilterDecoder implements Predicate {
    private final JsonContentEncoder encoder;

    public FilterDecoder(JsonContentEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public boolean matches(Exchange exchange) {
        Object o = exchange.getIn().getBody();
        if (o instanceof String) {
            Object[] args = encoder.decode((String) o);
            exchange.getIn().setBody(args);
            return true;
        }
        return false;
    }
}
