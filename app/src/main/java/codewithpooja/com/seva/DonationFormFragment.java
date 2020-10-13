package codewithpooja.com.seva;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContentResolverCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class DonationFormFragment extends Fragment {
    private EditText foodQuantity;
    private  EditText foodBrief;
    private Button submitDonationButton;
    private ImageButton browseButton;
    private ImageView imageView;
    public static final int PICK_IMAGE_CODE = 101;
    Uri uri;

    SharedPref sharedPref;
    myInterface inter;

    public void setInter(myInterface inter) {
        this.inter = inter;
    }

    public DonationFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_donation_form, container, false);

        foodQuantity = v.findViewById(R.id.food_qty);
        foodBrief = v.findViewById(R.id.food_brief);
        submitDonationButton = v.findViewById(R.id.donation_submit_btn);
        browseButton = v.findViewById(R.id.camera_btn);
        imageView = v.findViewById(R.id.imageViewCam);

        sharedPref = new SharedPref(getActivity().getApplicationContext());

        submitDonationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            saveNewDonation();
            }
        });

        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Browse button clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_CODE);
            }
        });
        return v;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CODE) {
            Log.e("ADD FOOD IMAGE", ""+requestCode);
            uri = data.getData();
            Log.e("ADD FOOD IMAGE URI", uri.toString());

            imageView.setImageURI(uri);
        }
    }

    private void saveNewDonation() {
        String donationQuantity = foodQuantity.getText().toString().trim();
        String donationBrief = foodBrief.getText().toString().trim();

        if (!donationQuantity.equals("") && !donationBrief.equals("") && uri != null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);

            File file = null;
            if(isStoragePermissionGranted()) {
                file = UriToFilePath.getFile(getContext(), uri); //"/storage/self/primary/Download/boy.png";//
                Log.e("FilePath","Path: "+file.getName());
            }


            RequestBody requestFile = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(uri)),file); //
            String action = "add";

            MultipartBody.Part image = MultipartBody.Part.createFormData("image_file", file.getName(), requestFile);
            RequestBody request = RequestBody.create(okhttp3.MultipartBody.FORM, action);
            final RequestBody donation_quantity = RequestBody.create(okhttp3.MultipartBody.FORM, donationQuantity);
            RequestBody donation_brief = RequestBody.create(okhttp3.MultipartBody.FORM, donationBrief);
            RequestBody token = RequestBody.create(okhttp3.MultipartBody.FORM, sharedPref.getTokenKey());

            Call<Result> call = service.addDonation(image, request, donation_quantity, donation_brief, token);

            Log.e("CHECK","CAME till here");
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Result res = response.body();
                    Toast.makeText(getContext(), res.message,
                            Toast.LENGTH_LONG).show();
                    Log.e("message","" + response.errorBody());
                    Log.e("response",""+ response.code());
                    if (!res.error) {
                       inter.changeTab(0);
                    }
                    foodQuantity.setText(null);
                    foodBrief.setText(null);
                    imageView.setVisibility(View.GONE);
                }


                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Toast.makeText(getContext(),"Error: " + t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

      public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return true;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

     @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String message = "Msg: ";
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    message += "Permission Granted, Now you can use local drive.";
                } else {
                    message += "Permission Denied, You cannot use local drive ";
                }
                break;
        }
        Toast.makeText(getContext(), message,Toast.LENGTH_SHORT).show();
    }

}
