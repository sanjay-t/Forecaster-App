package com.weather.sanjay.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {
    public String d;
    public JSONObject out;
    public static TextView precip_id, crain_id, wspeed_id, dpoint_id, humid_id, vis_id, srise_id, sset_id, summary_ResultActivity, temp_ResultActivity, lohi_ResultActivity;
    public TextView dis;
    public static ShareDialog shareDialog;
    public static String city_val, state_val, deg;
    public static Button mdetails;
    public static ImageView fb;
    public static Button vmap;
    public static ImageView currentimage;
    public CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultactivity);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Intent getJsonObj= getIntent();
        state_val=getJsonObj.getStringExtra("sval");
        city_val=getJsonObj.getStringExtra("cval");
        deg=getJsonObj.getStringExtra("deg");
        try {
            out = new JSONObject(getJsonObj.getStringExtra("json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            displayer(out);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        moredetails(out);
        fb = (ImageView)findViewById(R.id.fb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sharer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        vmap=(Button)findViewById(R.id.vmap);
        vmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapstart();
            }
        });
    }

    private void mapstart() {
        Intent in = new Intent(ResultActivity.this,MapsActivity.class);
        startActivity(in);
    }

    private void sharer() throws JSONException {
        String forc = out.getString("forc");
        JSONObject forcobj = new JSONObject(forc);
        Log.d("FORC", String.valueOf(forcobj));
        String currently = forcobj.getString("currently");
        JSONObject currentlyobj = new JSONObject(currently);
        String curimg = currentlyobj.getString("icon");
        String furl = null;
        switch (curimg){
            case "clear-day": furl="http://cs-server.usc.edu:45678/hw/hw8/images/clear.png";break;
            case "clear-night": furl="http://cs-server.usc.edu:45678/hw/hw8/images/clear_night.png";break;
            case "rain": furl="http://cs-server.usc.edu:45678/hw/hw8/images/rain.png";break;
            case "snow": furl="http://cs-server.usc.edu:45678/hw/hw8/images/snow.png";break;
            case "sleet": furl="http://cs-server.usc.edu:45678/hw/hw8/images/sleet.png";break;
            case "wind":furl="http://cs-server.usc.edu:45678/hw/hw8/images/wind.png" ;break;
            case "fog": furl="http://cs-server.usc.edu:45678/hw/hw8/images/fog.png";break;
            case "cloudy": furl="http://cs-server.usc.edu:45678/hw/hw8/images/cloudy.png";break;
            case "partly-cloudy-night": furl="http://cs-server.usc.edu:45678/hw/hw8/images/cloud_night.png";break;
            case "partly-cloudy-day": furl="http://cs-server.usc.edu:45678/hw/hw8/images/cloud_day.png";break;
        }
        String sum = currentlyobj.getString("summary");
        int w = currentlyobj.getInt("temperature");
        String pt;
        if(deg.equals("us")){
            pt= sum+", "+w+" \u2109";
        }
        else
        pt=sum+", "+w+" \u2103";

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Current weather in "+city_val+", "+state_val)
                    .setContentDescription(
                            pt)
                    .setContentUrl(Uri.parse("http://forecast.io"))
                    .setImageUrl(Uri.parse(furl))
                    .build();

            shareDialog.show(linkContent);
        }
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        Toast.makeText(ResultActivity.this,
                "Posted Successfully", Toast.LENGTH_LONG).show();
        else if(resultCode==RESULT_CANCELED)
            Toast.makeText(ResultActivity.this,
                    "Post Cancelled", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(ResultActivity.this,
                    "Post Cancelled", Toast.LENGTH_LONG).show();

    }

    private void moredetails(final JSONObject out) {
        mdetails=(Button)findViewById(R.id.mdetails);
        mdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moredetails = new Intent(ResultActivity.this, DetailsActivity.class);
                moredetails.putExtra("out", String.valueOf(out));
                moredetails.putExtra("cname", city_val);
                moredetails.putExtra("sname", state_val);
                moredetails.putExtra("deg", deg);
                startActivity(moredetails);
            }
        });
    }

    private void displayer(JSONObject out) throws JSONException {
        Log.d("LATI", out.getString("lati"));
        String forc = out.getString("forc");
        JSONObject forcobj = new JSONObject(forc);
        Log.d("FORC", String.valueOf(forcobj));
        String currently = forcobj.getString("currently");
        JSONObject currentlyobj = new JSONObject(currently);
        String hourly = forcobj.getString("hourly");
        JSONObject hourlyobj = new JSONObject(hourly);
        Log.d("HOURLYOBJECT", String.valueOf(hourlyobj));
        String daily = forcobj.getString("daily");
        JSONObject dailyobj = new JSONObject(daily);
        Log.d("DAILYOBJECT", String.valueOf(dailyobj));
        temp_ResultActivity=(TextView)findViewById(R.id.temp_ResultActivity);
        precip_id=(TextView)findViewById(R.id.precip_id);
        crain_id=(TextView)findViewById(R.id.crain_id);
        wspeed_id=(TextView)findViewById(R.id.wspeed_id);
        dpoint_id=(TextView)findViewById(R.id.dpoint_id);
        humid_id=(TextView)findViewById(R.id.humid_id);
        vis_id=(TextView)findViewById(R.id.vis_id);
        srise_id=(TextView)findViewById(R.id.srise_id);
        sset_id=(TextView)findViewById(R.id.sset_id);
        currentimage=(ImageView)findViewById(R.id.currentimage);
        String curimg = currentlyobj.getString("icon");
        switch (curimg){
            case "clear-day": currentimage.setImageResource(R.drawable.clear);break;
            case "clear-night": currentimage.setImageResource(R.drawable.clear_night);break;
            case "rain": currentimage.setImageResource(R.drawable.rain);break;
            case "snow": currentimage.setImageResource(R.drawable.snow);break;
            case "sleet": currentimage.setImageResource(R.drawable.sleet);break;
            case "wind": currentimage.setImageResource(R.drawable.wind);break;
            case "fog": currentimage.setImageResource(R.drawable.fog);break;
            case "cloudy": currentimage.setImageResource(R.drawable.cloudy);break;
            case "partly-cloudy-night": currentimage.setImageResource(R.drawable.cloud_night);break;
            case "partly-cloudy-day": currentimage.setImageResource(R.drawable.cloud_day);break;
        }
        summary_ResultActivity=(TextView)findViewById(R.id.summary_ResultActivity);
        String sumRes = currentlyobj.getString("summary")+" in "+city_val+", "+state_val;
        summary_ResultActivity.setText(sumRes);
        if(deg.equals("us"))
            temp_ResultActivity.setText((currentlyobj.getInt("temperature"))+" \u2109");
        else
            temp_ResultActivity.setText((currentlyobj.getInt("temperature"))+" \u2103");
        lohi_ResultActivity=(TextView)findViewById(R.id.lohi_ResultActivity);
        JSONArray temp = dailyobj.getJSONArray("data");
        Log.d("JSONARRAY TEMP DATA", String.valueOf(temp));
        int maxTemp = temp.getJSONObject(0).getInt("temperatureMax");
        int minTemp = temp.getJSONObject(0).getInt("temperatureMin");
        String temp_sum = "L : "+(minTemp)+" \u00B0"+" | H : "+(maxTemp)+" \u00B0";
        lohi_ResultActivity.setText(temp_sum);
        Double pint = currentlyobj.getDouble("precipIntensity");
        Log.d("Precipitation Intensity", String.valueOf(pint));
        if(pint>=0 && pint<0.002)
            precip_id.setText("None");
        else if(pint>=0.002 && pint<0.017)
            precip_id.setText("Very Light");
        else if(pint>=0.017 && pint<0.1)
            precip_id.setText("Light");
        else if(pint>=0.1 && pint < 0.4)
            precip_id.setText("Moderate");
        else if(pint>=0.4)
            precip_id.setText("Heavy");
        crain_id.setText((currentlyobj.getInt("precipProbability")*100)+" %");
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        if(deg.equals("us"))
            wspeed_id.setText(df.format(currentlyobj.getDouble("windSpeed"))+" mph");
        else if(deg.equals("si"))
            wspeed_id.setText(df.format(currentlyobj.getDouble("windSpeed"))+" m/s");
        if(deg.equals("us"))
            dpoint_id.setText(df.format(currentlyobj.getDouble("dewPoint"))+" \u2109");
        else if(deg.equals("si"))
            dpoint_id.setText(df.format(currentlyobj.getDouble("dewPoint"))+" \u2103");
        humid_id.setText((currentlyobj.getInt("humidity")*100)+" %");
        if(deg.equals("us"))
        vis_id.setText(String.valueOf( df.format(Float.parseFloat(currentlyobj.getString("visibility"))))+" mi");
        else if(deg.equals("si"))
            vis_id.setText(String.valueOf( df.format(Float.parseFloat(currentlyobj.getString("visibility"))))+" km");
        srise_id.setText(out.getString("sr").replaceAll("\"",""));
        sset_id.setText(out.getString("ss").replaceAll("\"",""));
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
