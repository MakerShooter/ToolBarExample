package com.example.android.toolbarexample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView txtWebLink;
    Button shareIt;
    NotificationCompat.Builder notification;
    private static final int uniqueID = 3456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtWebLink = (TextView) findViewById(R.id.txtWebLink);


        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //hide the application name
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //notification part
        notification = new NotificationCompat.Builder(this);
        //this close the nofication
        notification.setAutoCancel(true);

    }


    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void displayWeb(View v) {

        openWebPage("http://uwolnijcialo.pl");

    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void composeEmail() {
        String subject = "Trener oddechu";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, "to jest nowa plikacja zobacz to");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void displayMail(View v) {
        composeEmail();
    }


    //this is to enable the action of the icon
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
//to create the share activity
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "share it");
                intent.putExtra(Intent.EXTRA_TEXT, "new trainer");
                //this is showing the title
                startActivity(Intent.createChooser(intent, "share using"));

                return true;

            case R.id.niNext:

                startActivity(new Intent(this, subActivity.class));
                return true;

            case R.id.miSetting:
                Context context = getApplicationContext();
                CharSequence text = "Hello toast!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void getNotification(View v){

        //build the notification
        notification.setSmallIcon(R.drawable.actionbar);
        notification.setTicker("this is time to train");
        notification.setContentText("Idzie Ci świetnie przed nami kolejny trening");
        notification.setContentTitle("Trener Oddechu przypomina");
        notification.setWhen(System.currentTimeMillis());

        //set the intent to start it
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);


        //build and issue notification
        NotificationManager mn = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        mn.notify(uniqueID,notification.build());

    }

}
