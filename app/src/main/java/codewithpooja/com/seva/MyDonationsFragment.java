package codewithpooja.com.seva;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyDonationsFragment extends Fragment {

    RecyclerView donationListRV;
    DonationListAdapter donationListAdapter;
    ArrayList donationArrayList;
    SharedPref sharedPref; //making an object of shared pref*/


    public MyDonationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_donations, container, false);

        donationArrayList = new ArrayList<>();
        sharedPref = new SharedPref(getActivity().getApplicationContext());



        donationListRV = v.findViewById(R.id.donationListRV); //initializing RV
        donationListRV.setLayoutManager(new LinearLayoutManager(getContext()));
       // donationListRV.setAdapter(donationListAdapter);

       // donationListAdapter = new DonationListAdapter(getContext(), donationArrayList);
        getDonationList();
        return v;
    }


    private void getDonationList(){
        Retrofit retrofit = new Retrofit.Builder() //making a new retrofit obj
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<List<Donation>> call  = service.getDonationList("dlist", sharedPref.getTokenKey());
        call.enqueue(new Callback<List<Donation>>() {
            @Override
            public void onResponse(Call<List<Donation>> call, Response<List<Donation>> response) {
                donationArrayList = (ArrayList) response.body();
                donationListAdapter = new DonationListAdapter(getContext(), donationArrayList);
                donationListRV.setAdapter(donationListAdapter);
                //donationListAdapter.setListData(donationArrayList); //no effect on uncommenting
               // donationListAdapter.notifyDataSetChanged();
                Log.e("MAIN", ""+donationArrayList.size());
            }

            @Override
            public void onFailure(Call<List<Donation>> call, Throwable t) {
                Toast.makeText(
                        getActivity().getApplicationContext(),
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }
    public void refresh(){
        getDonationList();
    }
  }
