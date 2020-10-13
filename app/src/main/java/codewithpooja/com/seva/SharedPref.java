package codewithpooja.com.seva;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor; //to make changes in shared pref (we are reading and writing into the editor)

    public  SharedPref(Context context){ //initializing
        sharedPreferences = context.getSharedPreferences("Mypref", context.MODE_PRIVATE); //giving file name and mode
        editor = sharedPreferences.edit();
    }
    public void setStatus(boolean status){ //status is our key here
        editor.putBoolean("STATUS", status); //set status creates a key/updates it
        editor.commit();
    }
    public  boolean getStatus(){ //for retrieving the status value, using getboolean since we have set a boolean value
        return  sharedPreferences.getBoolean("STATUS", false); //if no status then default value is false
    }


    public void setType(String type){ //status is our key here
        editor.putString("TYPE", type); //set status creates a key/updates it
        editor.commit();
    }
    public  String getType(){
        return sharedPreferences.getString("TYPE", "");
    }


    public void setTokenKey(String key) {
        editor.putString("KEY", key);
        editor.commit();
    }
    public String getTokenKey() {
        return sharedPreferences.getString("KEY","");
    }


    public void setName(String name) {
        editor.putString("NAME", name);
        editor.commit();
    }
    public String getName() {
        return sharedPreferences.getString("NAME","");
    }
}
