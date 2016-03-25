package imaginatio.databasetesting;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
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



    public class MainActivity extends Activity
        
       
    {
        //////////////////////////

        HttpClient httpclient;
        HttpPost httppost;
        String g;
        //////////////////////////
        TextView myview;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        EditText editText;
       TextView editText2;
        Button testbutton;
        Context myne;
        String result = null;
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            myview = (TextView)findViewById(R.id.hello);
            testbutton = (Button)findViewById(R.id.button);


            editText = (EditText)findViewById(R.id.e1);
           final TextView editText1 = (TextView)findViewById(R.id.e2);

          //  editText2 = (TextView)findViewById(R.id.e3);
         //   final TextView editText3 = (TextView)findViewById(R.id.e4);
            Button button = (Button) findViewById(R.id.button1);
            StrictMode.setThreadPolicy(policy);

            button.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View view)
                {

                    String result = null;
                    InputStream is = null;
                    String v1 = editText.getText().toString();
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                    nameValuePairs.add(new BasicNameValuePair("f1",v1));
                    try
                    {
                        
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();

                        Log.e("log_tag", "connection success ");

                    }
                    catch(Exception e)
                    {
                        Log.e("log_tag", "Error in http connection "+e.toString());
                        Toast.makeText(getApplicationContext(), "Connection fail", Toast.LENGTH_SHORT).show();

                    }
                    //convert response to string
                    try{
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null)
                        {
                            sb.append(line + "\n");

                        }
                        is.close();

                        result=sb.toString();
                        Log.e("result",result);
                    }
                    catch(Exception e)
                    {
                        Log.e("log_tag", "Error converting result "+e.toString());

                        Toast.makeText(getApplicationContext(), " Input reading fail", Toast.LENGTH_SHORT).show();

                    }

                    //parse json data
                    try{
                        JSONObject object = new JSONObject(result);
                        String ch=object.getString("re");
                       Log.e("data",ch);
                        if(ch.equals("success"))
                        {

                            JSONObject no = object.getJSONObject("0");

                            //long q=object.getLong("f1");
                            String x = no.getString("f1");
                            String w= no.getString("f2");
                            String e=no.getString("f3");
                            String g = no.getString("f4");
                            Log.e("name",w);

                           // editText.setText(x);
                           editText1.setText(x);
                          //  editText2.setText(h);
                         //   editText3.setText(e);
                            String h= "000000000000000000000000000hhjdfhjksdahfsdjahfjklashdflkhasddlkfhaksdhfkhsadfkhaskdfhaksdhfkashdfhasddlfhalskddhflkjasddhflkjhasdklfhasldjkhfljasddhflkj";

                            myview.setText(w);

                        }


                        else
                        {

                            Toast.makeText(getApplicationContext(), "Record is not available.. Enter valid number", Toast.LENGTH_SHORT).show();

                        }


                    }
                    catch(JSONException e)
                    {
                        Log.e("log_tag", "Error parsing data "+e.toString());
                        Toast.makeText(getApplicationContext(), "JsonArray fail", Toast.LENGTH_SHORT).show();
                    }


                }
            });




        }

        public void start(View view) {
            new datasearch(getApplicationContext()).execute();
            Log.e("message","started database");

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
               Log.e("connection status","started connecting with server");
           }

           @Override
           protected void onPostExecute(Object o) {
               mNotificationHelper.completed();
               //Log.e("log_tag", "connection success ");
               Log.e("result",g);
           }

           @Override
           protected void onProgressUpdate(Object[] values) {
           }

           @Override
           protected Object doInBackground(Object[] objects) {

               InputStream is = null;
               String v1 = "cardiac";
               ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

               nameValuePairs.add(new BasicNameValuePair("f1",v1));
               try
               {
                   HttpClient httpclient = new DefaultHttpClient();
                   HttpPost httppost = new HttpPost("http://192.168.22.130/select.php");
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
                //  Toast.makeText(getApplicationContext(), "Connection fail", Toast.LENGTH_SHORT).show();

               }

               try{
                   JSONObject object = new JSONObject(result);
                   String ch=object.getString("re");
                   Log.e("data",ch);
                   if(ch.equals("success"))
                   {

                       JSONObject no = object.getJSONObject("0");

                       //long q=object.getLong("f1");
                       String x = no.getString("f1");
                       String w= no.getString("f2");
                       String e=no.getString("f3");
                        g = no.getString("f4");


                   }


                   else
                   {

                    //   Toast.makeText(getApplicationContext(), "Record is not available.. Enter valid number", Toast.LENGTH_SHORT).show();

                   }


               }
               catch(JSONException e)
               {
                   Log.e("log_tag", "Error parsing data "+e.toString());
                  // Toast.makeText(getApplicationContext(), "JsonArray fail", Toast.LENGTH_SHORT).show();
               }

               return null;
           }
       }
}
//all updated