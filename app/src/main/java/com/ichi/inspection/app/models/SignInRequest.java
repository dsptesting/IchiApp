package com.ichi.inspection.app.models;

/**
 * Created by Palak on 25-03-2017.
 */

public class SignInRequest {

    private String userName;
    private String password;

    public SignInRequest() {}

    @Override
    public String toString() {
        return "SignInRequest{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
