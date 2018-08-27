package com.example.shared_parking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.shared_parking.activities.main.MainActivity;
import com.example.shared_parking.activities.parkingoffer.OffersActivity;
import com.example.shared_parking.activities.parkingspots.SpotsActivity;
import com.example.shared_parking.activities.search.SearchActivity;
import com.example.shared_parking.networking.NetworkUtilities;
import com.example.shared_parking.networking.ServerCallback;

import org.json.JSONException;
import org.json.JSONObject;


public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "BaseActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        final TextView Name = (TextView)headerView.findViewById(R.id.tvHeaderName);
        final TextView Mail = (TextView)headerView.findViewById(R.id.tvHeaderMail);
        final TextView Balance = (TextView)headerView.findViewById(R.id.tvHeaderBalance);

        SharedPreferences sharedPref = getSharedPreferences("key", Context.MODE_PRIVATE);
        NetworkUtilities.getUser(sharedPref.getString("auth_token", "default"), this, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    if(result.getBoolean("status") == false){
                        Name.setText("Please log in");
                        Mail.setText("or register!");
                        Balance.setText("No Balance!");
                    }
                    else{
                        JSONObject userjson = result.getJSONObject("result");
                        Name.setText(userjson.getString("firstname") + " " + userjson.getString("lastname"));
                        Mail.setText(userjson.getString("mail"));
                        Balance.setText(Double.toString((double)((double)userjson.getInt("balance")/100)) + " €");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(VolleyError volleyError) {
                Log.e(TAG, "Error with volley while create parkingspacing" + volleyError);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_main) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (id == R.id.nav_overview) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_contracts) {
            Intent intent = new Intent(this, ContractsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_offers) {
            Intent intent = new Intent(this, OffersActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_spots) {
            Intent intent = new Intent(this, SpotsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_report) {
            Intent intent = new Intent(this, ReportActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            // Delete auth_token
            SharedPreferences sharedPref = this.getSharedPreferences("key", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("auth_token", "");
            editor.commit();
            // Open log in screen
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }



        return true;
    }
}
