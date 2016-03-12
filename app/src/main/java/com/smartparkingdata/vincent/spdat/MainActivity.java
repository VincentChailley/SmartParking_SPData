package com.smartparkingdata.vincent.spdat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvData;
    public int i=0;
    //private ListView lvModeles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnGet = (Button)findViewById(R.id.btnGet);
        Button btnGet2 = (Button)findViewById(R.id.btnGet2);
        tvData = (TextView)findViewById(R.id.tvJsonItem);
        //lvModeles = (ListView)findViewById(R.id.lvModeles);


/*        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==3) {
                    new JSONTask().execute("http://aureliemarcuard.com/sp/Jsontest3.json");
                    i=0;
                    return;
                }
                if(i==2) {
                    new JSONTask().execute("http://aureliemarcuard.com/sp/Jsontest4.json");
                    i++;
                    return;
                }
                if(i==1) {
                    new JSONTask().execute("http://aureliemarcuard.com/sp/Jsontest5.json");
                    i++;
                    return;
                }
                if(i==0) {
                    new JSONTask().execute("http://aureliemarcuard.com/sp/Jsontest6.json");
                    i++;
                }

            }
        });
*/

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONTask().execute("http://www.aureliemarcuard.com/sp/Jsontest0.json");
                //startActivity(new Intent(MainActivity.this, MapsActivity.class));
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                //intent.putExtra("",);
                startActivity(intent);

            }
        });



        btnGet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new JSONTask().execute("http://www.aureliemarcuard.com/sp/Jsontest0.json");
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Salut, fonctionnel bientôt", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //Récupere les données du JSON en les traitant.
    public class JSONTask extends AsyncTask<String, String, String>{

        @Override
        public String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try{
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("estimations");

                StringBuffer finalBufferedData = new StringBuffer();

                for (int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    Modeles modeles = new Modeles();

                    String rue = finalObject.getString("rue");
                    modeles.getRue_obj();
                    int estimation = finalObject.getInt("estimation");
                    modeles.getEstimation_obj();
                    String longitude = (String) finalObject.get("longitude");
                    modeles.getLongitude_obj();
                    String latitude = (String) finalObject.get("latitude");
                    modeles.getLatitude_obj();
                    finalBufferedData.append(rue + " : " + estimation + longitude + ":" + latitude + "\n");
                }

                return finalBufferedData.toString();
                //return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }



        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tvData.setText(result);
        }
    }

/*    public class JSONTask extends AsyncTask<String, String, List<Modeles>>{

        @Override
        public List<Modeles> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try{
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("estimations");

                List<Modeles> modelesList = new ArrayList<>();

                for (int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    Modeles modele = new Modeles();
                    modele.setRue_obj(finalObject.getString("rue"));
                    modele.setEstimation_obj(finalObject.getInt("estimation"));
                    modele.setLongitude_obj((String) finalObject.get("longitude"));
                    modele.setLatitude_obj((String) finalObject.get("latitude"));

                    //adding the final object to the list
                    modelesList.add(modele);
                }
                return modelesList;
                //return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(List<Modeles> result) {
            super.onPostExecute(result);
        }
    }

    //Recupérer les données du JSON sans traitement :
/*    public class JSONTaskold extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try{
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("estimations");

                StringBuffer finalBufferedData = new StringBuffer();
                for (int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    String rue = finalObject.getString("rue");
                    int estimation = finalObject.getInt("estimation");
                    long longitude = (long) finalObject.get("longitude");
                    long latitude = (long) finalObject.get("latitude");
                    finalBufferedData.append(rue + " : " + estimation + "\n" + longitude + ":" + latitude + "\n");
                }

                return finalBufferedData.toString();
                //return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tvData.setText(result);
        }
    }*/
}
