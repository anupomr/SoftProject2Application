package app.sebobooks.utility;
/*
 * Purpose: this utility helps for fatch API data
 * Author:  Anupom Roy, Shila Das
 * Date: March 16, 2019
 * Version: 1.7
 * */
import android.os.AsyncTask;
import android.util.Log;

import com.example.anupo.softproject2application.BooksActivity;
import com.example.anupo.softproject2application.R;

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
import java.util.ArrayList;
import java.util.List;

public class BackgroundWorker extends AsyncTask<Void,Void,Void> {
    String data="";
    List<Book> lstBook;
    String dataParsed="";
    String singleParsed="";
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Log.d("shila","start do in bg");
            URL url=new URL("http://bookapi-dev.us-east-1.elasticbeanstalk.com/api/Books.json");
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

            String line="";
            while (line !=null){
                line =bufferedReader.readLine();
                data=data+line;
            }
            Log.d("shila",data);
            JSONArray jsonArray=new JSONArray(data);
            lstBook=new ArrayList<>();
            for (int i=0;i <jsonArray.length();i++){
                JSONObject jsonObject= (JSONObject) jsonArray.get(i);
                Book b=new Book(jsonObject.getString("bookId"),jsonObject.getString("title"),jsonObject.getString("description"),Float.parseFloat( jsonObject.getString("price")), R.drawable.library_books);
                lstBook.add(b);
//
            }
            lstBook.get(1).thumbnail=R.drawable.b2;
               lstBook.get(2).thumbnail=R.drawable.b3;
                lstBook.get(3).thumbnail=R.drawable.b4;
                lstBook.get(4).thumbnail=R.drawable.b5;
            lstBook.get(5).thumbnail=R.drawable.b1;
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
        Log.d("shila","start on post exe");
        BooksActivity.lstBook=this.lstBook;

        super.onPostExecute(aVoid);
    }

}
