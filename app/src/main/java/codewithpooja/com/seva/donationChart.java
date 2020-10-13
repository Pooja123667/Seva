package codewithpooja.com.seva;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class donationChart extends AppCompatActivity {
    private PieChart pieChart;
    private BarChart barChart;
    ArrayList foodArrayList;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_chart);
        pieChart = (PieChart)findViewById(R.id.chart);
        barChart = (BarChart)findViewById(R.id.barchart);
        sharedPref = new SharedPref(getApplicationContext());
        foodArrayList = new ArrayList<>();

     //    Donation donation = (Donation)getIntent().getSerializableExtra("DONATION");

        Log.e("error", "got initialized");

        Retrofit retrofit = new Retrofit.Builder() //making a new retrofit obj
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<List<Donation>> call  = service.getDonationList("dlist", sharedPref.getTokenKey()); //use shared pref for name
        call.enqueue(new Callback<List<Donation>>() {
            @Override
            public void onResponse(Call<List<Donation>> call, Response<List<Donation>> response) {
                if(response.body() != null)
                {
                    foodArrayList = (ArrayList) response.body();
                    Log.e("SIZE", "" + response.body().size());
                    List<PieEntry> pieEntries = new ArrayList<>();
                    for(Donation donation : response.body())
                    {
                        pieEntries.add(new PieEntry(Float.parseFloat(donation.getDonation_quantity())));
                    }
                    pieChart.setVisibility(View.VISIBLE);
                    pieChart.animateXY(2000,2000);
                    PieDataSet pieDataSet = new PieDataSet(pieEntries, "Food waste tracker");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    PieData pieData = new PieData(pieDataSet);
                    pieChart.setData(pieData);

                    Description description = new Description();
                    description.setText("Quantity of food donated");
                    pieChart.setDescription(description);
                    pieChart.invalidate();

                    List<BarEntry> barEntries = new ArrayList<>();
                    for(Donation donation:response.body())
                    {
                        barEntries.add(new BarEntry(Float.parseFloat(donation.getDonation_quantity()), Float.parseFloat(donation.getDonation_id())));
                    }
                    BarDataSet barDataSet = new BarDataSet(barEntries, "Food Wasted");
                    barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                    BarData barData = new BarData(barDataSet);
                    barData.setBarWidth(3.0f);

                    barChart.setVisibility(View.VISIBLE);
                    barChart.animateY(3000);
                    barChart.setData(barData);
                    barChart.setFitBars(true);

                    Description description1 = new Description();
                    description1.setText("Quantity of food donated");
                    barChart.setDescription(description1);
                    barChart.invalidate();

                }
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
