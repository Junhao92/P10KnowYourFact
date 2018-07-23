package com.example.a14049472.p10_knowyourfact;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Fragment> al;
    MyFragmentPagerAdapter adapter;
    ViewPager vPager;
    Button btnreadLater,btnChangeColor;
    int requestCode = 123;
    int notificationID = 888;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vPager = (ViewPager)findViewById(R.id.viewpager1);
        btnreadLater=(Button)findViewById(R.id.btnReadLater);
        btnChangeColor=(Button)findViewById(R.id.btnChangeColor);
        FragmentManager fm = getSupportFragmentManager();

        btnChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                Random rnd = new Random();
                int color = Color.argb(255,rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256));
                vPager.setBackgroundColor(color);

            }
        });

        btnreadLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new
                            NotificationChannel("default", "Default Channel",
                            NotificationManager.IMPORTANCE_DEFAULT);

                    channel.setDescription("This is for default notification");
                    notificationManager.createNotificationChannel(channel);
                }

                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity
                        ( MainActivity.this, requestCode, intent,
                                PendingIntent.FLAG_CANCEL_CURRENT);


                // Build notification
                NotificationCompat.Builder builder = new
                        NotificationCompat.Builder(MainActivity.this, "default");
                builder.setContentTitle("Read later!");
                builder.setContentText("Subject");
                builder.setSmallIcon(android.R.drawable.btn_star_big_off);
                builder.setContentIntent(pIntent);
                builder.setAutoCancel(true);

                Notification n = builder.build();

                // An integer good to have, for you to programmatically cancel it
                notificationManager.notify(notificationID, n);
                finish();
            }
        });

        al=new ArrayList<Fragment>();
        al.add(new Frag1());
        al.add(new Frag2());
        al.add(new Frag3());

        adapter = new MyFragmentPagerAdapter(fm,al);
        vPager.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_previous){
            int previousPage=vPager.getCurrentItem()-1;
            vPager.setCurrentItem(previousPage,true);

        }else if (id ==R.id.action_random){
            Random random = new Random();
            int randomPage = random.nextInt(3);
            //int randomPage=vPager.getCurrentItem();
            vPager.setCurrentItem(randomPage,true);
        }else {
            int nextPage = vPager.getCurrentItem() + 1;
            vPager.setCurrentItem(nextPage, true);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
