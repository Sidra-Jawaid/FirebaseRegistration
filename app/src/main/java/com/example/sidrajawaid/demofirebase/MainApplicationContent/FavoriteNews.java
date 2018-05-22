package com.example.sidrajawaid.demofirebase.MainApplicationContent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
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
    ArrayList data_list=new ArrayList();
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
        rac=new RecyclerAdapter(data_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        getDatafromDatabase();
        rac.notifyDataSetChanged();

        return v;
    }
    public void getDatafromDatabase()
    {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG,"children count = "+dataSnapshot.getChildrenCount());
                Log.d(TAG,"Array index = "+dataSnapshot.getValue()+"\n\n\n");
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    GenericTypeIndicator<Map<String,Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String,Object>>() {};
                    article = ds.getValue(genericTypeIndicator );

                }
                for(Object obj:article.values())
                {
                    data_list.add(obj);
                    Log.d("Article = ",""+data_list);
                }


                /*for(int i=0;i<=dataSnapshot.getChildrenCount()-1;i++)
                {
                    data_list.add(article.get(i).toString());*//*.getSource().getId(),article.get(i).getSource().getName())
                            ,article.get(i).getAuthor()
                            ,article.get(i).getTitle()
                            ,article.get(i).getDescription()
                            ,article.get(i).getUrl()
                            ,article.get(i).getUrlToImage()));*//*
                }
                rac=new RecyclerAdapter(data_list);
                recyclerView.setAdapter(rac);
                RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(itemDecoration);*/
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Favorite news no array",""+databaseError);
            }
        });
    }
}
