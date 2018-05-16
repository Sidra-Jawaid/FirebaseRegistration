package com.example.sidrajawaid.demofirebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class UserProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference mDatabaseReference;
    FirebaseAuth mFirebaseAuth;
    StorageReference storageReference;
    Toolbar toolbar;
    ImageView profileimage;
    TextView emailtext,nametext;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    public static final String TAG="UserProfile";
    private ActionBarDrawerToggle mActioBarDrawertoggle;
    String userkaporanaam,userkaemail;
    FragmentTransaction ft;
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        toolbar=findViewById(R.id.relayout);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawerlayout);
        mNavigationView=findViewById(R.id.nabview);
        mActioBarDrawertoggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mActioBarDrawertoggle);
        mActioBarDrawertoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationView.setNavigationItemSelectedListener(this);

        View headerview=mNavigationView.getHeaderView(0);
        nametext=(TextView)headerview.findViewById(R.id.layouttext1);
        emailtext= (TextView)headerview.findViewById(R.id.layouttext2);
        profileimage=(ImageView)headerview.findViewById(R.id.person_image);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(mFirebaseAuth.getUid());
        Log.d(TAG, "AGAYA current  uid = " +mFirebaseAuth.getCurrentUser().getUid());
        Log.d(TAG, "AGAYA uid = " +mFirebaseAuth.getUid());
        Log.d(TAG,"OUTPOUT  "+ mDatabaseReference.getKey());
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userkaemail=dataSnapshot.child("userEmail").getValue(String.class);
                userkaporanaam=dataSnapshot.child("userFullname").getValue(String.class);
                emailtext.setText(userkaemail);
                nametext.setText(userkaporanaam);
                Log.d("value agai ha {0},{1}" +userkaemail,userkaporanaam);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,"Koi data nhi aya " );
            }
        });
        fm =getSupportFragmentManager();
        ft=fm.beginTransaction();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if(mActioBarDrawertoggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.one:
                Log.d(TAG,"item clicked");
                Toast.makeText(UserProfile.this, "Item1 clicked", Toast.LENGTH_SHORT).show();
                EditProfileFragment editProfileFragment=new EditProfileFragment();
                ft.replace(R.id.homescreenfragment,editProfileFragment);
                ft.commit();

                break;
            case R.id.two:
                Log.d(TAG,"item clicked");
                Toast.makeText(UserProfile.this, "Item2 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.three:
                Log.d(TAG,"item clicked");
                Toast.makeText(UserProfile.this, "Item3 clicked", Toast.LENGTH_SHORT).show();
                signOutDialogFragment();
                //startActivity(new Intent(getApplication(),LogIn.class));
                break;
            case R.id.four:
                Log.d(TAG,"item clicked");
                Toast.makeText(UserProfile.this, "Item4 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.five:
                Log.d(TAG,"item clicked");
                Toast.makeText(UserProfile.this, "Item5 clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void signOutDialogFragment() {
        final AlertDialog.Builder mSignoutAlert = new AlertDialog.Builder(this);
        mSignoutAlert.setMessage("Are you sure you want to exit?")
                .setIcon(R.drawable.bacground)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(UserProfile.this,MainActivity.class));
                        Log.d(TAG,"User signed out");
                    }
                })
                .setCancelable(false)
                .show();
    }
}
