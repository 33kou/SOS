package prgc.snct.sos.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import prgc.snct.sos.R;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);
        Button button4 = (Button)findViewById(R.id.button4);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){

            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            //intent.setClassName("sos.Activities", "sos.Activities.MapsActivity");
            startActivity(intent);

        }else if(v.getId() == R.id.button2){

            Intent intent = new Intent(MainActivity.this,BluetoothMain.class);
            //intent.setClassName("prgc.snct.sos.Activities", "prgc.snct.sos.Activities.BluetoothMain");
            startActivity(intent);

        }else if(v.getId() == R.id.button3) {

            Intent intent = new Intent(MainActivity.this,DatabaseMain.class);
            //intent.setClassName("prgc.snct.sos.Activities", "prgc.snct.sos.Activities.DatabaseMain");
            startActivity(intent);

        }else if(v.getId() == R.id.button4) {

            Intent intent = new Intent(MainActivity.this,TransceiverMain.class);
            startActivity(intent);

        }
    }
}
