package com.example.sidrajawaid.demofirebase.RetrofitFiles;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sidrajawaid.demofirebase.RetrofitFiles.ApiInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class GetFragment extends Fragment {

    private ApiInterface mAPIInterface;
    Button button;
    //onFragmentDataSend listener ;
    public static final String TAG = "GetFragment";

    public GetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*View v=inflater.inflate(R.layout.fragment_get, container, false);
        button=(Button)v.findViewById(R.id.btn);
        mAPIInterface = ApiClient.getApiClient().create(ApiInterface.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getPost();
                    callingFragment();
            }
        });
        return v;
    }
    public void callingFragment()
    {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        DeleteFragment deleteFragment=new DeleteFragment();
        ft.replace(R.id.frag,deleteFragment);
        ft.commit();
    }
    public void getPost() {
        mAPIInterface.getPost().enqueue(new Callback<Posts>() {

            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                Log.d(TAG,"GET: onResponce method called");
                if(response.isSuccessful()){
                    Log.i(TAG, "GET responce code= "+response.code());
                    if(listener!=null) {
                        listener.sendDataToActivity(""+response.body());
                    }
                    Log.i(TAG, "Got" + response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Log.e(TAG, "Unable to Get ");
            }
        });
    }
    public interface onFragmentDataSend{
        void sendDataToActivity(String s);
    }
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener=(onFragmentDataSend)context;
        }
        catch (ClassCastException e)
        {

        }
    }
*/
        return null;
    }
}
