package codewithpooja.com.seva;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DonationStatusListAdapter extends RecyclerView.Adapter<DonationStatusListAdapter.ViewHolder> { //using Array User as my base for fetching values
    private Context context;
    private ArrayList<Donation> listData;

    DonationStatusListAdapter(Context context, ArrayList<Donation> listData){ //constructor to initialize values
        this.context = context;
        this.listData = listData;
    }
    public void setListData(ArrayList<Donation> listData){
        this.listData = listData;
       // Log.e("CHECK NAME", listData.get(0).name);
    }
    @NonNull
    @Override
    public DonationStatusListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_status_list,parent,false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull DonationStatusListAdapter.ViewHolder holder, int position) {
        holder.bind(position);

    }
    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageFood;
        public TextView collectorName;
        public TextView collectorContact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            collectorName = (TextView)itemView.findViewById(R.id.collector_name);
            imageFood = (ImageView) itemView.findViewById(R.id.image_food);
            collectorContact= (TextView)itemView.findViewById(R.id.collector_contact);
        }
        public void bind(final int position) {
            final Donation donation = listData.get(position);
            collectorName.setText(donation.name);
            collectorContact.setText(donation.contact);
            final String url = donation.image_name;
            Log.e("IMAGE_URL", url);
            Picasso.with(context).load(url).resize(500,300).into(imageFood);
        }
    }
}
