package codewithpooja.com.seva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    private TextView registerLink;
    private EditText username;
    private  EditText password;
    private Button loginSubmitButton;
    SharedPref sharedPref; //making an object of shared pref
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerLink = (TextView)findViewById(R.id.register_link);
        username = (EditText) findViewById(R.id.username_text);
        password = (EditText)findViewById(R.id.password_text);
        loginSubmitButton = (Button)findViewById(R.id.login_submit_button);
        registerLink.setPaintFlags(registerLink.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

        loginSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           if(validateUsername() && validatePassword()) //checking for validations
                {
                    String uname = username.getText().toString().trim();
                    String pwd = password.getText().toString().trim();
                    Log.e("error", "came till here");
                    Retrofit retrofit = new Retrofit.Builder() //making a new retrofit obj
                            .baseUrl(APIUrl.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    APIService service = retrofit.create(APIService.class);
                    Call<Result> call = service.checkUser("signin",uname, pwd);

                    call.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            Result res = response.body();
                            Log.e("message","" + response.errorBody());
                            Log.e("response",""+ response.code());
                            if(!res.error){ //error here
                                SharedPref sharedPref = new SharedPref(getApplicationContext()); //creating an object
                                sharedPref.setStatus(true);
                                sharedPref.setTokenKey(res.data.token_key); // setting tokenkey as token key which comes from DB
                                sharedPref.setName(res.data.name); //setting the name as the names of the user who logged in
                                sharedPref.setType(res.data.user_type); // sharedPref.setType("d"); //trying yo make it generalized
                                Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_SHORT);

                                if(res.data.user_type.equals("d")){
                                    Intent donorScreenIntent = new Intent(getBaseContext(), DonorLandingScreenActivity.class);
                                    donorScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK); //
                                    startActivity(donorScreenIntent);
                                    finish();
                                }
                                else if(res.data.user_type.equals("c")){
                                    Intent collectorScreenIntent = new Intent(getBaseContext(), CollectorLandingScreenActivity.class);
                                    collectorScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK); //
                                    startActivity(collectorScreenIntent);
                                    finish();
                                }
                            }
                            else{
                                Toast.makeText(
                                        LoginActivity.this,
                                        "In-Valid Login Credentials",
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + t.getMessage(),
                                    Toast.LENGTH_LONG).show();

                            Log.e("LOGIN ERROR", t.getMessage());
                        }
                    });



                }
                else
                    {
                        Toast.makeText(getBaseContext(), "validation incomplete", Toast.LENGTH_SHORT);
                    }

            }
        });
    }

    private void openRegisterActivity(){
        Intent registrationIntent = new Intent(this, RegistrationActivity.class);
        startActivity(registrationIntent);
    }

        public boolean validateUsername(){ //will add check for username from DB, check for special char
            String userName= username.getText().toString().trim();
            if(userName.isEmpty()){
                username.setError("Field cannot be empty");
                return false;
            }else {
                username.setError(null);
                return true; }
    }

    private boolean validatePassword() {
        String passwordInput = password.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        }else{
            password.setError(null);
            return  true;

        }
    }

    /*private  boolean donorLogin(){
        String userName= username.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();
        if(userName.equals("pk") && passwordInput.equals("123"))
        {
            Toast.makeText(this, "success till conditional statemnts", Toast.LENGTH_SHORT).show();
            return  true;
        }
        else
            Toast.makeText(this, "combination is wrong", Toast.LENGTH_SHORT).show();
            return false;
    }*/

    /*private boolean collectorLogin(){
        String userName= username.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();
        if(userName.equals("pk") && passwordInput.equals("456"))
        {
            return  true;}
        else
            Toast.makeText(this, "combination is wrong", Toast.LENGTH_SHORT).show();
        return false;
    }*/


}
