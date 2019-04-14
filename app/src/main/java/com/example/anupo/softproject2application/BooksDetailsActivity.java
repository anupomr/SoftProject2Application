package com.example.anupo.softproject2application;
/*
 * Purpose: Show details of a selected Book
 * Author:  Anupom Roy
 * Date: March 2, 2019
 * Version: 1.7
 * */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BooksDetailsActivity extends AppCompatActivity {
    private TextView tvTitle, tvDesc, tvId,tvPrice,tvEdition, tvIsbn, tvPublisher;
    private ImageView img;
    Button checkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_details);
        checkout=findViewById(R.id.btCheckout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent checkoutIntent=new Intent(BooksDetailsActivity.this, PaymentActivity.class);
                Log.d("ook detail" ,tvId.getText().toString());
                Log.d("book detail" ,tvPrice.getText().toString());
                checkoutIntent.putExtra("bookId",tvId.getText().toString());
                checkoutIntent.putExtra("price",tvPrice.getText().toString());
                startActivity(checkoutIntent);
            }
        });

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
        tvPrice.setText(String.valueOf(intent.getExtras().getFloat("price")));

        img.setImageResource(intent.getExtras().getInt("thumbnail"));
        Log.d("from book detail c" ,tvId.getText().toString());
        Log.d("from book detail c" ,tvPrice.getText().toString());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                Intent homeIntent=new Intent(this,MainActivity.class);
                startActivity(homeIntent);
                break;
            case R.id.login:
                Intent loginIntent=new Intent(this,LoginActivity.class);
                startActivity(loginIntent);
                Toast.makeText(this, "You selected login!", Toast.LENGTH_LONG).show();
                break;
            case R.id.book:
                Intent books=new Intent(this,BooksActivity.class);
                startActivity(books);
                break;
            case R.id.bookList:
                Intent booksListIntent=new Intent(this,BooksListActivity.class);
                startActivity(booksListIntent);

                break;
            case R.id.exit:
                Toast.makeText(this, "You selected exit!", Toast.LENGTH_LONG).show();
                System.exit(1);
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
