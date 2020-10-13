package codewithpooja.com.seva;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ViewPagerAdapterDonor extends FragmentPagerAdapter { //fragmentPagerAdapter is an inbuilt base adapter
    private ArrayList<FragmentData> donorFragmentDataList;

    ViewPagerAdapterDonor(FragmentManager fm, ArrayList<FragmentData> dfdl){
        super(fm);
        this.donorFragmentDataList = dfdl;

    }
    @NonNull

    @Override
    public Fragment getItem(int position) {
        return ((FragmentData)this.donorFragmentDataList.get(position)).fmObject;
    }
    @Override
    public int getCount() {
        return this.donorFragmentDataList.size();
    }

    @Nullable
    public CharSequence getPageTitle(int position) {
        return ((FragmentData)this.donorFragmentDataList.get(position)).title;
    }

}
