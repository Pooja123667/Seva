package codewithpooja.com.seva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class selectDonation extends AppCompatActivity {

     SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_donation);
        ImageView foodImage = (ImageView)findViewById(R.id.imageViewC);
        TextView foodBrief = (TextView) findViewById(R.id.food_brief);
        TextView donorContact = (TextView)findViewById(R.id.donor_contact);
        TextView foodQuantity = (TextView)findViewById(R.id.food_qty);
        TextView foodDate = (TextView)findViewById(R.id.food_date);
        TextView donorName = (TextView)findViewById(R.id.donor_name);
        ImageButton accept_btn = (ImageButton) findViewById(R.id.accept_btn);
        ImageButton decline_btn = (ImageButton)findViewById(R.id.reject_btn);

        sharedPref = new SharedPref(getApplicationContext());
        Intent data = getIntent();

        Donation donation = (Donation)data.getSerializableExtra("DONATION");
        final int position = data.getIntExtra("POS",-1);


        data.putExtra("donation_id", donation.donation_id);
        final String donation_id = data.getStringExtra("donation_id");
        final String token = sharedPref.getTokenKey();
        Log.e("donation id","" + donation_id);
        Log.e("token current user", "" + token);

        foodBrief.setText("Food brief : " + donation.donation_brief);
        donorContact.setText("Donor contact : " + donation.contact);
        foodQuantity.setText("Food quantity : " + donation.donation_quantity);
        foodDate.setText("Donation date : " + donation.donation_date);
        donorName.setText("Donor name : " + donation.name);

        String url = donation.image_name;
        Picasso.with(this).load(url).into(foodImage);

        Log.e("POSITION", "" + position);


        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder() //making a new retrofit obj
                        .baseUrl(APIUrl.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService service = retrofit.create(APIService.class);
                Call<Result> call = service.setFoodStatus("accept", token, donation_id);
                Log.e("DONATION id", "" + donation_id);
                Log.e("token of logged in user", "" + token);

                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Result res = response.body();
                        Log.e("message","" + response.errorBody());
                        Log.e("response",""+ response.code());
                        if(!res.error){ //error here
                            Toast.makeText(getApplicationContext(),
                                    res.message,
                                    Toast.LENGTH_LONG).show();
                            Toast.makeText(getBaseContext(), "Donation Accepted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), CollectorLandingScreenActivity.class);
                            startActivityForResult(intent, 101);
                            finish(); //finishes the process and returns to main activity
                        }
                        else{
                            Toast.makeText(getApplicationContext(),
                                    res.message,
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
        });

        decline_btn.setOnClickListener(new View.OnClickListener() { //get the position for that particular list item
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("POS", position);
                Log.e("POSITION ANDAR SE", "" + position);
                setResult(1, intent);
                Toast.makeText(getBaseContext(), "Donation declined", Toast.LENGTH_SHORT).show();
                finish(); //finishes the process and returns to main activity (Collector Landing screen activity)
            }
        });
    }

}
