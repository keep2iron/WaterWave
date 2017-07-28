package io.github.keep2iron.waterwave;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WaterWaveView waterWaveView = (WaterWaveView) findViewById(R.id.waterWaveView);
        waterWaveView.startWave();

        final ImageView ivAndroid = (ImageView) findViewById(R.id.ivAndroid);
        waterWaveView.setOnWaveListener(new WaterWaveView.OnWaveListener() {
            @Override
            public void onWave(int y) {
                ivAndroid.setY(y - ivAndroid.getHeight());
            }
        });
    }
}