package com.example.anupo.softproject2application;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class BooksListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);
        new ParseTask().execute();
    }

    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                String $url_json = "https://api.myjson.com/bins/lbwna";
                URL url = new URL($url_json);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
                Log.d("FOR_LOG", resultJson);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);

            final ListView lView = (ListView) findViewById(R.id.lvMain);

            String[] from = {"name_item"};
            int[] to = {R.id.name_item};
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> hashmap;

            try {
                JSONObject json = new JSONObject(strJson);
                JSONArray jArray = json.getJSONArray("platform");

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject friend = jArray.getJSONObject(i);

                    String nameOS = friend.getString("name");
                    Log.d("FOR_LOG", nameOS);

                    hashmap = new HashMap<String, String>();
                    hashmap.put("name_item", "" + nameOS);
                    arrayList.add(hashmap);
                }

                final SimpleAdapter adapter = new SimpleAdapter(BooksListActivity.this, arrayList, R.layout.item, from, to);
                lView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
