package com.example.sidrajawaid.demofirebase;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.core.Constants;
import com.firebase.tubesock.Base64;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp extends Fragment {

    private static final String TAG="SignUp";
    private static final int RESULT_CODE=000;
    ImageView profile_image;
    EditText ed_fullname,ed_username, ed_password, ed_retypepassword,ed_age;
    Spinner country_spinner;
    Button btn;
    FirebaseAuth firebaseAuth;
    StorageReference firebaseStorage;
    DatabaseReference firebase;
    Bitmap bitmap;
    ArrayList spinneritem=new ArrayList<>();
    DataModel datamodel;
    ProgressDialog progressDialog;
    Uri imageUri;
    private String fullname,password,repassword,username,spinner_value,cut_string1,cut_string2,string_userage,splChrs = "-/@#$%^&_+=()",profileimageurl,u_id;

    private int user_age, index_at;
    public SignUp() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        Firebase.setAndroidContext(getContext());
        firebase=FirebaseDatabase.getInstance().getReference();
        //firebaseStorage=FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis()+".jpg");
        firebaseStorage=FirebaseStorage.getInstance().getReference("profilepics/");
        spinneritem.add("Select Location");
        spinneritem.add("Balochistan,Pakistan");
        spinneritem.add("Karachi,Pakistan");
        spinneritem.add("Islamabad,Pakistan");
        spinneritem.add("Lahore,Pakistan");
        spinneritem.add("Multan,Pakistan");
        spinneritem.add("Faisalabad,Pakistan");
        profile_image=v.findViewById(R.id.profile_image);
        ed_fullname=v.findViewById(R.id.edittext0);
        ed_username = v.findViewById(R.id.edittext1);
        ed_password = v.findViewById(R.id.edittext2);
        ed_retypepassword = v.findViewById(R.id.edittext3);
        ed_age=v.findViewById(R.id.edittext4);
        country_spinner=v.findViewById(R.id.spinner);
        country_spinner.setAdapter(new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,spinneritem));
        progressDialog=new ProgressDialog(getContext());
        btn = v.findViewById(R.id.btnSignup);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(checkValidation()){
                        authenticationMethod();
                    }
            }
        });
        return v;
    }
    public void authenticationMethod() {
        Log.d(TAG,"aunthenticationMethod() running .......");
        progressDialog.setMessage("Validating Info......");
        progressDialog.show();
        //progressDialog.dismiss();*/
        firebaseAuth.createUserWithEmailAndPassword(username,password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.setMessage("Successful ......");
                            progressDialog.show();
                            Log.d(TAG, "Success ful Sign Up ");
                            u_id=firebaseAuth.getUid();
                            Log.d(TAG, firebaseAuth.getUid());
                            setDataToFirebase();
                            Toast.makeText(getContext(), "Signed Up", Toast.LENGTH_SHORT).show();
                            //Diasable all fields after sign up
                            profile_image.setImageResource(R.drawable.profile_image);
                            profile_image.setEnabled(false);
                            ed_fullname.setText("");
                            ed_fullname.setEnabled(false);
                            ed_username.setText("");
                            ed_username.setEnabled(false);
                            ed_password.setText("");
                            ed_password.setEnabled(false);
                            ed_retypepassword.setText("");
                            ed_retypepassword.setEnabled(false);
                            ed_age.setText("");
                            ed_age.setEnabled(false);
                            country_spinner.setEnabled(false);
                            progressDialog.dismiss();
                            FragmentManager fm=getFragmentManager();
                            FragmentTransaction ft=fm.beginTransaction();
                            LogIn logIn=new LogIn();
                            ft.replace(R.id.parentframelayout,logIn);
                            ft.commit();
                        } else {
                            progressDialog.setMessage("Error Signing Up......");
                            progressDialog.show();
                            Log.d("MainActivity", " error signing up = " + task.getException());
                            Toast.makeText(getContext(), "error signing up"+ task.getException() ,Toast.LENGTH_SHORT).show();
                            //progressDialog.dismiss();
                        }
                    }
                });

    }
    public void uploadImage()
    {
        Intent uploadimage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(uploadimage,RESULT_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_CODE&&data!=null&&resultCode==RESULT_OK)
        {
            imageUri=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imageUri);
                profile_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                profile_image.setImageResource(R.drawable.noimage);
                e.printStackTrace();
            }
        }
        else {
            profile_image.setImageResource(R.drawable.noimage);
        }
    }
    public void sendingImageToStorage()
    {
        if(imageUri!=null)
        {
            firebaseStorage.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Upload done", Toast.LENGTH_SHORT).show();
                        }

                    });
        }
        else {
            profile_image.setImageResource(R.drawable.noimage);
        }
    }
    public void setDataToFirebase()
    {
         Log.d(TAG," setDataToFirebase() running");
            datamodel=new DataModel(username,fullname,string_userage,spinner_value,u_id);
            firebase.child("users").child(u_id).setValue(datamodel);
            sendingImageToStorage();
    }
    public boolean checkValidation()
    {
        fullname=ed_fullname.getText().toString().trim();
        username=ed_username.getText().toString().trim();
        password=ed_password.getText().toString().trim();
        string_userage=ed_age.getText().toString().trim();
        repassword=ed_retypepassword.getText().toString().trim();
        spinner_value=country_spinner.getSelectedItem().toString();
        index_at=username.indexOf('@');
        cut_string1=username.substring(0,index_at);
        cut_string2=username.substring(index_at+1,username.length()-1);
        Log.d(TAG," cutstring2 = "+cut_string2);
        Log.d(TAG," checkValidation()running ... ");
        Log.d(TAG," Performing validation...... ");
        progressDialog.setMessage("Saving Signup Info");
        progressDialog.show();
        //progressDialog.dismiss();
        //validating for fullname
        if(TextUtils.isEmpty(fullname))
        {
            ed_fullname.setError("Enter full name");
            return false;
        }
        //validation for username
        if(cut_string1.matches("[0-9]+")||cut_string1.matches("[splChrs]+"))
        {
            ed_username.setError("username should be combination of alphanumeric");
            return false;
        }
        if(cut_string1.equals(password))
        {
            ed_password.setError("password should not contain username");
            return false;
        }
        if(TextUtils.isEmpty(username))
        {
            ed_username.setError("Required");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches())
        {
            ed_username.setError("Not a valid email address");
            return false;
        }
        //validation for password
        if(TextUtils.isEmpty(password))
        {
            ed_password.setError("Required");
            return false;
        }
        if(password.length()<6)
        {
            ed_password.setError("Atleast 6 characters");
            return false;
        }
        //validation for retype password
        if(TextUtils.isEmpty(repassword))
        {
            ed_retypepassword.setError("Required");
            return false;
        }
        if(!(password.equals(repassword)))
        {
            ed_retypepassword.setError("Password doesnot match");
            return false;
        }
        // validattion for age
        try
        {
            //user_age= Integer.parseInt(string_userage);
            if (TextUtils.isEmpty(ed_age.getText().toString().trim())||ed_age.getText().toString()==" "||ed_age==null) {
                user_age= Integer.parseInt(string_userage);
            }
        }
        catch (NumberFormatException e)
        {
            ed_age.setError("Required");
            return false;
        }
        if(Integer.parseInt(string_userage)<12||Integer.parseInt(string_userage)>90)
        {
            ed_age.setError("Enter correct age");
            return false;
        }
        //validation for location
        if(spinner_value.toString()==spinneritem.get(0))
        {
            setSpinnerError(country_spinner,"Select Location");
            return false;
        }
        return true;
    }
    public void setSpinnerError(Spinner spinner, String error)
    {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }

    }

}




