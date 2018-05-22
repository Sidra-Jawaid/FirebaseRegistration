package com.example.sidrajawaid.demofirebase.FirebaseData;

import android.util.Log;

import com.example.sidrajawaid.demofirebase.CustomRecyclerView.RecyclerAdapter;
import com.example.sidrajawaid.demofirebase.MainApplicationContent.DataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SendFirebaseData {
    ArrayList<DataModel> savedarrayList=new ArrayList();
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabaseReference;
    RecyclerAdapter recyclerAdapter=new RecyclerAdapter();
    public static final String TAG="SendFirebaseData";

    public SendFirebaseData() {
    }

    /*public void tempMethod()
    {
        mFirebaseAuth=FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(mFirebaseAuth.getUid()).child("FavoriteNews");
        Log.d(TAG,"here is data");
        savedarrayList = recyclerAdapter.getArrayList();
        DataModel dataModel=new DataModel(savedarrayList);
        //Log.d("item of saved list",""+savedarrayList.get(0).getSavedlist());
        mDatabaseReference.setValue(dataModel);
    }*/
}
