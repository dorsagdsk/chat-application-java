package Model;

import java.util.regex.Pattern;

public class Regex {
    public boolean emailRegex(String email){
        return (Pattern.matches("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*$",email));
    }

    public boolean usernameRegex(String username){ // ye chizi peyda kon natoone noghte khali sabt kone
        return (Pattern.matches("[a-zA-Z]+[0-9_.a-zA-Z]+" , username));
    }
    public boolean passwordRegex(String password){
        return (password.length()>=8&&Pattern.matches("[a-zA-Z0-9 -]+" , password));
    }

    public boolean nameRegex(String name){
        return (Pattern.matches("^[a-zA-Z]+[a-zA-Z ]+", name));
    }

    public boolean groupNameRegex(String groupName){
        return (Pattern.matches("^[a-zA-Z0-9]+[a-zA-Z0-9 ]+",groupName));
    }

    public boolean linkRegex(String link){
        return (Pattern.matches("^[@]+[a-zA-Z0-9_.]+",link));
    }
}
