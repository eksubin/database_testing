package imaginatio.databasetesting;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class first_page extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        new Thread(new BarUpdate()).start();
    }

    private class BarUpdate implements Runnable {

        @Override
        public void run() {
            int value = 0;
            for (int i = 0; i <= 60; i++) {
                try {
                    Thread.sleep(60);
                    value = i;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(value == 60) {
                    Intent myinte = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(myinte);
                    finish();

                }
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_page, menu);
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
}
