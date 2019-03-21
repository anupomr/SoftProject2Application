package app.anupom.utility;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anupo.softproject2application.BooksDetailsActivity;
import com.example.anupo.softproject2application.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Book> mData;

    public RecyclerViewAdapter(Context mContext, List<Book> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.cardview_item_book,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        holder.tv_book_id.setText(mData.get(position).bookId);
        holder.tv_book_title.setText(mData.get(position).title);
        holder.tv_book_price.setText("$"+ String.valueOf( mData.get(position).price));
        holder.img_book_thumbnail.setImageResource(mData.get(position).thumbnail);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,BooksDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("bookId",mData.get(position).bookId);
                intent.putExtra("title",mData.get(position).title);
                intent.putExtra("description",mData.get(position).description);
                intent.putExtra("price",mData.get(position).price);
                intent.putExtra("thumbnail",mData.get(position).thumbnail);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public  static  class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_book_title;
        TextView tv_book_price;
        ImageView img_book_thumbnail;
        CardView cardView;
        TextView tv_book_id;
        public MyViewHolder(View itemView) {

            super(itemView);
            tv_book_title=(TextView)itemView.findViewById(R.id.book_title_id);
            tv_book_price=(TextView)itemView.findViewById(R.id.book_title_price);
            img_book_thumbnail=(ImageView)itemView.findViewById(R.id.book_img_id);
            cardView=(CardView)itemView.findViewById(R.id.cardViewId);
            tv_book_id=(TextView)itemView.findViewById(R.id.book_id);
        }
    }

}
