package codewithpooja.com.seva;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
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

public class CollectorLandingScreenActivity extends AppCompatActivity implements CollectorInterface {
    RecyclerView CollectionListRV;
    CollectionListAdapter collectionListAdapter;
    ArrayList collectionArrayList;
    SharedPref sharedPref; //making an object of shared pref
    private Button moreButton;
    private TextView donorQuantity;
    private ImageView foodImage;
    private Toolbar toolbar;
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
                Toast.makeText(this, "Collector logged out selected", Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.actionRefresh:
                getCollectionList();
                Toast.makeText(
                        getApplicationContext(),
                        "Donation List Refreshed",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_tracker:
                Intent in = new Intent(this, collectorStats.class);
                startActivity(in);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_collectorside_donations);
        Toast.makeText(this, "this is the landing screen for collector side!",Toast.LENGTH_SHORT).show();

        sharedPref = new SharedPref(getApplicationContext());
        setTitle("Welcome: " + sharedPref.getName());
        collectionArrayList = new ArrayList<>();
        getCollectionList();

        collectionListAdapter = new CollectionListAdapter(this, collectionArrayList);

        CollectionListRV= (RecyclerView)findViewById(R.id.collectionListRV);

        moreButton = (Button)findViewById(R.id.read_more_button);
        donorQuantity = (TextView)findViewById(R.id.donorqty);
        foodImage = (ImageView)findViewById(R.id.foodImage);
        toolbar = (Toolbar)findViewById(R.id.collector_toolbar);
        toolbar.setTitle("All new donations");

        CollectionListRV.setLayoutManager(new LinearLayoutManager(this));
        CollectionListRV.setAdapter(collectionListAdapter);
        collectionListAdapter.setCollectorInterface(this);
    }

     @Override
    protected void onStart() {
        super.onStart();
        if(!sharedPref.getStatus()){ //if status false then return to home.
            Intent intent = new Intent(this, LoginActivity.class); //when wrong sending user back to login
            Toast toast = Toast.makeText(getBaseContext(), "something went wrong (CS)", Toast.LENGTH_SHORT);
            toast.show();
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 102) {
            removeDonation(data.getIntExtra("POS", -1));
        }
        else if(requestCode == 101){
            Toast.makeText(this, "Thank you for not wasting food!", Toast.LENGTH_SHORT).show();
        }

    }

    private void getCollectionList(){
        Retrofit retrofit = new Retrofit.Builder() //making a new retrofit obj
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<List<Donation>> call  = service.getAllDonations("alldlist"); //use shared pref for name
        call.enqueue(new Callback<List<Donation>>() {
            @Override
            public void onResponse(Call<List<Donation>> call, Response<List<Donation>> response) {
                collectionArrayList = (ArrayList) response.body();
                //collectionArrayList = new CollectionListAdapter(this, collectionArrayList);
               // CollectionListRV.setAdapter(collectionListAdapter);
                collectionListAdapter.setListData(collectionArrayList); //no effect on uncommenting
                collectionListAdapter.notifyDataSetChanged();
                Log.e("MAIN", ""+ collectionArrayList.size());
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
    private void removeDonation(int position){
        if(position != -1)
        {
            collectionArrayList.remove(position);
            collectionListAdapter.setListData(collectionArrayList);
            collectionListAdapter.notifyItemRemoved(position);
        }
    }

    @Override
    public void startDelete(Donation donation, int position) {
        Intent intent = new Intent(this, selectDonation.class);
        intent.putExtra("DONATION", donation);
        intent.putExtra("POS", position);
        startActivityForResult(intent,102);
    }
}


