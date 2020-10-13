package codewithpooja.com.seva;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("error")
    boolean error;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    User data;

    public Result(boolean error, String message, User data){ //constructor to initialize
        this.error = error;
        this.message = message;
        this.data = data;

    }
}
