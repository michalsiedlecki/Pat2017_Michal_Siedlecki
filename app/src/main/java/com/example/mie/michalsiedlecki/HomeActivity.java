package com.example.mie.michalsiedlecki;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mie.michalsiedlecki.Models.HomeModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private GridView homeListView;
    //IP for emulator
    //public final static String BASE_SERVER_URL = "http://10.0.2.2:8080/page_0.json";
    //IP for phone
    public final static String BASE_SERVER_URL = "http://192.168.1.101:8080/page_0.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeListView = (GridView)findViewById(R.id.homeListView);

        cachedImage();
        new JSONTask().execute(BASE_SERVER_URL);
    }

    public void onLogoutButtonClick(View view) {
        logout();
        openMainActivity();
    }

    public void logout(){
        SharedPreferences loginStatus = getSharedPreferences("data", 0);
        SharedPreferences.Editor editorLoginStatus = loginStatus.edit();
        editorLoginStatus.putBoolean("loginStatus", false);
        editorLoginStatus.commit();
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void cachedImage(){
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public class  JSONTask extends AsyncTask<String, String, List<HomeModel>>{

        @Override
        protected List<HomeModel> doInBackground(String... params){
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String line;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJSON = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJSON);
                JSONArray parentArray = parentObject.getJSONArray("array");

                List<HomeModel> homeModelList = new ArrayList<>();

                for (int i=0; i<parentArray.length(); i++){
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    HomeModel homeModel = new HomeModel();
                    homeModel.setUrl(finalObject.getString("url"));
                    homeModel.setTitle(finalObject.getString("title"));
                    homeModel.setDesc(finalObject.getString("desc"));
                    homeModelList.add(homeModel);
                }

                return homeModelList;

            } catch (IOException | JSONException e){
                e.printStackTrace();
            } finally{
                if (connection != null){
                    connection.disconnect();
                }
                try{
                    if (reader != null){
                        reader.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<HomeModel> result) {
            super.onPostExecute(result);
            if (result != null){
                ItemAdapter adapter = new ItemAdapter(getApplicationContext(), R.layout.list_item, result);
                homeListView.setAdapter(adapter);
            }

        }
    }

    public class ItemAdapter extends ArrayAdapter{

        private List<HomeModel> homeModelList;
        private int resource;
        private LayoutInflater infalter;
        ItemAdapter(Context context, int resource, List<HomeModel> objects) {
            super(context, resource, objects);
            homeModelList = objects;
            this.resource = resource;
            infalter = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = infalter.inflate(resource, null);
            }

            ImageView imageView;
            TextView titleTextView;
            TextView descTextView;
            final ProgressBar progressBar;

            imageView = (ImageView)convertView.findViewById(R.id.itemImageView);
            titleTextView = (TextView)convertView.findViewById(R.id.titleTextView);
            descTextView = (TextView)convertView.findViewById(R.id.descTextView);
            progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);

            ImageLoader.getInstance().displayImage(homeModelList.get(position).getUrl(), imageView, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);
                }
            });
            titleTextView.setText(homeModelList.get(position).getTitle());
            descTextView.setText(homeModelList.get(position).getDesc());
            return convertView;
        }
    }
}
