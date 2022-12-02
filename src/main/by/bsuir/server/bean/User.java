package main.by.bsuir.server.bean;

public class User {
    private final String login;
    private final String password;
    public static enum Rights {GUEST, MANAGER, ROOT}
    private final Rights rights;

    public User(String login, String password){
        this.login = login;
        this.password = password;
        rights = Rights.GUEST;
    }

    public User(String login, String password, Rights rights){
        this.login = login;
        this.password = password;
        this.rights = rights;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Rights getRights(){
        return rights;
    }
}
