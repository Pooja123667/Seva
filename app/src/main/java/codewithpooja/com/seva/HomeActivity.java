package codewithpooja.com.seva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private Button donationButton;
    private Button collectionButton;
    SharedPref sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPref = new SharedPref(getApplicationContext());
        boolean status = sharedPref.getStatus();
        String type = sharedPref.getType();
        donationButton = (Button)findViewById(R.id.donate_button);
        collectionButton = (Button)findViewById(R.id.collect_button);
        if(status && type.equals("d"))
        {
            collectionButton.setVisibility(View.GONE);
        }
        else if(status && type.equals("c"))
        {
            donationButton.setVisibility(View.GONE);
        }
        donationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDonationActivity();
            }
        });

        collectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCollectorActivity();
            }
        });

        }

        public void  openDonationActivity(){
        Intent donorIntent = new Intent(this, DonorLandingScreenActivity.class);
        startActivity(donorIntent);
        finish();
        }

    public void  openCollectorActivity(){
        Intent collectorIntent = new Intent(this, CollectorLandingScreenActivity.class);
        startActivity(collectorIntent);
        finish();
    }
    }

