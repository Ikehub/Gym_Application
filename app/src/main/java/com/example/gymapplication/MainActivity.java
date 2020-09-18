package com.example.gymapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button btnPlans, btnAllTrainings, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        Utils.getInstance(this);

        btnAllTrainings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllTrainingsActivity.class);
                startActivity(intent);
            }
        });

        btnPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlanActivity.class);
                startActivity(intent);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.app_name));
                builder.setMessage("Designed and Developed with love by Ikenna \n" +
                        "Contact me at ikennaokonkwo2012@gmail.com for any enquires.");
                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("Contact", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: Navigate user to an email app with my email as the receiver
                        String mailto = "mailto:ikennaokonkwo2012@gmail.com" +
                                "?subject=" + Uri.encode("Important Message") +
                                "&body=" + Uri.encode("Hi There, \n");

                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setData(Uri.parse(mailto));

                        try {
                            startActivity(emailIntent);
                        } catch (ActivityNotFoundException e) {
                            //TODO: Handle case where no email app is available
                            Toast.makeText(MainActivity.this, "You have no available email application, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setCancelable(false);
                builder.create().show();

            }
        });
    }

    private void initViews() {
        Log.d(TAG, "initViews: Started");
        btnPlans = findViewById(R.id.btnPlanActivity);
        btnAllTrainings = findViewById(R.id.btnAllActivities);
        btnAbout = findViewById(R.id.btnAbout);
    }
}