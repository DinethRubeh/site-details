package com.example.android.sitedetails;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.sitedetails.models.SiteDetailsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NewSiteDetails extends DrawerActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "NewSiteDetails";

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private EditText textViewSiteID,textViewElectricalTilt,textViewMechanicalTilt,
            textViewTowerHeight,textViewRRUTYPE,textViewAzimuth,textViewName,textViewDate,
            textViewCabinetType,textViewSectorID,textViewAntHeight;


    private ArrayList<String> siteNamesArrayList;
    private ArrayList<String> siteIdsArrayList;
    private AutoCompleteTextView  textViewSiteName,textViewAntennaType,textViewBBUType,textViewBand;

    private boolean checkIfOnClick = false;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_new_site_details, null, false);
        drawerLayout.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.siteDetails);

        pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pd.create();
        }
        pd.show();

        siteNamesArrayList = new ArrayList<>();
        siteIdsArrayList = new ArrayList<>();
        LoadSiteDetails(); //this method might require some time due to the heavy data load
        Log.d(TAG,"before finish");
        new Handler().postDelayed(new Runnable() { // this thread will delay the execution of below methods. it will wait
            //until LoadSiteDetails method finishes
            @Override
            public void run() {
                init_btnclearListner();
                setSuggestionAdapters();
                btnclearListner();
                btnclear1Listner();
            }
        },100); //100 milliseconds.  when you enter more site names to the db , this has to be increased.

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setSuggestionAdapters() {

        ArrayAdapter<String> siteNamesAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, siteNamesArrayList);
        textViewSiteName.setThreshold(1);
        textViewSiteName.setAdapter(siteNamesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_new_site_details, menu);
        return true;
    }
    public void btnclearListner() {
        Button btnclear = (Button) findViewById(R.id.btnclear);
        btnclear.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   textViewAntennaType.setText("");
                   textViewElectricalTilt.setText("");
                   textViewMechanicalTilt.setText("");
                   textViewTowerHeight.setText("");
                   textViewRRUTYPE.setText("");
                   textViewAzimuth.setText("");
                   textViewName.setText("");
                   textViewDate.setText("");
                   textViewAntHeight.setText("");
                   textViewSectorID.setText("");
                   Toast.makeText(NewSiteDetails.this, "Continue Site", Toast.LENGTH_LONG).show();
               }
    });
    }
    public void btnclear1Listner() {
        Button btnclear1 = (Button) findViewById(R.id.btnclear1);
        btnclear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewAntennaType.setText("");
                textViewElectricalTilt.setText("");
                textViewMechanicalTilt.setText("");
                textViewTowerHeight.setText("");
                textViewRRUTYPE.setText("");
                textViewAzimuth.setText("");
                textViewName.setText("");
                textViewDate.setText("");
                textViewAntHeight.setText("");
                textViewSiteID.setText("");
                textViewBand.setText("");
                textViewBBUType.setText("");
                textViewSiteName.setText("");
                textViewCabinetType.setText("");
                textViewSectorID.setText("");
                Toast.makeText(NewSiteDetails.this, "Add New Site", Toast.LENGTH_LONG).show();
            }
        });
    }

    void init_btnclearListner(){

        //you don't need to initialize the same view more than once.

        textViewSiteID = (EditText) findViewById(R.id.siteid);

        textViewAntennaType = (AutoCompleteTextView) findViewById(R.id.antennatype);
        String[] antenna_type = getResources().getStringArray(R.array.antenna_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,antenna_type);
        textViewAntennaType.setAdapter(adapter);

        textViewElectricalTilt = (EditText) findViewById(R.id.electricaltilt);
        textViewMechanicalTilt = (EditText) findViewById(R.id.mechanicaltilt);
        textViewTowerHeight = (EditText) findViewById(R.id.towerheight);
        textViewRRUTYPE = (EditText) findViewById(R.id.rrutype);
        textViewAzimuth = (EditText) findViewById(R.id.azimuth);

        textViewBand = (AutoCompleteTextView) findViewById(R.id.band);
        String[] band = getResources().getStringArray(R.array.band);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,band);
        textViewBand.setAdapter(adapter1);

        textViewName = (EditText) findViewById(R.id.name);
        textViewDate = (EditText) findViewById(R.id.date);
        textViewSiteName = (AutoCompleteTextView) findViewById(R.id.sitename);
        textViewCabinetType = (EditText) findViewById(R.id.cabinettype);

        textViewBBUType = (AutoCompleteTextView) findViewById(R.id.bbutype);
        String[] bbu_type = getResources().getStringArray(R.array.bbu_type);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,bbu_type);
        textViewBBUType.setAdapter(adapter2);

        textViewSectorID = (EditText) findViewById(R.id.sectorid);
        textViewAntHeight = (EditText) findViewById(R.id.antennaheight);

        textViewSiteName.setOnItemClickListener(this);
    }

    void LoadSiteDetails(){
        //this method is used to load siteName and siteId from the database
        Log.d(TAG,"start");
        DatabaseReference siteDetailsRef = FirebaseDatabase.getInstance().getReference("Site-Details");
        siteDetailsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SiteDetailsModel siteDetails = snapshot.getValue(SiteDetailsModel.class);

                    assert siteDetails != null;
                    siteNamesArrayList.add(siteDetails.getSiteName());
                    siteIdsArrayList.add(siteDetails.getSiteId());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d(TAG,"finish");
        pd.dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // this method gets triggered when user click a suggested siteName
        checkIfOnClick = true;
        String site_id = siteIdsArrayList.get(siteNamesArrayList.indexOf(parent.getItemAtPosition(position).toString()));
        textViewSiteID.setText(site_id); //site Id will be set here
        Log.d(TAG,"site id: "+ site_id);
        Log.d(TAG,"site name: "+ parent.getItemAtPosition(position).toString());
    }

    public void saveDetails(View view) { //btnSave onClick method
        String SiteID = textViewSiteID.getText().toString().trim();
        String AntennaType = textViewAntennaType.getText().toString().trim();
        String ElectricalTilt = textViewElectricalTilt.getText().toString().trim();
        String MechanicalTilt = textViewMechanicalTilt.getText().toString().trim();
        String TowerHeight = textViewTowerHeight.getText().toString().trim();
        String RRUTYPE = textViewRRUTYPE.getText().toString().trim();
        String Azimuth = textViewAzimuth.getText().toString().trim();
        String Band = textViewBand.getText().toString().trim();
        String Name = textViewName.getText().toString().trim();
        String Date = textViewDate.getText().toString().trim();
        String SiteName = textViewSiteName.getText().toString().trim();
        String CabinetType = textViewCabinetType.getText().toString().trim();
        String BBUType = textViewBBUType.getText().toString().trim();
        String SectorID = textViewSectorID.getText().toString().trim();
        String AntennaHeight = textViewAntHeight.getText().toString().trim();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Export Site Details").child(SiteName+"-"+SectorID);
        myRef.child("Antenna Type").setValue(AntennaType);
        myRef.child("Electrical Tilt").setValue(ElectricalTilt);
        myRef.child("Mechanical Tilt").setValue(MechanicalTilt);
        myRef.child("Tower Height").setValue(TowerHeight);
        myRef.child("RRU Type").setValue(RRUTYPE);
        myRef.child("Azimuth").setValue(Azimuth);
        myRef.child("Band").setValue(Band);
        myRef.child("Name").setValue(Name);
        myRef.child("Date").setValue(Date);
        myRef.child("Site ID").setValue(SiteID);
        myRef.child("Cabinet Type").setValue(CabinetType);
        myRef.child("BBU Type").setValue(BBUType);
        myRef.child("Sector ID").setValue(SectorID);
        myRef.child("Site Name").setValue(SiteName);
        myRef.child("Antenna Height").setValue(AntennaHeight);

        if (!checkIfOnClick){ //check if the site is brand new or existing one
            addNewSiteDetail(SiteName,SiteID);
        }

        checkIfOnClick = false;

        Toast.makeText(NewSiteDetails.this, "Site Details Added", Toast.LENGTH_LONG).show();

    }

    private void addNewSiteDetail(String siteName, String siteID) {
        //add site details to the db if it is brand new

        DatabaseReference siteDetailsRef = FirebaseDatabase.getInstance().getReference("Site-Details").push();

        siteDetailsRef.child("siteName").setValue(siteName);
        siteDetailsRef.child("siteId").setValue(siteID);
    }
}