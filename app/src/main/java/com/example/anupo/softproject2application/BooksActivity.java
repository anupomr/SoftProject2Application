package com.example.anupo.softproject2application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import app.anupom.utility.BackgroundWorker;
import app.anupom.utility.Book;
import app.anupom.utility.RecyclerViewAdapter;

public class BooksActivity extends AppCompatActivity {
    public static List<Book> lstBook;
    RecyclerView myrv;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        lstBook=new ArrayList<>();
        btn=findViewById(R.id.btn);
        myrv=(RecyclerView) findViewById(R.id.recyclerview_id);

        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                BackgroundWorker bw=new BackgroundWorker();
                Log.d("shila","test before execute");
                bw.execute();
                Log.d("shila","test after execute");
                RecyclerViewAdapter myAdapter=new RecyclerViewAdapter(getBaseContext(),lstBook);
                myrv.setLayoutManager(new GridLayoutManager(getBaseContext(),3));
                myrv.setAdapter(myAdapter);
            }
        });
    }
}
