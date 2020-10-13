package codewithpooja.com.seva;

class User {
    int user_id;
    String name;
    String username;
    String contact;
    String password;
    String user_type;
    String token_key;


    public User(int user_id, String name, String username, String contact, String password, String user_type, String token_key){
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.contact = contact;
        this.user_type = user_type;
        this.name = name;
        this.token_key = token_key;

    }
}
