package com.example.livecurrencyconverter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int from = 2, to = 2;
    String strFrom;
    String strTo;

    double amount = 0;
    EditText amt;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //-----------------------------------------------

        Spinner sFrom = (Spinner) findViewById(R.id.spinnerFrom);
        Spinner sTo = (Spinner) findViewById(R.id.spinnerTo);
         amt = (EditText)findViewById(R.id.amount);

         amt.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {

             }

             @Override
             public void afterTextChanged(Editable s) {
                 convert(to,from);

             }
         });

        result = (TextView)findViewById(R.id.finalResult);



        sFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                from=position;
                switch (position){
                    case 0:
                        strFrom = "USD";
                        break;
                    case 1:
                        strFrom = "INR";
                        break;
                    case 2:
                        strFrom = "EUR";
                        break;
                    case 3:
                        strFrom = "GBP";
                        break;
                    case 4:
                        strFrom = "JPY";
                        break;


                }

                convert(from, to);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MainActivity.this, "not1", Toast.LENGTH_SHORT).show();

            }
        });

        sTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                to=position;
                switch (position){
                    case 0:
                        strTo = "USD";
                        break;
                    case 1:
                        strTo = "INR";
                        break;
                    case 2:
                        strTo = "EUR";
                        break;
                    case 3:
                        strTo = "GBP";
                        break;
                    case 4:
                        strTo = "JPY";
                        break;


                }


                convert(from, to);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(MainActivity.this, "not2", Toast.LENGTH_SHORT).show();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void convert(int from, int to) {


        new CheckConnectionStatus().execute("http://data.fixer.io/api/latest?access_key=4897ebcb4ee310c89c48aabbf0c50574");


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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //----------Connection stuffs-------------

    class CheckConnectionStatus extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {

            URL url = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s = bufferedReader.readLine();
                bufferedReader.close();
                return s;

            } catch (FileNotFoundException f) {

            } catch (IOException e) {
                Log.e("Error", e.getMessage(), e);
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            Log.i("Result", s);


            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);


                double valueTo = jsonObject.getJSONObject("rates").getDouble(strTo);
                double valueFrom = jsonObject.getJSONObject("rates").getDouble(strFrom);

                if(!amt.getText().toString().equals(""))
                amount = Double.parseDouble(amt.getText().toString());
                double finalVal = (valueTo/valueFrom)*amount;


//                JSONArray jsonArray = jsonObject.getJSONArray("rates");
                ;

                //Toast.makeText(MainActivity.this, ""+finalVal, Toast.LENGTH_SHORT).show();


                result.setText( String.format("%.5f", finalVal));


//                double usd = jsonObject.getDouble("USD");
//                Toast.makeText(MainActivity.this, ""+usd, Toast.LENGTH_SHORT).show();



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
