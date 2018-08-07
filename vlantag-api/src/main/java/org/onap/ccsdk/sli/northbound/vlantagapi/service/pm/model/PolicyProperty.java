package org.onap.ccsdk.sli.northbound.vlantagapi.service.pm.model;

public class PolicyProperty {
    String clientAuth;
    String authorization;
    String url;
    String environment;
    String username;
    String password;

    public String getClientAuth() {
        return clientAuth;
    }

    public void setClientAuth(String clientAuth) {
        this.clientAuth = clientAuth;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PolicyProperty [clientAuth=" + clientAuth + ", authorization=" + authorization + ", url=" + url
                + ", environment=" + environment + ", username=" + username + ", password=" + password + "]";
    }

}
