package com.example.sidrajawaid.demofirebase.MainApplicationContent;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sidrajawaid.demofirebase.MainApplicationContent.DataModel;
import com.example.sidrajawaid.demofirebase.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
//import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

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
    Boolean check=false;
    Uri imageUri;
    Bitmap bitmap;
    ImageView uProfileImage;
    TextView tv1;
    int ageInt;
    private String nName,nAge,nLocation,nID,nEmail;      //variables for new values
    ArrayAdapter spinneradapter,tempadapter;
    ArrayList spinneritem=new ArrayList<>();
    ArrayList tempspinner=new ArrayList<>();
    public static final String TAG="EditProfileFragment";
    public static final int RESULT_CODE=000;
    Uri noimage= Uri.parse("https://firebasestorage.googleapis.com/v0/b/demofirebase-7d7d6.appspot.com/o/profilepics%2Fnoimage.png?alt=media&token=1bff4b60-6a59-4687-91b2-936b1fc9e09e");
    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_editprofile, container, false);
        progDialog = new ProgressDialog(getContext());
        //GETTING OBJECT REFERNCE TO ALL THE WIDGETS
        uProfileImage = v.findViewById(R.id.profile_image);
        uProfileImage.setEnabled(false);
        profileeditswitch = v.findViewById(R.id.toggle1);
        uName = v.findViewById(R.id.edittext1);
        uAge = v.findViewById(R.id.uepedittext4);
        uLocation = v.findViewById(R.id.spinner);
        uLocation.setEnabled(false);
        btn = v.findViewById(R.id.btnSaveChanges);
        //ADDING ITEMS TO ARRAYLIST FOR SPINNER
        spinneritem.add("Select Location");
        spinneritem.add("Balochistan,Pakistan");
        spinneritem.add("Karachi,Pakistan");
        spinneritem.add("Islamabad,Pakistan");
        spinneritem.add("Lahore,Pakistan");
        spinneritem.add("Multan,Pakistan");
        spinneritem.add("Faisalabad,Pakistan");
        //SETTING ADAPTER
        tempadapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,tempspinner);
        spinneradapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,spinneritem);
        //GETTING FIREBASE REFERNCES
        mFirebaseAuth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference("profilepics/").child(mFirebaseAuth.getUid());
        mDatabaseReference=FirebaseDatabase.getInstance().getReference().child("users").child(mFirebaseAuth.getUid());
        //LOGGING
        Log.d(TAG, "AGAYA current  uid = " +mFirebaseAuth.getCurrentUser().getUid());
        Log.d(TAG, "AGAYA uid = " +mFirebaseAuth.getUid());
        Log.d(TAG,"OUTPOUT  "+ mDatabaseReference.getKey());
        //ADDING LISTENER TO DATABASE AND GETTING VALUES
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userkiage=dataSnapshot.child("userAge").getValue(String.class);
                userkaemail=dataSnapshot.child("userEmail").getValue(String.class);
                userkaporanaam=dataSnapshot.child("userFullname").getValue(String.class);
                userkiauthid=dataSnapshot.child("userID").getValue(String.class);
                userkilocation=dataSnapshot.child("userLocation").getValue(String.class);
                Log.d(TAG,"user ki location = "+userkilocation );
                uName.setText(userkaporanaam+" ");
                uAge.setText(userkiage+"");
                tempspinner.add(userkilocation);
                uLocation.setAdapter(tempadapter);
                nEmail=userkaemail.toString();
                nID=userkiauthid.toString();
                Log.d("yeh values nhi arhe "+nEmail,nID);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,"Koi data nhi aya " );
            }
        });
        //ADDING LISTENER TO FIREBASE STORAGE
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getContext()).load(uri).into(uProfileImage);
                Log.d(TAG,"image agai ha = "+ uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"image save mhi ha = "+ e.getMessage());
                Picasso.with(getContext()).load(noimage).into(uProfileImage);
            }
        });

        //TOGGLEBUTTON CHECK LISTENER
        profileeditswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    uName.setEnabled(true);
                    uAge.setEnabled(true);
                    uLocation.setEnabled(true);
                    uLocation.setAdapter(spinneradapter);
                    uProfileImage.setEnabled(true);
                    Toast.makeText(getContext(), "is checked", Toast.LENGTH_SHORT).show();
                    performingValidation();
                }
                else
                {
                    uName.setEnabled(false);
                    uAge.setEnabled(false);
                    uLocation.setEnabled(false);
                    uProfileImage.setEnabled(false);
                    uLocation.setAdapter(null);
                    uLocation.setAdapter(tempadapter);
                    Toast.makeText(getContext(), "is not checked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TO SEND THE UPDATED DATA TO FIREBASE
                if(check) {
                    setDataToFirebase();
                    disableEverything();
                }
                else {
                    Toast.makeText(getContext(), "Make some changes to be saved", Toast.LENGTH_SHORT).show();
                    return;
                }
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
    private void disableEverything() {
        Toast.makeText(getContext(), "changes saved", Toast.LENGTH_SHORT).show();
        profileeditswitch.setChecked(false);
        uProfileImage.setEnabled(false);
        uName.setEnabled(false);
        uAge.setEnabled(false);
        uLocation.setEnabled(false);
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
    public void sendingImageToStorage()
    {
        if(imageUri!=null)
        {
            storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUri = taskSnapshot.getDownloadUrl();
                            Toast.makeText(getContext(), "Upload done = "+downloadUri, Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"image upload hogai ha  ");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG,"image upload nhi hoe ");
                }
            });
        }
    }
    //PERFORM VALIDATION
    public boolean performingValidation()
    {
        nName=uName.getText().toString().trim();
        nAge=uAge.getText().toString().trim();
        nLocation=uLocation.getSelectedItem().toString();
        //Validation for name
        if(TextUtils.isEmpty(nName)||nName==" ")
        {
            uName.setError("Please enter new Name");
            return false;
        }
        // validation for age
        try
        {
            //user_age= Integer.parseInt(string_userage);
            if (TextUtils.isEmpty(nAge)||nAge==" "||uAge==null) {
                ageInt= Integer.parseInt(nAge);
            }
        }
        catch (NumberFormatException e)
        {
            uAge.setError("Required");
            return false;
        }
        if(Integer.parseInt(nAge)<12||Integer.parseInt(nAge)>90)
        {
            uAge.setError("Enter correct age");
            return false;
        }
        //Validation for Location
        if(nLocation==spinneritem.get(0))
        {
            setSpinnerError(uLocation,"Select Location");
            return false;
        }
        check=true;
        return true;
    }
    //SETTING DATA TO FIREBASE
    public void setDataToFirebase()
    {
        mDatabaseReference.setValue(new DataModel(nEmail,nName,nAge,nLocation,userkiauthid));
        Log.d(TAG,"gaya data firebase ma");
        Log.d(TAG,"gaya data firebase ma "+nName+nAge+nLocation);
        sendingImageToStorage();
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
