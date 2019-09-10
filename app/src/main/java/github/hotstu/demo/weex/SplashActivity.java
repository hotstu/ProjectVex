package github.hotstu.demo.weex;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import github.hotstu.vex.VexActivity;

public class SplashActivity extends AppCompatActivity   {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent i = new Intent(this, VexActivity.class);
        startActivity(i);
        finish();
    }
}
