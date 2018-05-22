package com.example.sidrajawaid.demofirebase.MainApplicationContent;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sidrajawaid.demofirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogIn extends Fragment{
    EditText ed1;
    EditText ed2;
    TextView tv1;
    Button login;
    public static final String TAG="LogIn";
    private FirebaseAuth firebaseAuth;
    String username;
    String password;
    ProgressDialog progressDialog;
    public LogIn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(getContext());
        ed1=view.findViewById(R.id.ed1);
        ed2=view.findViewById(R.id.ed2);
        login=view.findViewById(R.id.btnlogin);
        tv1=view.findViewById(R.id.text3);
        tv1.setClickable(true);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager frg_mng=getFragmentManager();
                FragmentTransaction fragmentTransaction=frg_mng.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.ltr,R.anim.rtl,R.anim.backpressenter,R.anim.backpressexit);
                SignUp fragment2=new SignUp();
                fragmentTransaction.replace(R.id.parentframelayout,fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEditText()) {
                    loginMethod();
                }
            }
        });
        return view;
    }
    public void loginMethod()
    {
       /* if (firebaseAuth.getCurrentUser() != null) {
            // Already signed in
            // Do nothing
        }else {*/

            firebaseAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                            /*Toast.makeText(getContext(), "Signed In =   " + task.getResult().getAdditionalUserInfo(), Toast.LENGTH_SHORT).show();
                            Log.d("MainActivity", "Signed In =  " + task.getResult());*/
                                progressDialog.setMessage("Authenticating .....");
                                progressDialog.show();
                                progressDialog.dismiss();
                                startActivity(new Intent(getContext(), UserProfile.class));
                            } else {
                                Toast.makeText(getContext(), "Problem Siging In : Invalid userID or password ", Toast.LENGTH_SHORT).show();
                                Log.d("MainActivity", "Signed In =  " + task.getException());
                                ed1.setError("Invalid userID or password");
                            }
                        /*else{
                            //Exception block
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                ed2.setError("weak password");
                                ed2.requestFocus();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                ed1.setError("invalid email");
                                ed1.requestFocus();
                            } catch(FirebaseAuthUserCollisionException e) {
                                ed1.setError("another user exists with similar credentials");
                                ed1.requestFocus();
                            } catch(Exception e) {
                                Log.e(TAG, e.getMessage());
                            }

                        }*/
                        }
                    });

    }
    public boolean checkEditText(){
        username=ed1.getText().toString().trim();
        password=ed2.getText().toString().trim();
        if(TextUtils.isEmpty(username))
        {
            ed1.setError("Enter username");
            return false;
        }
        if(TextUtils.isEmpty(password))
        {
            ed2.setError("Enter password");
            return false;
        }
        return  true;
    }

}
