package com.example.mobile_computing_ca2.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    public static User getUser(JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email");
        String token = jsonObject.getString("token");
        User user = new User(name, email, token);

        return user;
    }

    private String name;
    private String email;
    private String token;

    public User(String name, String email, String token) {
        this.name = name;
        this.email = email;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;

        if (obj != null && obj instanceof User) {
            User that  = (User) obj;
            if (this.email.equalsIgnoreCase(that.email)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return this.name + "(" + this.email + ")";
    }
}
