package com.example.sidrajawaid.demofirebase.MainApplicationContent;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.squareup.picasso.Picasso;

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
        //GETTING FIREBASE REFERENCES
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(mFirebaseAuth.getUid());
        storageReference= FirebaseStorage.getInstance().getReference("profilepics/").child(mFirebaseAuth.getUid());
        Log.d(TAG, "AGAYA current  uid = " +mFirebaseAuth.getCurrentUser().getUid());
        Log.d(TAG, "AGAYA uid = " +mFirebaseAuth.getUid());
        Log.d(TAG,"OUTPOUT  "+ mDatabaseReference.getKey());
        //ADDING LISTENER TO FIREBASE DATABASE
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
        //ADDING LISTENER TO FIREBASE STORAGE
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getApplication()).load(uri).into(profileimage);
                Log.d(TAG,"image agai ha = "+ uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Picasso.with(getApplication()).load("https://firebasestorage.googleapis.com/v0/b/demofirebase-7d7d6.appspot.com/o/profilepics%2Fnoimage.png?alt=media&token=1bff4b60-6a59-4687-91b2-936b1fc9e09e").into(profileimage);
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
        int id=item.getItemId();
        if(id==R.id.one)
        {
            Log.d(TAG,"item1 clicked");
            Toast.makeText(UserProfile.this, "Item1 clicked", Toast.LENGTH_SHORT).show();
            EditProfileFragment editProfileFragment=new EditProfileFragment();
            fm =getSupportFragmentManager();
            ft.replace(R.id.homescreenfragment,editProfileFragment).commit();
        }
        else if(id==R.id.two)
        {
            Log.d(TAG,"item2 clicked");
            Toast.makeText(UserProfile.this, "Item2 clicked", Toast.LENGTH_SHORT).show();
            NewsFragment newsFragment=new NewsFragment();
            fm =getSupportFragmentManager();
            ft.replace(R.id.homescreenfragment,newsFragment).commit();
        }
        else if(id==R.id.three)
        {
            Log.d(TAG,"item3 clicked");
            Toast.makeText(UserProfile.this, "Item3 clicked", Toast.LENGTH_SHORT).show();
            signOutDialogFragment();
        }
        else if(id==R.id.four)
        {
            Log.d(TAG,"item4 clicked");
            Toast.makeText(UserProfile.this, "Item4 clicked", Toast.LENGTH_SHORT).show();
            FavoriteNews favoriteNews=new FavoriteNews();
            fm =getSupportFragmentManager();
            ft.addToBackStack(null).replace(R.id.homescreenfragment,favoriteNews).commit();
        }
        else if(id==R.id.five)
        {
            Log.d(TAG,"item3 clicked");
            Toast.makeText(UserProfile.this, "Item3 clicked", Toast.LENGTH_SHORT).show();
            signOutDialogFragment();
        }
        else if(id==R.id.six)
        {
            Log.d(TAG,"item3 clicked");
            Toast.makeText(UserProfile.this, "Item3 clicked", Toast.LENGTH_SHORT).show();
            signOutDialogFragment();
        }
        else if(id==R.id.seven)
        {
            Log.d(TAG,"item7 clicked");
            Toast.makeText(UserProfile.this, "Item3 clicked", Toast.LENGTH_SHORT).show();
            aboutUsDialog();
        }

        /*switch (item.getItemId())
        {
            case R.id.one:
                Log.d(TAG,"item1 clicked");
                Toast.makeText(UserProfile.this, "Item1 clicked", Toast.LENGTH_SHORT).show();
                EditProfileFragment editProfileFragment=new EditProfileFragment();
                ft.addToBackStack(null);
                ft.add(R.id.homescreenfragment,editProfileFragment);
                //ft.commit();
                break;
            case R.id.two:
                Log.d(TAG,"item2 clicked");
                Toast.makeText(UserProfile.this, "Item2 clicked", Toast.LENGTH_SHORT).show();
                NewsFragment newsFragment=new NewsFragment();
                ft.addToBackStack(null);
                ft.add(R.id.homescreenfragment,newsFragment);
                //ft.commit();
                break;
            case R.id.three:
                Log.d(TAG,"item3 clicked");
                Toast.makeText(UserProfile.this, "Item3 clicked", Toast.LENGTH_SHORT).show();
                signOutDialogFragment();
                //startActivity(new Intent(getApplication(),LogIn.class));

            case R.id.four:
                Log.d(TAG,"item4 clicked");
                Toast.makeText(UserProfile.this, "Item4 clicked", Toast.LENGTH_SHORT).show();
                FavoriteNews favoriteNews=new FavoriteNews();
                ft.addToBackStack(null);
                ft.add(R.id.homescreenfragment,favoriteNews);
                //ft.commit();

            case R.id.five:
                Log.d(TAG,"item5 clicked");
                Toast.makeText(UserProfile.this, "Item5 clicked", Toast.LENGTH_SHORT).show();

            case R.id.six:
                Log.d(TAG,"item6 clicked");
                Toast.makeText(UserProfile.this, "Item5 clicked", Toast.LENGTH_SHORT).show();

            case R.id.seven:
                Log.d(TAG,"item7 clicked");
                Toast.makeText(UserProfile.this, "Item5 clicked", Toast.LENGTH_SHORT).show();
                //aboutUsDialog();

        }*/
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
    public void aboutUsDialog()
    {
        AlertDialog.Builder aboutus=new AlertDialog.Builder(this);
        aboutus.setTitle("About us")
                .setIcon(R.drawable.info)
                .setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore" +
                        " et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliqui" +
                        "p ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore e" +
                        "u fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deser" +
                        "unt mollit anim id est laborum Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore\" +\n" +
                        "\" et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliqui\" +\n" +
                        "\"p ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore e\" +\n" +
                        "\"u fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deser\" +\n" +
                        "\"unt mollit anim id est laborum")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
