package com.example.android.sitedetails;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class AboutUsActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_about_us);


        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_about_us, null, false);
        drawerLayout.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.about); //highlights the item in the drawer. onResume ekatath dapan.
        //then, wena activity ekaka idan back unamath hari eka highlight karala pennanawa
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.about);
    }
}
