package com.example.anupo.softproject2application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class BooksDetailsActivity extends AppCompatActivity {
    private TextView tvTitle, tvDesc, tvId,tvPrice,tvEdition, tvIsbn, tvPublisher;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_details);
        tvId=(TextView)findViewById(R.id.tvId);
        tvTitle=(TextView)findViewById(R.id.tvTitle);
        tvDesc=(TextView)findViewById(R.id.tvDesc);
        tvPrice=(TextView)findViewById(R.id.tvPrice);
        tvEdition=(TextView)findViewById(R.id.tvEdition);
        tvIsbn=(TextView)findViewById(R.id.tvIsbn);
        tvPublisher=(TextView)findViewById(R.id.tvPublisher);
        img=(ImageView) findViewById(R.id.img_book);
        Intent intent=getIntent();
        String bookid=intent.getExtras().getString("bookId");
        tvId.setText(bookid);
        tvTitle.setText(intent.getExtras().getString("title"));
        tvDesc.setText(intent.getExtras().getString("description"));
        tvPrice.setText(intent.getExtras().getString("price"));

        img.setImageResource(intent.getExtras().getInt("thumbnail"));
    }
}
