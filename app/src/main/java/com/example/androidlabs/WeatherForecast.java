package com.example.androidlabs;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class WeatherForecast extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        setProgressBarVisibility(true);
        ForecastQuery req = new ForecastQuery();
        req.execute();
    }
    class ForecastQuery extends AsyncTask<String, Integer, String> {
        TextView mx = findViewById(R.id.max_temp);
        ProgressBar pb = findViewById(R.id.prog_bar);
        TextView mn = findViewById(R.id.min_temp);
        TextView cr = findViewById(R.id.current_temp);
        TextView u = findViewById(R.id.UV);
        ImageView wi = findViewById(R.id.weather_icon);
        private Double UV;
        private String min;
        private String max;
        private String current;
        private Bitmap bm;
        private String OTWeather = "https://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
        private String OTUV = "https://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";
        private String iconName;
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(OTWeather);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8"); //response is data from the server
                String parameter = null;
                int eventType = xpp.getEventType();
                while(eventType != XmlPullParser.END_DOCUMENT)
                {
                    if(eventType == XmlPullParser.START_TAG)
                    {
                        if(xpp.getName().equals("weather"))
                        {
                            iconName = xpp.getAttributeValue(null,    "icon");
                        }
                        else if(xpp.getName().equals("temperature"))
                        {
                            min = xpp.getAttributeValue(null,    "min");
                            publishProgress(25);
                            Log.i("WeatherForecast", "The min is now: " + min) ;
                            max = xpp.getAttributeValue(null, "max");
                            publishProgress(50);
                            Log.i("WeatherForecast", "The max is now: " + max) ;
                            current = xpp.getAttributeValue(null, "value");
                            publishProgress(75);
                            Log.i("WeatherForecast", "The current is now: " + current) ;
                        }
                    }
                    eventType = xpp.next();
                }
                if (fileExistance(iconName+".png")){
                    Log.i("WeatherForecast", "Image File Found");
                    FileInputStream fis = null;
                    try {    fis = openFileInput(iconName+".png");   }
                    catch (FileNotFoundException e) {    e.printStackTrace();  }
                    bm = BitmapFactory.decodeStream(fis);
                }else {
                    Bitmap image = null;
                    url = new URL("https://openweathermap.org/img/w/" + iconName + ".png");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        image = BitmapFactory.decodeStream(connection.getInputStream());
                        publishProgress(100); }
                    FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Log.i("WeatherForecast", "Image was Downloaded");
                    bm = image; }
                URL UVurl = new URL(OTUV);
                HttpURLConnection UVurlConnection = (HttpURLConnection) UVurl.openConnection();
                InputStream UVresponse = UVurlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(UVresponse, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String UVresult = sb.toString();
                JSONObject uvReport = new JSONObject(UVresult);
                UV = uvReport.getDouble("value");
                Log.i("WeatherForecast", "The uv is now: " + UV) ;

            }
            catch (Exception e)
            {
            }
            return "Done";
        }
        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }
            public void onProgressUpdate(Integer ... args)
        {
            pb.setVisibility(View.VISIBLE);
            pb.setProgress(args[0]);
        }
        public void onPostExecute(String fromDoInBackground)
        {
            Log.i("HTTP", fromDoInBackground);
            mx.setText("High of: " +max);
            mn.setText("Low of: "+min);
            cr.setText("Current Temp: "+ current);
            u.setText("UV Rating: "+UV);
            wi.setImageBitmap(bm);
            pb.setVisibility(View.INVISIBLE); }
    }
}
