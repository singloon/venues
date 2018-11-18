package com.kalan.venues.model;

public class Credentials {
    private final String clientId;
    private final String secret;

    public Credentials(String clientId, String secret) {
        this.clientId = clientId;
        this.secret = secret;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSecret() {
        return secret;
    }
}
