package com.example.sidrajawaid.demofirebase.MainApplicationContent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sidrajawaid.demofirebase.CustomRecyclerView.RecyclerAdapter;
import com.example.sidrajawaid.demofirebase.FirebaseData.SendFirebaseData;
import com.example.sidrajawaid.demofirebase.R;
import com.example.sidrajawaid.demofirebase.RetrofitFiles.ApiClient;
import com.example.sidrajawaid.demofirebase.RetrofitFiles.ApiInterface;
import com.example.sidrajawaid.demofirebase.RetrofitFiles.Article;
import com.example.sidrajawaid.demofirebase.RetrofitFiles.Example;
import com.example.sidrajawaid.demofirebase.RetrofitFiles.Source;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    private ApiInterface mAPIInterface;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabaseReference;
    ArrayList savedarrayList=new ArrayList();
    String sourceid,sourcename,articleauthor,articletitle,articledescription,articleurl,articleurltoimage;
    //onFragmentDataSend listener ;
    public static final String TAG = "NewsFragment";
    RecyclerView mRecyclerView;
    RecyclerAdapter mRecyclerAdapter;
    ArrayList<Article> mArrayList=new ArrayList();
    Example result=null;
    SendFirebaseData sendFirebaseData;
    public NewsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_news, container, false);
        mFirebaseAuth= FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(mFirebaseAuth.getUid()).child("FavoriteNews");
        mRecyclerView=v.findViewById(R.id.newrecyclerview);
        mAPIInterface = ApiClient.getApiClient().create(ApiInterface.class);
        mRecyclerAdapter=new RecyclerAdapter(mArrayList);
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mRecyclerAdapter);
        savedarrayList = ((RecyclerAdapter) mRecyclerView.getAdapter()).getArrayList();
        itemMethod();
        getPost();
        return v;
    }
    public void getPost() {

        mAPIInterface.getArticle().enqueue(new Callback<Example>() {

            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Log.d(TAG,"GET: onResponce method called");
                if(response.isSuccessful()) {
                    Log.i(TAG, "GET responce code= " + response.code());
                    result= response.body();
                    for(int i=0;i<=result.getTotalResults()-1;i++) {
                        mArrayList.add(new Article(new Source(result.getArticles().get(i).getSource().getId()
                                , result.getArticles().get(i).getSource().getName())
                                , result.getArticles().get(i).getAuthor()
                                , result.getArticles().get(i).getTitle()
                                , result.getArticles().get(i).getDescription()
                                , result.getArticles().get(i).getUrl()
                                , result.getArticles().get(i).getUrlToImage()));
                        ((RecyclerAdapter)mRecyclerView.getAdapter()).setList(mArrayList);
                    }

                        Log.d(TAG, "Responce body = " + response.body().toString());
                    Log.d(TAG, "Responce one = " );
                }
                }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.i(TAG, "Failure = "+t.getCause());
            }
        });
    }
    public void itemMethod()
    {
        ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.RIGHT) {
                    Toast.makeText(getContext(),"Saved",Toast.LENGTH_SHORT).show();
                    mRecyclerAdapter.addView(position);
                    mRecyclerAdapter.notifyDataSetChanged();
                    tempMethod();
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
   public void tempMethod()
   {
       Log.d(TAG,"here is data");
       //DataModel dataModel=new DataModel(savedarrayList);
       Log.d(TAG,"array sent to firebase");
       mDatabaseReference.setValue(savedarrayList);

   }
}
