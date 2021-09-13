package com.example.clevertap;

import android.app.NotificationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.pushnotification.CTPushNotificationListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements CTPushNotificationListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CleverTapAPI cleverTapAPI = CleverTapAPI.getDefaultInstance(getApplicationContext());

        cleverTapAPI.createNotificationChannel(getApplicationContext(),
                "testChannelId", "Test Channel Name",
                "Test Channel Description",
                NotificationManager.IMPORTANCE_MAX,
                true);
        cleverTapAPI.setCTPushNotificationListener(this);

        Button button = findViewById(R.id.btn_ct_event);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
                prodViewedAction.put("Product Id", "1");
                prodViewedAction.put("Product Image", "https://d35fo82fjcw0y8.cloudfront.net/2018/07/26020307/customer-success-clevertap.jpg");
                prodViewedAction.put("Product Name", "CleverTap");
                prodViewedAction.put("Date", new java.util.Date());

                cleverTapAPI.pushEvent("Product viewed", prodViewedAction);

                HashMap<String, Object> profileData = new HashMap<String, Object>();
                profileData.put("Email", "darshsanghvi.2016@gmail.com");
                cleverTapAPI.pushProfile(profileData);

            }
        });

    }

    @Override
    public void onNotificationClickedPayloadReceived(HashMap<String, Object> payload) {
        Log.d(TAG, "onNotificationClickedPayloadReceived: " + payload);
    }
}