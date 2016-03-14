package com.weather.sanjay.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class DetailsActivity extends AppCompatActivity {

    public JSONObject outval;
    public TextView mdetails24;
    public String state_val;
    public String city_val;
    public String deg_val;
    public Button n24, n7;
    public LinearLayout detlay1;
    public LinearLayout linlayinflate;
    public LinearLayout li1;
    public View hiddenInfo;
    public View hiddenInfo1;
    public java.util.ArrayList <TextView> listtexttime = new java.util.ArrayList<TextView>();
    public java.util.ArrayList <ImageView> listimg = new java.util.ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        detlay1=(LinearLayout)findViewById(R.id.detlay1);
        li1=(LinearLayout)findViewById(R.id.li1);
        linlayinflate=(LinearLayout)findViewById(R.id.linlayinflate);
        Intent getval = getIntent();
        state_val=getval.getStringExtra("sname");
        city_val=getval.getStringExtra("cname");
        deg_val=getval.getStringExtra("deg");
        try {
            outval= new JSONObject(getval.getStringExtra("out"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            initializer(outval);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        n24=(Button)findViewById(R.id.n24);
        n7=(Button)findViewById(R.id.n7);
        n24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init(outval);
                n24.setBackground(getResources().getDrawable(R.drawable.btn_blue_glossy));
                n7.setBackground(getResources().getDrawable(R.drawable.btn_white_glossy));
            }
        });
        n7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n7.setBackground(getResources().getDrawable(R.drawable.btn_blue_glossy));
                n24.setBackground(getResources().getDrawable(R.drawable.btn_white_glossy));

                inti7(outval);
            }
        });
        disp24(outval);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
    private void disp24(final JSONObject outval) {
        mdetails24=(TextView)findViewById(R.id.mdetails24);
        mdetails24.setText("More details for " + city_val + ", " + state_val);
    }

    private void populated(JSONObject outval) throws JSONException {
        String forc= null;
        forc=outval.getString("forc");
        JSONObject forcobj = new JSONObject(forc);
        String day = forcobj.getString("daily");
        JSONObject dayobj = new JSONObject(day);
        JSONArray dayarr = dayobj.getJSONArray("data");
        TextView wkt1 = null, wktt1, wkt2 = null, wktt2, wkt3 = null, wktt3, wkt4 = null, wktt4, wkt5 = null, wktt5, wkt6 = null, wktt6, wkt7 = null, wktt7;
        ImageView wki1 = null,wki2 = null,wki3 = null,wki4 = null,wki5 = null,wki6 = null,wki7=null;
        wkt1=(TextView)findViewById(R.id.wkt1);
        wktt1=(TextView)findViewById(R.id.wktt1);
        wkt2=(TextView)findViewById(R.id.wkt2);
        wktt2=(TextView)findViewById(R.id.wktt2);
        wkt3=(TextView)findViewById(R.id.wkt3);
        wktt3=(TextView)findViewById(R.id.wktt3);
        wkt4=(TextView)findViewById(R.id.wkt4);
        wktt4=(TextView)findViewById(R.id.wktt4);
        wkt5=(TextView)findViewById(R.id.wkt5);
        wktt5=(TextView)findViewById(R.id.wktt5);
        wkt6=(TextView)findViewById(R.id.wkt6);
        wktt6=(TextView)findViewById(R.id.wktt6);
        wkt7=(TextView)findViewById(R.id.wkt7);
        wktt7=(TextView)findViewById(R.id.wktt7);
        wki1=(ImageView)findViewById(R.id.wki1);
        wki2=(ImageView)findViewById(R.id.wki2);
        wki3=(ImageView)findViewById(R.id.wki3);
        wki4=(ImageView)findViewById(R.id.wki4);
        wki5=(ImageView)findViewById(R.id.wki5);
        wki6=(ImageView)findViewById(R.id.wki6);
        wki7=(ImageView)findViewById(R.id.wki7);

        SimpleDateFormat s1= new SimpleDateFormat("MMM");
        SimpleDateFormat s2 = new SimpleDateFormat("EEEE");
        SimpleDateFormat s3 = new SimpleDateFormat("d");
        String dm;
        String dw;
        String dd;
        Date d;
        String ic;
        listtexttime.clear();
        listtexttime.add(wktt1);
        listtexttime.add(wktt2);
        listtexttime.add(wktt3);
        listtexttime.add(wktt4);
        listtexttime.add(wktt5);
        listtexttime.add(wktt6);
        listtexttime.add(wktt7);
        if(deg_val.equals("us")) {
            int k = 0;
            for (TextView t : listtexttime) {
                t.setText("Min : " + dayarr.getJSONObject(k).getString("temperatureMin") + "\u2109 | Max :" + dayarr.getJSONObject(k).getString("temperatureMax") + " \u2109");
                k++;
            }
        }
        else{
            if(deg_val.equals("si")) {
                int k = 0;
                for (TextView t : listtexttime) {
                    t.setText("Min : " + dayarr.getJSONObject(k).getString("temperatureMin") + "\u2103 | Max :" + dayarr.getJSONObject(k).getString("temperatureMax") + " \u2103");
                    k++;
                }
            }
        }
        listtexttime.clear();
        listtexttime.add(wkt1);
        listtexttime.add(wkt2);
        listtexttime.add(wkt3);
        listtexttime.add(wkt4);
        listtexttime.add(wkt5);
        listtexttime.add(wkt6);
        listtexttime.add(wkt7);
            int k=0;
            for(TextView t: listtexttime){
                d = new Date((long) dayarr.getJSONObject(k).getInt("time") * 1000);
                dm = s1.format(d);
                dw = s2.format(d);
                dd = s3.format(d);
                t.setText(dw + ", " + dm + " " + dd);
                k++;
            }
        listtexttime.clear();
        listimg.clear();
        listimg.add(wki1);
        listimg.add(wki2);
        listimg.add(wki3);
        listimg.add(wki4);
        listimg.add(wki5);
        listimg.add(wki6);
        listimg.add(wki7);
        int i=0;
        for(ImageView a: listimg){
            ic = dayarr.getJSONObject(i).getString("icon");
            switch (ic){
                case "clear-day": a.setImageResource(R.drawable.clear);break;
                case "clear-night": a.setImageResource(R.drawable.clear_night);break;
                case "rain": a.setImageResource(R.drawable.rain);break;
                case "snow": a.setImageResource(R.drawable.snow);break;
                case "sleet": a.setImageResource(R.drawable.sleet);break;
                case "wind": a.setImageResource(R.drawable.wind);break;
                case "fog": a.setImageResource(R.drawable.fog);break;
                case "cloudy": a.setImageResource(R.drawable.cloudy);break;
                case "partly-cloudy-night": a.setImageResource(R.drawable.cloud_night);break;
                case "partly-cloudy-day": a.setImageResource(R.drawable.cloud_day);break;
            }
            i++;
        }
        listimg.clear();
       }

    private void initializer(JSONObject outval) throws JSONException {
        String forc = null;
        try {
            forc = outval.getString("forc");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject forcobj = null;
        try {
            forcobj= new JSONObject(forc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String hourly = forcobj.getString("hourly");
        JSONObject hourlyobj = new JSONObject(hourly);
        JSONArray hourlyArray = hourlyobj.getJSONArray("data");
        String hourlyTime = outval.getString("srd");
        JSONArray hsrd = new JSONArray(hourlyTime);
        TextView time241,time242,time243,time244,time245,time246,time247,temp241,temp242,temp243,temp244,temp245,temp246,temp247;
        ImageView sum241,sum242,sum243,sum244,sum245,sum246,sum247;
        time241=(TextView)findViewById(R.id.time241);
        temp241=(TextView)findViewById(R.id.temp241);

        time242=(TextView)findViewById(R.id.time242);
        temp242=(TextView)findViewById(R.id.temp242);

        time243=(TextView)findViewById(R.id.time243);
        temp243=(TextView)findViewById(R.id.temp243);

        time244=(TextView)findViewById(R.id.time244);
        temp244=(TextView)findViewById(R.id.temp244);

        time245=(TextView)findViewById(R.id.time245);
        temp245=(TextView)findViewById(R.id.temp245);

        time246=(TextView)findViewById(R.id.time246);
        temp246=(TextView)findViewById(R.id.temp246);

        time247=(TextView)findViewById(R.id.time247);
        temp247=(TextView)findViewById(R.id.temp247);
        sum241=(ImageView)findViewById(R.id.sum241);
        sum242=(ImageView)findViewById(R.id.sum242);
        sum243=(ImageView)findViewById(R.id.sum243);
        sum244=(ImageView)findViewById(R.id.sum244);
        sum245=(ImageView)findViewById(R.id.sum245);
        sum246=(ImageView)findViewById(R.id.sum246);
        sum247=(ImageView)findViewById(R.id.sum247);
        listimg.clear();
        listimg.add(sum241);
        listimg.add(sum242);
        listimg.add(sum243);
        listimg.add(sum244);
        listimg.add(sum245);
        listimg.add(sum246);
        listimg.add(sum247);
        int i=0;
        String ic;
        for(ImageView a: listimg){
            ic = hourlyArray.getJSONObject(i).getString("icon");
            switch (ic){
                case "clear-day": a.setImageResource(R.drawable.clear);break;
                case "clear-night": a.setImageResource(R.drawable.clear_night);break;
                case "rain": a.setImageResource(R.drawable.rain);break;
                case "snow": a.setImageResource(R.drawable.snow);break;
                case "sleet": a.setImageResource(R.drawable.sleet);break;
                case "wind": a.setImageResource(R.drawable.wind);break;
                case "fog": a.setImageResource(R.drawable.fog);break;
                case "cloudy": a.setImageResource(R.drawable.cloudy);break;
                case "partly-cloudy-night": a.setImageResource(R.drawable.cloud_night);break;
                case "partly-cloudy-day": a.setImageResource(R.drawable.cloud_day);break;
            }
            i++;
        }
        listimg.clear();

        int k=0;
        listtexttime.clear();
        listtexttime.add(time241);
        listtexttime.add(time242);
        listtexttime.add(time243);
        listtexttime.add(time244);
        listtexttime.add(time245);
        listtexttime.add(time246);
        listtexttime.add(time247);
        for(TextView t : listtexttime){
            t.setText(hsrd.get(k).toString());
            k++;
        }
    }


    private void populater(JSONObject outval) throws JSONException {
        String forc = null;
        try {
            forc = outval.getString("forc");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject forcobj = null;
        try {
            forcobj= new JSONObject(forc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String hourly = forcobj.getString("hourly");
        JSONObject hourlyobj = new JSONObject(hourly);
        JSONArray hourlyArray = hourlyobj.getJSONArray("data");
        String hourlyTime = outval.getString("srd");
        JSONArray hsrd = new JSONArray(hourlyTime);
        TextView time248,time249,time2410,time2411,time2412,time2413,time2414,time2415,time2416,time2417,time2418,time2419,time2420,time2421,time2422,time2423,time2424,temp248,temp249,temp2410,temp2411,temp2412,temp2413,temp2414,temp2415,temp2416,temp2417,temp2418,temp2419,temp2420,temp2421,temp2422,temp2423,temp2424;
        ImageView sum248,sum249,sum2410,sum2411,sum2412,sum2413,sum2414,sum2415,sum2416,sum2417,sum2418,sum2419,sum2420,sum2421,sum2422,sum2423,sum2424;

        time248=(TextView)findViewById(R.id.time248);
        temp248=(TextView)findViewById(R.id.temp248);

        time249=(TextView)findViewById(R.id.time249);
        temp249=(TextView)findViewById(R.id.temp249);

        time2410=(TextView)findViewById(R.id.time2410);
        temp2410=(TextView)findViewById(R.id.temp2410);

        time2411=(TextView)findViewById(R.id.time2411);
        temp2411=(TextView)findViewById(R.id.temp2411);

        time2412=(TextView)findViewById(R.id.time2412);
        temp2412=(TextView)findViewById(R.id.temp2412);

        time2413=(TextView)findViewById(R.id.time2413);
        temp2413=(TextView)findViewById(R.id.temp2413);

        time2414=(TextView)findViewById(R.id.time2414);
        temp2414=(TextView)findViewById(R.id.temp2414);

        time2415=(TextView)findViewById(R.id.time2415);
        temp2415=(TextView)findViewById(R.id.temp2415);

        time2416=(TextView)findViewById(R.id.time2416);
        temp2416=(TextView)findViewById(R.id.temp2416);

        time2417=(TextView)findViewById(R.id.time2417);
        temp2417=(TextView)findViewById(R.id.temp2417);

        time2418=(TextView)findViewById(R.id.time2418);
        temp2418=(TextView)findViewById(R.id.temp2418);

        time2419=(TextView)findViewById(R.id.time2419);
        temp2419=(TextView)findViewById(R.id.temp2419);

        time2420=(TextView)findViewById(R.id.time2420);
        temp2420=(TextView)findViewById(R.id.temp2420);

        time2421=(TextView)findViewById(R.id.time2421);
        temp2421=(TextView)findViewById(R.id.temp2421);

        time2422=(TextView)findViewById(R.id.time2422);
        temp2422=(TextView)findViewById(R.id.temp2422);

        time2423=(TextView)findViewById(R.id.time2423);
        temp2423=(TextView)findViewById(R.id.temp2423);

        time2424=(TextView)findViewById(R.id.time2424);
        temp2424=(TextView)findViewById(R.id.temp2424);


        sum248=(ImageView)findViewById(R.id.sum248);
        sum249=(ImageView)findViewById(R.id.sum249);
        sum2410=(ImageView)findViewById(R.id.sum2410);
        sum2411=(ImageView)findViewById(R.id.sum2411);
        sum2412=(ImageView)findViewById(R.id.sum2412);
        sum2413=(ImageView)findViewById(R.id.sum2413);
        sum2414=(ImageView)findViewById(R.id.sum2414);
        sum2415=(ImageView)findViewById(R.id.sum2415);
        sum2416=(ImageView)findViewById(R.id.sum2416);
        sum2417=(ImageView)findViewById(R.id.sum2417);
        sum2418=(ImageView)findViewById(R.id.sum2418);
        sum2419=(ImageView)findViewById(R.id.sum2419);
        sum2420=(ImageView)findViewById(R.id.sum2420);
        sum2421=(ImageView)findViewById(R.id.sum2421);
        sum2422=(ImageView)findViewById(R.id.sum2422);
        sum2424=(ImageView)findViewById(R.id.sum2424);
        sum2423=(ImageView)findViewById(R.id.sum2423);

        listimg.clear();
        listimg.add(sum248);
        listimg.add(sum249);
        listimg.add(sum2410);
        listimg.add(sum2411);
        listimg.add(sum2412);
        listimg.add(sum2413);
        listimg.add(sum2414);
        listimg.add(sum2415);
        listimg.add(sum2416);
        listimg.add(sum2417);
        listimg.add(sum2418);
        listimg.add(sum2419);
        listimg.add(sum2420);
        listimg.add(sum2421);
        listimg.add(sum2422);
        listimg.add(sum2423);
        listimg.add(sum2424);


        int i=0;
        String ic;
        for(ImageView a: listimg){
            ic = hourlyArray.getJSONObject(i).getString("icon");
            switch (ic){
                case "clear-day": a.setImageResource(R.drawable.clear);break;
                case "clear-night": a.setImageResource(R.drawable.clear_night);break;
                case "rain": a.setImageResource(R.drawable.rain);break;
                case "snow": a.setImageResource(R.drawable.snow);break;
                case "sleet": a.setImageResource(R.drawable.sleet);break;
                case "wind": a.setImageResource(R.drawable.wind);break;
                case "fog": a.setImageResource(R.drawable.fog);break;
                case "cloudy": a.setImageResource(R.drawable.cloudy);break;
                case "partly-cloudy-night": a.setImageResource(R.drawable.cloud_night);break;
                case "partly-cloudy-day": a.setImageResource(R.drawable.cloud_day);break;
            }
            i++;
        }
        listimg.clear();


        /*
       int m=0;
        for(ImageView v:listimg){
            String ic = hourlyArray.getJSONObject(m).getString("icon");
            switch (ic){
                case "clear-day": v.setImageResource(R.drawable.clear);break;
                case "clear-night": v.setImageResource(R.drawable.clear_night);break;
                case "rain": v.setImageResource(R.drawable.rain);break;
                case "snow": v.setImageResource(R.drawable.snow);break;
                case "sleet": v.setImageResource(R.drawable.sleet);break;
                case "wind": v.setImageResource(R.drawable.wind);break;
                case "fog": v.setImageResource(R.drawable.fog);break;
                case "cloudy": v.setImageResource(R.drawable.cloudy);break;
                case "partly-cloudy-night": v.setImageResource(R.drawable.cloud_night);break;
                case "partly-cloudy-day": v.setImageResource(R.drawable.cloud_day);break;
                default:v.setImageResource(R.drawable.clear);
            }
            m++;
        }*/
        listimg.clear();

        int k=0;
        listtexttime.clear();
        listtexttime.add(time248);
        listtexttime.add(time249);
        listtexttime.add(time2410);
        listtexttime.add(time2411);
        listtexttime.add(time2412);
        listtexttime.add(time2413);
        listtexttime.add(time2414);
        listtexttime.add(time2415);
        listtexttime.add(time2416);
        listtexttime.add(time2417);
        listtexttime.add(time2418);
        listtexttime.add(time2419);
        listtexttime.add(time2420);
        listtexttime.add(time2421);
        listtexttime.add(time2422);
        listtexttime.add(time2423);
        listtexttime.add(time2424);
        k=0;
        for(TextView t : listtexttime){
            t.setText(hsrd.get(k).toString());
            k++;
        }
        listtexttime.clear();
        listtexttime.add(temp248);
        listtexttime.add(temp249);
        listtexttime.add(temp2410);
        listtexttime.add(temp2411);
        listtexttime.add(temp2412);
        listtexttime.add(temp2413);
        listtexttime.add(temp2414);
        listtexttime.add(temp2415);
        listtexttime.add(temp2416);
        listtexttime.add(temp2417);
        listtexttime.add(temp2418);
        listtexttime.add(temp2419);
        listtexttime.add(temp2420);
        listtexttime.add(temp2421);
        listtexttime.add(temp2422);
        listtexttime.add(temp2423);
        listtexttime.add(temp2424);

        if(deg_val.equals("us")) {
            k=0;
            for(TextView t : listtexttime){
                t.setText(hourlyArray.getJSONObject(k).getString("temperature")+" \u2109");
            }
        }
        else{
            if(deg_val.equals("us"))
                k=0;
            for(TextView t: listtexttime){
                t.setText(hourlyArray.getJSONObject(k).getString("temperature")+" \u2103");
                k++;
            }
        }
    }

    private void init(JSONObject outval) {
        hiddenInfo1 = getLayoutInflater().inflate(R.layout.hid, linlayinflate, false);
        detlay1.removeView(hiddenInfo);
        linlayinflate.addView(hiddenInfo1);
        try {
            populater(outval);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void inti7(JSONObject outval) {
        hiddenInfo = getLayoutInflater().inflate(R.layout.hidsev, detlay1, false);
        detlay1.removeView(li1);
        detlay1.addView(hiddenInfo);
        try {
            populated(outval);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindDrawbles(findViewById(R.id.detlay1));
        System.gc();
    }

    public void unbindDrawbles(View view){
        if(view.getBackground()!=null){
            view.getBackground().setCallback(null);
        }
        if(view instanceof ViewGroup){
            for(int i=0;i<((ViewGroup) view).getChildCount();i++){
                unbindDrawbles(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
