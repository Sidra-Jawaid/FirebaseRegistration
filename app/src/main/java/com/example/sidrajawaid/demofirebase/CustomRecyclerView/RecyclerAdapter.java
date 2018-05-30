package com.example.sidrajawaid.demofirebase.CustomRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sidrajawaid.demofirebase.FirebaseData.SendFirebaseData;
import com.example.sidrajawaid.demofirebase.MainApplicationContent.DataModel;
import com.example.sidrajawaid.demofirebase.R;
import com.example.sidrajawaid.demofirebase.RetrofitFiles.Article;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    URL url1,url2=null;
    Context context;
    Bitmap bmp1,bmp2 = null;
    ArrayList<Article> arrayList;
    Article example;
    String temp1,temp2;
    Article article=new Article();
    //ArrayList saveditems=new ArrayList();

    public RecyclerAdapter(ArrayList arrayList) {
        this.arrayList = arrayList;
    }
    public RecyclerAdapter() {
        newClass new_class=new newClass(temp1,temp2);
        new_class.execute();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row,parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Article example= arrayList.get(position);
        holder.tv0.setText(example.getSource().getId());
        holder.tv1.setText(example.getTitle());
        holder.tv2.setText(example.getDescription());
        holder.tv3.setText(example.getUrl());
        Picasso.with(context).load(example.getUrlToImage()).into(holder.tv4);
        holder.tv5.setText(example.getSource().getName());
        holder.tv6.setText(example.getAuthor());
    }
    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size():0;
    }
    public void setList(ArrayList<Article> list) {

        this.arrayList = list;
        notifyDataSetChanged();
    }
    public void addView(int pos) {
        article.setArt(arrayList.get(pos));
        arrayList.remove(pos);
        this.notifyDataSetChanged();
        this.notifyItemRangeChanged(pos,arrayList.size());
    }
    public void dismissView(int pos)
    {
        arrayList.remove(pos);
        this.notifyItemRemoved(pos);
        this.notifyItemRangeChanged(pos,arrayList.size());
    }
    public String getDissmisArticleTitle(int pos) {
        String dissmisviewtitle=arrayList.get(pos).getTitle();
        return dissmisviewtitle;
    }
    public Article getArticle()
    {
        return article;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv0;
        TextView tv1;
        TextView tv2;
        TextView tv3;
        /*TextView tv4;*/

        ImageView tv4;
        TextView tv5;
        TextView tv6;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv0= itemView.findViewById(R.id.sourceid);
            tv5=itemView.findViewById(R.id.sourcename);
            tv1 =  itemView.findViewById(R.id.newstitle);
            tv2 = itemView.findViewById(R.id.description);
            tv3 =  itemView.findViewById(R.id.newseurl);
            tv4 = itemView.findViewById(R.id.newimageurl);
            tv6= itemView.findViewById(R.id.author);
        }
    }
    public class newClass extends AsyncTask<Void, Void, String[]> {
        String s1,s2;
        newClass(String s1,String s2)
        {
            this.s1=s1;
            this.s2=s2;
        }

        @Override
        protected String[] doInBackground(Void... voids) {
            try {
                url1 = new URL(example.getUrl());
                url2=new URL(example.getUrlToImage());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                bmp1 = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
                bmp2 = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new String[0];
        }
        @Override
        protected void onPostExecute(String[] s) {
            s1= s[0];
            s2= s[1];

            super.onPostExecute(s);
        }
    }
}
