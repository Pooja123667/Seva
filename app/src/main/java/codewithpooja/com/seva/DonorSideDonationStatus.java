package codewithpooja.com.seva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DonorSideDonationStatus extends AppCompatActivity { //this is an activity
    RecyclerView AcceptedDonationRV;
    DonationStatusListAdapter donationStatusListAdapter;
    ArrayList acceptedDonationArrayList;
    SharedPref sharedPref;
    private Toolbar toolbar;
    private ImageView donationimage;
    private TextView collectorName;
    private TextView collector_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_donation_status);
        sharedPref = new SharedPref(getApplicationContext());
        acceptedDonationArrayList = new ArrayList<>();
        getAcceptedDonationList();

        donationStatusListAdapter = new DonationStatusListAdapter(this, acceptedDonationArrayList);

        AcceptedDonationRV = (RecyclerView)findViewById(R.id.acceptedDonationListRV);

        toolbar = (Toolbar)findViewById(R.id.accepted_donation_toolbar);
        toolbar.setTitle("Accepted Donations");

        donationimage = (ImageView)findViewById(R.id.image_food);
        collectorName = (TextView)findViewById(R.id.collector_name);
        collector_contact = (TextView)findViewById(R.id.collector_contact);

        AcceptedDonationRV.setLayoutManager(new LinearLayoutManager(this));
        AcceptedDonationRV.setAdapter(donationStatusListAdapter);


    }
    private void getAcceptedDonationList(){
        Retrofit retrofit = new Retrofit.Builder() //making a new retrofit obj
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<List<Donation>> call  = service.getAcceptedDonationList("acceptedD", sharedPref.getTokenKey());
        call.enqueue(new Callback<List<Donation>>() {
            @Override
            public void onResponse(Call<List<Donation>> call, Response<List<Donation>> response) {
                acceptedDonationArrayList = (ArrayList) response.body();
                //collectionArrayList = new CollectionListAdapter(this, collectionArrayList);
                // CollectionListRV.setAdapter(collectionListAdapter);
                donationStatusListAdapter.setListData(acceptedDonationArrayList); //no effect on uncommenting
                donationStatusListAdapter.notifyDataSetChanged();
                Log.e("MAIN", ""+ acceptedDonationArrayList.size());
            }

            @Override
            public void onFailure(Call<List<Donation>> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }

        });
    }

}
