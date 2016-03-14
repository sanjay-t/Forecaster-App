package com.weather.sanjay.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    public static ImageView forecast_img_id;
    public static EditText street_val;
    public static EditText city_val;
    public static Button submit;
    public static TextView error_id;
    public static Spinner state_val;
    public static RadioButton radioDeg;
    public static RadioButton radioDeg1;
    public static Button about;
    public static JSONObject jsonout;
    public static int flag = 0;
    public static int crasher = 1;
    public static String deg = null;
    public Button reset;
    public RadioGroup radioGroup;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        FacebookSdk.sdkInitialize(getApplicationContext());
        forecast_img_id = (ImageView) findViewById(R.id.forecast_img_id);
        forecast_img_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgchanger();
            }
        });
        about = (Button) findViewById(R.id.abt);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                about();
            }
        });

        validator();
        reset();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void reset() {
        street_val = (EditText) findViewById(R.id.street_val);
        city_val = (EditText) findViewById(R.id.city_val);
        error_id = (TextView) findViewById(R.id.error_id);
        submit = (Button) findViewById(R.id.submit);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioDeg = (RadioButton) findViewById(R.id.radioDeg);
        radioDeg1 = (RadioButton) findViewById(R.id.radioDeg1);
        state_val = (Spinner) findViewById(R.id.state_val);
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city_val.setText("");
                street_val.setText("");
                state_val.setSelection(0);
                radioGroup.check(R.id.radioDeg);
            }
        });
    }

    private void about() {
        Intent intent1 = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent1);

    }

    public void validator() {
        street_val = (EditText) findViewById(R.id.street_val);
        city_val = (EditText) findViewById(R.id.city_val);
        error_id = (TextView) findViewById(R.id.error_id);
        submit = (Button) findViewById(R.id.submit);
        radioDeg = (RadioButton) findViewById(R.id.radioDeg);
        radioDeg1 = (RadioButton) findViewById(R.id.radioDeg1);
        state_val = (Spinner) findViewById(R.id.state_val);
        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (street_val.getText().toString().equals("")) {
                            error_id.setText("Please enter Street address");
                            crasher = 0;
                        } else if (city_val.getText().toString().equals("")) {
                            error_id.setText("Please enter City");
                            crasher = 0;
                        } else if (state_val.getSelectedItem().toString().equals("Select a state")) {
                            error_id.setText("Please select a state");
                            crasher = 0;
                        } else {
                            error_id.setText("");
                            crasher = 1;
                        }

                        if (radioDeg.isChecked())
                            deg = new String("us");
                        else if (radioDeg1.isChecked())
                            deg = new String("si");

                        if (crasher == 1) {
                            Connector c = new Connector(street_val.getText().toString(), city_val.getText().toString(), state_val.getSelectedItem().toString(), deg);
                            c.execute();
                            try {
                                jsonout = c.get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                            display(jsonout);

                        }
                    }
                }
        );

    }

    private void display(JSONObject jsonout) {
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("json", jsonout.toString());
        intent.putExtra("cval", city_val.getText().toString());
        intent.putExtra("sval", state_val.getSelectedItem().toString());
        intent.putExtra("deg", deg);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        startActivity(intent);
    }

    public void imgchanger() {
        Intent intentc = new Intent();
        intentc.setAction(Intent.ACTION_VIEW);
        intentc.addCategory(Intent.CATEGORY_BROWSABLE);
        intentc.setData(Uri.parse("http://forecast.io"));
        startActivity(intentc);
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
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.weather.sanjay.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.weather.sanjay.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
