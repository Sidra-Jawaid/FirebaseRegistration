package com.example.sidrajawaid.demofirebase.MainApplicationContent;


import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sidrajawaid.demofirebase.CustomRecyclerView.RecyclerAdapter;
import com.example.sidrajawaid.demofirebase.R;
import com.example.sidrajawaid.demofirebase.RetrofitFiles.Article;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteNews extends Fragment {
    public static final String TAG="FavoriteNews";
    RecyclerView recyclerView;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabaseReference;
    RecyclerAdapter rac;
    private ArrayList data_list=new ArrayList();
    private List keyarray,matcher;
    private ArrayList<Article>articlearraylist;
    private String swipeview,map_key = null;
    private Collection<Article> values;
    Map<String,Article> map;
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
        itemMethod();
        //rac.notifyDataSetChanged();
        return v;
    }
    public void itemMethod()
    {
        ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    Toast.makeText(getContext(),"Deleted",Toast.LENGTH_SHORT).show();
                    swipeview=rac.getDissmisArticleTitle(position);
                    deleteData(swipeview);
                    rac.dismissView(position);
                    //rac.notifyDataSetChanged();
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    public void getDatafromDatabase()
    {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    GenericTypeIndicator<Map<String,Article>> t = new GenericTypeIndicator<Map<String,Article>>() {};
                        Map<String,Article> map = dataSnapshot.getValue(t);
                        values = map.values();
                }
                else {
                    Toast.makeText(getContext(), "No favorite news!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                articlearraylist=new ArrayList<>(values);
                for(int i=0;i<=articlearraylist.size()-1;i++){
                    data_list.add(articlearraylist.get(i).getArt());
                }
                rac = new RecyclerAdapter(data_list);
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
    public void deleteData(final String refKey){
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    GenericTypeIndicator<Map<String,Article>> t = new GenericTypeIndicator<Map<String,Article>>() {};
                    map = dataSnapshot.getValue(t);
                    values = map.values();
                }
                else {
                    Toast.makeText(getContext(), "No favorite news!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<Article> articlearraylist =new ArrayList<>(values);
                keyarray=new ArrayList();
                for(int i=0;i<=articlearraylist.size()-1;i++){
                    if(refKey.equals(articlearraylist.get(i).getArt().getTitle())) {
                        Log.d("re parent = ", "" + mDatabaseReference.getParent());

                        for (String mapkey : map.keySet()) {
                            map_key = mapkey;
                        }
                        System.out.println(mDatabaseReference.child(map_key).getKey() + "================= map key");
                        final String key = mDatabaseReference.child(map_key).getKey();
                        Log.d(TAG, "mDatabaseReference.orderByKey() = " + key);
                        mDatabaseReference.child(key).removeValue();
                    }
                }
                Log.d("Delete Data method",""+dataSnapshot.getValue());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,"Nothing found:Database Error Occured");

            }
        });
    }
}
