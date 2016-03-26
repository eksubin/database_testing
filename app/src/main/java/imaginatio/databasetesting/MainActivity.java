package imaginatio.databasetesting;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;



    public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener


    {
        ///////////   Spinner Data  /////////////

        String c1="f1",c2="f2",c3="f3",c4="f4",c5="f5",c6="f6",c7="f7";
        String v2=null;
        private Spinner spinner;
        private static final String[]paths = {"Select a Domain you want to search ", "miRNA", "Mechanism","Disease","Pathways","Etilogy","Target","Fungtions"};


        ///////////    HTTP Connection  ///////////////

        HttpClient httpclient;
        HttpPost httppost;
        String mec,des,mi,path,eti;



        ///////////      Shared Preference Variables/////////////

        public static final String MyPREFERENCES = "MyPrefs" ;
        public static final String mirna = "mir";
        public static final String mechanism = "mech";
        public static final String desease = "dese";
        public static final String pathway = "path";
        public static final String etilogy = "etil";
        SharedPreferences sharedpreferences;


       /////////////        Extra Variables       /////////////////

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        EditText editText;
        Button testbutton;
        String result = null;
        Boolean alldataentered=false;


        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);      ////shared preference
            testbutton = (Button)findViewById(R.id.button);

            ////////////////////   Spinner  ////////////////////

            spinner = (Spinner)findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
            android.R.layout.simple_spinner_item,paths);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);      /////////Spinner
           spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
            spinner.setAdapter(adapter);

             editText = (EditText)findViewById(R.id.e1);
            StrictMode.setThreadPolicy(policy);

        }

        public void start(View view) {

                new datasearch(getApplicationContext()).execute();
        }


        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            switch (position) {
                case 0:
                    break;
                case 1:
                    v2=c1;
                    break;
                case 2:
                    v2=c2;
                    break;
                case 3:
                    v2=c3;
                    break;
                case 4:
                    v2=c4;
                    break;
                case 5:
                    v2=c5;
                    break;
                case 6:
                    v2=c6;
                    break;
                case 7:
                    v2=c7;
                    break;

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

        public class datasearch extends AsyncTask{
           private Nothelper mNotificationHelper;
           public datasearch(Context context)
           {
              mNotificationHelper = new Nothelper(context);
           }

           @Override
           protected void onPreExecute() {
               mNotificationHelper.createNotification();
              // Log.e("connection status","started connecting with server");
           }

           @Override
           protected void onPostExecute(Object o) {
               mNotificationHelper.completed();
               SharedPreferences.Editor editor = sharedpreferences.edit();
               editor.putString(mirna, mi);
               editor.putString(mechanism, mec);
               editor.putString(desease, des);
               editor.putString(pathway,path);
               editor.putString(etilogy,eti);
               editor.apply();
               if (alldataentered) {
                   Intent call_page = new Intent(getApplicationContext(), data.class);
                   startActivity(call_page);
               }
               else
               {
                   Toast.makeText(getApplicationContext(),"sorry data not available",Toast.LENGTH_LONG).show();
               }
           }

           @Override
           protected void onProgressUpdate(Object[] values) {
           }

           @Override
           protected Object doInBackground(Object[] objects) {

               InputStream is = null;
               String v1 = editText.getText().toString();
               ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

               nameValuePairs.add(new BasicNameValuePair("f1",v1));
               nameValuePairs.add(new BasicNameValuePair("f2",v2));
               try
               {
                   HttpClient httpclient = new DefaultHttpClient();
                   HttpPost httppost = new HttpPost("http://52.37.65.243/select.php");
                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                   HttpResponse response = httpclient.execute(httppost);
                   HttpEntity entity = response.getEntity();
                   is = entity.getContent();
                   BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                   StringBuilder sb = new StringBuilder();
                   String line = null;
                   while ((line = reader.readLine()) != null)
                   {
                       sb.append(line + "\n");

                   }
                   is.close();

                   result=sb.toString();


               }
               catch(Exception e)
               {
                   Log.e("log_tag", "Error in http connection "+e.toString());

               }

               try{
                   JSONObject object = new JSONObject(result);
                   String ch=object.getString("re");
                   //Log.e("data",ch);
                   if(ch.equals("success"))
                   {
                       alldataentered = true;

                       JSONObject no = object.getJSONObject("0");

                       mi = no.getString("f1");
                       mec= no.getString("f2");
                       des=no.getString("f3");
                        path = no.getString("f4");
                       eti = no.getString("f5");


                   }


                  else
                   {
                       alldataentered = false;
                   }


               }
               catch(JSONException e)
               {
                   Log.e("log_tag", "Error parsing data "+e.toString());

               }

               return null;
           }
       }
}
