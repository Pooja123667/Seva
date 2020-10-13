package codewithpooja.com.seva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DonorLandingScreenActivity extends AppCompatActivity implements myInterface{
    RecyclerView donationListRV;
    DonationListAdapter donationListAdapter;
    ArrayList<Donation> donationArrayList;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private FloatingActionButton notification_button;
    SharedPref sharedPref; //making an object of shared pref

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_logout:
                sharedPref.setStatus(false);
                finish();
                Intent goHomeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(goHomeIntent);
                Toast.makeText(this, "donor logged out successfuly", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_tracker:
                Intent intent = new Intent(getApplicationContext(), donationChart.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_landing_screen);
        //donationArrayList = new ArrayList<>();
        //getDonationList();
        sharedPref = new SharedPref(getApplicationContext());

        /*donationListRV = (RecyclerView)findViewById(R.id.donationListRV); //initializing RV
        donationListRV.setLayoutManager(new LinearLayoutManager(this));
        donationListRV.setAdapter(donationListAdapter);*/
        setTitle("Welcome: " + sharedPref.getName());
        //donationListAdapter = new DonationListAdapter(this, donationArrayList);


        Toast toast = Toast.makeText(this, "donation screen", Toast.LENGTH_SHORT);
        toast.show();
        Log.i("donorlogin", "oncreatemethod");
        notification_button = (FloatingActionButton)findViewById(R.id.donor_notif);
        toolbar = (Toolbar)findViewById(R.id.donor_toolbar);
        //setSupportActionBar(toolbar);

        viewPager = (ViewPager)findViewById(R.id.donor_view_pager);


        final ArrayList<FragmentData> dfdl = new ArrayList();
        dfdl.add(new FragmentData(new MyDonationsFragment(), "My donations"));
        DonationFormFragment donationFormFragment = new DonationFormFragment();
        donationFormFragment.setInter(this);
        dfdl.add(new FragmentData(donationFormFragment, "new donation")); //remains the same
         ViewPagerAdapterDonor viewPagerAdapterDonor = new ViewPagerAdapterDonor(getSupportFragmentManager(), dfdl);
        viewPager.setAdapter(viewPagerAdapterDonor);
        //how to start the transaction? (for data transfer between fragments)


        tabLayout = (TabLayout)findViewById(R.id.donor_tab_layout);
        this.tabLayout.setupWithViewPager(this.viewPager);

        notification_button.setOnClickListener(new View.OnClickListener() { //starting a new activity
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getBaseContext(), "floating button clicked", Toast.LENGTH_LONG);
                toast.show();
                Intent donationStatusIntent = new Intent(getBaseContext(), DonorSideDonationStatus.class);
                startActivity(donationStatusIntent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("donoronstartlogin","" + sharedPref.getStatus());
        if(!sharedPref.getStatus()){ //if status false then return to home.
            Intent intent = new Intent(this, LoginActivity.class); //when wrong sending user back to login
            Toast toast = Toast.makeText(getBaseContext(), "something went wrong", Toast.LENGTH_SHORT);
            toast.show();
            Log.i("donoronstartlogin","logincheck");
            startActivity(intent);
            finish();
        }
        Log.i("donoronstartlogin","logincheckoutside if");
    }

    @Override
    public void changeTab(int index) {
        tabLayout.getTabAt(index).select();
    }

}
