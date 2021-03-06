package pidal.alfonso.dialerApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;


public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_activity);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Start the next activity
                Intent i = new Intent(SplashScreenActivity.this, DialerActivity.class);
                startActivity(i);

                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, 4000);

    }

}
