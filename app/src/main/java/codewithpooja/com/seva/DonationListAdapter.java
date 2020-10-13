package codewithpooja.com.seva;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DonationListAdapter extends RecyclerView.Adapter<DonationListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Donation> listData;

    DonationListAdapter(Context context, ArrayList<Donation> data) {
        this.context = context;
        this.listData = data;
    }
    public void setListData(ArrayList<Donation> listData) {

        this.listData = listData;
        Log.e("CHECK", listData.get(0).donation_quantity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_list,parent,false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull DonationListAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView foodQuantity;
        public TextView currentDate;
        public ImageView imageFood;

         public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodQuantity = (TextView) itemView.findViewById(R.id.donation_food_qty);
            currentDate = (TextView) itemView.findViewById(R.id.donation_date);
            imageFood = (ImageView) itemView.findViewById(R.id.image_food);
        }

        public void bind(final int position) {
            final Donation donation = listData.get(position);
            foodQuantity.setText(donation.donation_quantity);
            currentDate.setText(donation.donation_date);
            String url = donation.image_name;
            Log.e("IMAGE_URL", url);
            Picasso.with(context).load(url).resize(500,300).into(imageFood);

        }
    }
}
