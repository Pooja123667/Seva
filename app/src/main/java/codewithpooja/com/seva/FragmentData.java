package codewithpooja.com.seva;

import androidx.fragment.app.Fragment;

public class FragmentData {
    Fragment fmObject;
    String title;

    public FragmentData(Fragment fmObject, String title){ //constructor to initialize
        this.fmObject = fmObject;
        this.title = title;
    }
}
