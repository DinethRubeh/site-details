package com.example.android.sitedetails;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;



public class DrawerActivity extends AppCompatActivity {
    protected DrawerLayout drawerLayout;
    protected Toolbar toolbar;
    protected int item_id;
    protected NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initNavigationDrawer();

    }

    public void initNavigationDrawer() {

        navigationView = findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item_id = item.getItemId();

                switch (item_id) {
                    case R.id.about: {
                        drawerLayout.closeDrawers();
                        StartActivity(AboutUsActivity.class);

                        break;
                    }
                    case R.id.siteDetails: {
                        drawerLayout.closeDrawers();
                        StartActivity(NewSiteDetails.class);
                    }

                }


                return true;
            }
        });



        View header = navigationView.getHeaderView(0);
        drawerLayout = findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }

        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void StartActivity(final Class toClass){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),toClass));
            }
        },200);

    }
}
