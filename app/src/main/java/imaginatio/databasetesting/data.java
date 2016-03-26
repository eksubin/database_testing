package imaginatio.databasetesting;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class data extends ActionBarActivity {
TextView mech,mir,des,path,eti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        mech = (TextView)findViewById(R.id.mechanism_dats);
        mir = (TextView)findViewById(R.id.mirna_data);
        des = (TextView)findViewById(R.id.disease_data);
        path = (TextView)findViewById(R.id.pathway_data);
        eti = (TextView)findViewById(R.id.etilogy_data);


        String mirna = prefs.getString("mir", null);
        String mechanism = prefs.getString("mech", null);
        String desease = prefs.getString("dese", null);
        String pathways = prefs.getString("path", null);
        String etilogy = prefs.getString("etil",null);

        mech.setText(mechanism);
        mir.setText(mirna);
        des.setText(desease);
        path.setText(pathways);
        eti.setText(etilogy);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
