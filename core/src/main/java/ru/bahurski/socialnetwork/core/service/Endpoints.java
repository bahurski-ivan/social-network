package ru.bahurski.socialnetwork.core.service;

/**
 * Created by Ivan on 22/12/2016.
 */
public enum Endpoints {
    DIALOGS_LIST("jms:dialogs.list"),
    DIALOGS_CREATE("jms:dialogs.create"),
    DIALOGS_ADD_MESSAGE("jms:dialogs.messages.add"),
    DIALOGS_LIST_MESSAGES("jms:dialogs.messages.list"),

    USERS_CREATE("jms:users.create"),
    USERS_UPDATE("jms:users.update"),
    USERS_SEARCH("jms:users.search"),
    USERS_LOGIN("jms:users.login"),

    PHOTOS_GET_BYTES("jms:photos.get.bytes"),
    PHOTOS_UPLOAD("jms:photos.upload"),
    PHOTOS_GET_BY_ID("jms:photos.find.id"),

    FRIENDS_REQUESTS_LIST("jms:friends.requests.list"),
    FRIENDS_LIST("jms:friends.list"),
    FRIENDS_CREATE("jms:friends.requests.create"),
    FRIENDS_REQUESTS_REJECT("jms:friends.requests.reject"),
    FRIENDS_REQUESTS_ACCEPT("jms:friends.requests.accept"),
    FRIENDS_REMOVE("jms:friends.remove");

    private final String endpointUrl;

    Endpoints(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }
}
