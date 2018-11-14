package com.company.action.commons;

public class Consts {
    public static final String CURRENT_USER = "currentUser";

    public interface Role{
        int ROLE_USER=0;
        int ROLE_ADMIN=1;
    }

    public interface ValidType{
        String EMAIL="email";
        String USERNAME="username";
    }

}
