package codewithpooja.com.seva;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CollectionListAdapter extends  RecyclerView.Adapter<CollectionListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Donation> listData;
    private CollectorInterface collectorInterface;
    SharedPref sharedPref;

    CollectionListAdapter(Context context, ArrayList<Donation> listData){
        this.context = context;
        this.listData = listData;

    }
    public void setCollectorInterface(CollectorInterface collectorInterface) {
        this.collectorInterface = collectorInterface;
    }

    public void setListData(ArrayList<Donation> listData){
        this.listData = listData;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_list_collectorside,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionListAdapter.ViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView foodQuantity;
        public ImageView imageFood;
        public Button readMore;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodQuantity = (TextView) itemView.findViewById(R.id.donorqty);
            imageFood = (ImageView) itemView.findViewById(R.id.foodImage);
            readMore = (Button)itemView.findViewById(R.id.read_more_button);
        }

        public void bind(final int position) {
            final Donation donation = listData.get(position);
            foodQuantity.setText("Food Quantity: " + donation.donation_quantity +" kg"); //setting the food quantity
            final String url = donation.image_name;
            Log.e("IMAGE_URL", url);
            Picasso.with(context).load(url).resize(400,300).into(imageFood);
            readMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "clicked at position: " + position, Toast.LENGTH_SHORT).show();
                    /*Intent intent = new Intent(context, selectDonation.class);
                    intent.putExtra("image_url",donation.image_name);
                    intent.putExtra("donation_brief","Food brief :  " + donation.donation_brief);
                    intent.putExtra("donation_quantity","Food quantity :  " + donation.donation_quantity + "kg");
                    intent.putExtra("donation_date","Food date :  " + donation.donation_date);
                    intent.putExtra("donor_name", "Donor name :  " + donation.name);
                    intent.putExtra("donor_contact", "Donor contact :  " + donation.contact);
                    intent.putExtra("donation_id", donation.donation_id);
                    //intent.putExtra("POS", position);
                    Log.e("Pressed POSITION", "" + position);

                    sharedPref = new SharedPref(context);
                    intent.putExtra("token_key_c", sharedPref.getTokenKey());*/


                    //Log.e("my user", "" + sharedPref.getName()); //gives the logged in user
                    //Log.e("collector ka token", "" + sharedPref.getTokenKey());
                     collectorInterface.startDelete(donation,position);
                    //context.startActivity(intent); //staring thr next activity
                }
            });

        }
    }
}
