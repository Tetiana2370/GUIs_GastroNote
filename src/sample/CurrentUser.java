package sample;

public class CurrentUser {
    static User loggedUser;

    public static User getInstance(){
        if(loggedUser==null){
            loggedUser = new User();
        }
        return loggedUser;
    }
}
