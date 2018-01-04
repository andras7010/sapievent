package com.projekt.projekt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.projekt.projekt.Tablak.Users;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserDetails extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_user_details);

        final FirebaseAuth auth  =  FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("users").orderByChild("uid").equalTo(user.getUid());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null) {
                    Users value = dataSnapshot.getValue(Users.class);
                    Log.d("Shit", "Value is: " + value.toString());

                    if (dataSnapshot.exists()) {
                        Log.d("MukodikAUserDetails", value.toString());
                        Toast.makeText(getApplicationContext(), "****Yeah boiii****", Toast.LENGTH_LONG).show();
                        EditText firstnameField = (EditText)findViewById(R.id.firstname);
                        firstnameField.setText(value.getFirstName());
                        EditText lastnameField = (EditText)findViewById(R.id.lastname);
                        lastnameField.setText(value.getLastName());
                        EditText emailField = (EditText)findViewById(R.id.email);
                        emailField.setText(user.getEmail());
                        EditText phonenumberField = (EditText)findViewById(R.id.phonenumber);
                        phonenumberField.setText(value.getPhoneNumber());
                    } else {
                        Log.d("Shit", "Value is: " + "nem letezik");
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "****NOT FOUND****", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        CircleImageView userimage  = (CircleImageView) this.findViewById(R.id.userimage);;
        Glide.with(this)
                .load("http://i.imgur.com/zuG2bGQ.jpg")
                .into(userimage);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}

