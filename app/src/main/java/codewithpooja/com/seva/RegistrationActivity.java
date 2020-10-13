package codewithpooja.com.seva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Pattern;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button registrationSubmitButton;
    private EditText name;
    private EditText contact;
    private EditText username;
    private EditText set_password;
    private EditText confirm_password;
    private String type;
    private Spinner spinner;
    private static final Pattern PASSWORD_PATTERN =      //regex for password (registration)
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);
        name = (EditText) findViewById(R.id.r_name);
        contact = (EditText) findViewById(R.id.r_cont);
        username = (EditText) findViewById(R.id.r_uname);
        set_password = (EditText) findViewById(R.id.r_set_pw);
        confirm_password = (EditText) findViewById(R.id.r_conf_pw);
        registrationSubmitButton = (Button) findViewById(R.id.reg_sub_button);
        spinner = (Spinner) findViewById(R.id.acc_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.accountType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        registrationSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateName();
                validateNumber();
                validateUsername();
                validatePassword();
                if (validateName() && validateName() && validateUsername() && validatePassword()) {
                    signUp();
                } else {
                    Toast.makeText(getBaseContext(), "Validations incomplete!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void signUp() {
        String Name = name.getText().toString().trim();
        String Contact = contact.getText().toString().trim();
        String Username = username.getText().toString().trim();
        String Password = set_password.getText().toString().trim();

        if (type.equals("d")) {

            Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
            Log.e("REG", "Calling API");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);
            Call<Result> call = service.createUser("signup_d", Name, Contact, Username, Password);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Result res = response.body();
                    Log.e("message","" + response.errorBody());
                    Log.e("response",""+ response.code());
                    if (res.error) {
                        Toast.makeText(getApplicationContext(),
                                res.message,
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                res.message,
                                Toast.LENGTH_LONG).show();
                        //SharedPref sharedPref = new SharedPref(getApplicationContext());
                        //sharedPref.setType("d");
                        Intent intent = new Intent(RegistrationActivity.this,
                                LoginActivity.class); //Json error hence not going to next activity
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + t.getMessage(),
                            Toast.LENGTH_LONG).show();

                    Log.e("REG ERROR", t.getMessage());
                }
            });

        } else if (type.equals("c")) {
            Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
            Log.e("REG", "Calling API");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);
            Call<Result> call = service.createUser("signup_c", Name, Contact, Username, Password);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Result res = response.body();
                    if (res.error) {
                        Toast.makeText(getApplicationContext(),
                                res.message,
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                res.message,
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegistrationActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + t.getMessage(),
                            Toast.LENGTH_LONG).show();

                    Log.e("REG ERROR", t.getMessage());
                }
            });
        }

    }

    public boolean validateName() {
        String orgName = name.getText().toString().trim();
        if (orgName.isEmpty()) {
            name.setError("Name field is required");
            return false;
        } else {
            return true;
        }

    }

    public boolean validateNumber() {
        String user_contact = contact.getText().toString().trim();
        if (user_contact.length() > 10) {
            contact.setError("number cannot be more than 10 digits");
            return false;
        } else if (user_contact.length() < 10) {
            contact.setError("number cannot be less than 10 digits");
        }
        return true;
    }

    public boolean validateUsername() {
        String reg_username = username.getText().toString().trim();
        if (reg_username.isEmpty()) {
            username.setError("username is empty");
            return false;
        }
        return true;
    }

    public boolean validatePassword() {
        String set_pw = set_password.getText().toString();
        String conf_pw = confirm_password.getText().toString();
        if (set_pw.isEmpty()) {
            set_password.setError("password field required");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(set_pw).matches()) {
            set_password.setError("password too weak");
            Toast toast = Toast.makeText(getBaseContext(),
                    "atleast one uppercase, lowercase, special character and number required",
                    Toast.LENGTH_LONG);
            toast.show();
            return false;
        } else if (conf_pw.isEmpty()) {
            confirm_password.setError("confirm your password");
            return false;
        } else if (!(conf_pw.equals(set_pw))) {
            confirm_password.setError("password does not match");
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { //logic for the spinner
        String text = parent.getItemAtPosition(position).toString();
        if (text.equals("Food Donor")) {
            Toast.makeText(this, "You have selected " + text + " option", Toast.LENGTH_SHORT).show();
            type = "d";
        } else if (text.equals("Food Collector")) {
            Toast.makeText(this, "You have selected " + text + " option", Toast.LENGTH_SHORT).show();
            type = "c";
        }
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(parent.getContext(), "You have to select account type", Toast.LENGTH_SHORT).show();
    }
}
