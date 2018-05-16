package com.example.sidrajawaid.demofirebase;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
//import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {


    DatabaseReference mDatabaseReference;
    FirebaseAuth mFirebaseAuth;
    StorageReference storageReference;
    String userkaemail,userkaporanaam,userkiauthid,userkilocation,userkiage;
    ProgressDialog progDialog;
    Switch profileeditswitch;
    EditText uName,uAge;
    Spinner uLocation;
    Button btn;
    Uri imageUri;
    Bitmap bitmap;
    ImageView uProfileImage;
    TextView tv1;
    public static final String TAG="EditProfileFragment";
    public static final int RESULT_CODE=000;


    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_editprofile, container, false);
        progDialog = new ProgressDialog(getContext());
        mFirebaseAuth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference("profilepics/").child(mFirebaseAuth.getUid());
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference=FirebaseDatabase.getInstance().getReference().child("users").child(mFirebaseAuth.getUid());
        Log.d(TAG, "AGAYA current  uid = " +mFirebaseAuth.getCurrentUser().getUid());
        Log.d(TAG, "AGAYA uid = " +mFirebaseAuth.getUid());
        Log.d(TAG,"OUTPOUT  "+ mDatabaseReference.getKey());
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userkiage=dataSnapshot.child("userAge").getValue(String.class);
                userkaemail=dataSnapshot.child("userEmail").getValue(String.class);
                userkaporanaam=dataSnapshot.child("userFullname").getValue(String.class);
                userkiauthid=dataSnapshot.child("userID").getValue(String.class);
                userkilocation=dataSnapshot.child("userLocation").getValue(String.class);
                 //Picasso.with(getContext()).load(imageUri.toString()).into(uProfileImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,"Koi data nhi aya " );
            }
        });
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageUri=uri;
                uProfileImage.setImageURI(uri);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uProfileImage.setImageResource(R.drawable.noimage);
            }
        });
        uProfileImage = v.findViewById(R.id.profile_image);
        profileeditswitch = v.findViewById(R.id.toggle1);
        uName = v.findViewById(R.id.edittext1);
        uName.setText(userkaporanaam);
        uName.setEnabled(false);
        uProfileImage.setImageURI(imageUri);
        uProfileImage.setEnabled(false);
        uAge = v.findViewById(R.id.uepedittext4);
        tv1 = v.findViewById(R.id.temptextview);
        uLocation = v.findViewById(R.id.spinner);
        btn = v.findViewById(R.id.btnSaveChanges);
        profileeditswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    uName.setEnabled(true);
                    uAge.setEnabled(true);
                    uLocation.setEnabled(true);
                    uProfileImage.setEnabled(true);
                } else {

                    uAge.setEnabled(false);
                    uAge.setText(userkiage);

                    uName.setEnabled(false);
                    uName.setText(userkaporanaam);

                    uLocation.setEnabled(false);
                    uLocation.setPrompt(userkilocation);

                    uProfileImage.setEnabled(false);
                    uProfileImage.setImageURI(imageUri);
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TO SEND THE UPDATED DATA TO FIREBASE
            }
        });
        uProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progDialog.setMessage("opening........");
                progDialog.show();
                uploadImage();
            }
        });

        return v;
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
                uProfileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
