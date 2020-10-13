package codewithpooja.com.seva;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class collectorStats extends AppCompatActivity {
private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_stats);
        txt = (TextView)findViewById(R.id.collector_stats);
    }
}
