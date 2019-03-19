package app.anupom.utility;

import android.os.AsyncTask;

import com.example.anupo.softproject2application.OrderDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ParseBook  extends AsyncTask<URL,String,Void> {

    String data="";
    String dataParsed="";
    String singleParsed="";

    @Override
    protected Void doInBackground(URL... params) {
        try {
            URL url=new URL("http://bookapi-dev.us-east-1.elasticbeanstalk.com/api/Books.json");
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while (line !=null){
                line =bufferedReader.readLine();
                data=data+line;
            }
            JSONArray jsonArray=new JSONArray(data);
            for (int i=0;i <jsonArray.length();i++){
                JSONObject jsonObject= (JSONObject) jsonArray.get(i);
                if(jsonObject.getString("title").equals("Java Standerd")) {
                    singleParsed = "Book Name: " + jsonObject.getString("title") + "\n" +
                            "Price    : " + jsonObject.getString("price") + "\n" +
                            "Edition  : " + jsonObject.getString("edition") + "\n";

                    dataParsed = dataParsed + singleParsed + "\n\n";
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        OrderDetailsActivity.data.setText(this.dataParsed);
        super.onPostExecute(aVoid);
    }
}
