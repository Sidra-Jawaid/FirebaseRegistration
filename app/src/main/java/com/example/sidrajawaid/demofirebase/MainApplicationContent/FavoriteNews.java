package com.example.sidrajawaid.demofirebase.MainApplicationContent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sidrajawaid.demofirebase.CustomRecyclerView.RecyclerAdapter;
import com.example.sidrajawaid.demofirebase.R;
import com.example.sidrajawaid.demofirebase.RetrofitFiles.Article;
import com.example.sidrajawaid.demofirebase.RetrofitFiles.Source;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteNews extends Fragment {
    ArrayList<Article> data_list=new ArrayList();
    RecyclerView recyclerView;
    Map<String,Object> article;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabaseReference;
    public static final String TAG="FavoriteNews";
    RecyclerAdapter rac;
    public FavoriteNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_favorite_news, container, false);
        recyclerView=v.findViewById(R.id.rvsaved);
        mFirebaseAuth= FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(mFirebaseAuth.getUid()).child("FavoriteNews");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getDatafromDatabase();
        //rac.notifyDataSetChanged();

        return v;
    }
    public void getDatafromDatabase()
    {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG,"children count = "+dataSnapshot.getChildrenCount());
                Log.d(TAG,"Array index = "+dataSnapshot.getValue()+"\n\n\n");

                ArrayList<Article> data  = new ArrayList<Article>();
                GenericTypeIndicator<ArrayList<Article>> t = new GenericTypeIndicator<ArrayList<Article>>() {};
                data = dataSnapshot.getValue(t);
                data_list = data;
                rac = new RecyclerAdapter(data);
                recyclerView.setAdapter(rac);
                rac.notifyDataSetChanged();
                rac=new RecyclerAdapter(data_list);
                recyclerView.setAdapter(rac);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Favorite news no array",""+databaseError);
            }
        });
    }
}
