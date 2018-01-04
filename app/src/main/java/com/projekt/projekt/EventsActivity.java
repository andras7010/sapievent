package com.projekt.projekt;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.projekt.projekt.Tablak.Eventek;

import de.hdodenhof.circleimageview.CircleImageView;


public class EventsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String EXTRA_SPACE_PHOTO = "EventsActivity.SPACE_PHOTO";
    private ImageView mImageView;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mImageView = (ImageView) findViewById(R.id.image);
        DataModel spacePhoto = getIntent().getParcelableExtra(EXTRA_SPACE_PHOTO);

        Log.d("Valami", "Itt vagyok");
        Glide.with(this)
                .load(spacePhoto.getUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mImageView);

        CircleImageView creator = (CircleImageView) this.findViewById(R.id.creator);
        ;
        Glide.with(this)
                .load("http://i.imgur.com/zuG2bGQ.jpg")
                .into(creator);

        Button button = (Button) findViewById(R.id.callbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0722232463"));

                if (ActivityCompat.checkSelfPermission(EventsActivity.this,
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        DatabaseReference mDatabaseReference =
                FirebaseDatabase.getInstance().getReference().child("event");
        final Query query = mDatabaseReference;
        Log.d("kecske", "kecske");


        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null) {
                    Eventek value = dataSnapshot.getValue(Eventek.class);
                    Log.d("Shit", "Value is: " + value.toString());

                    if (dataSnapshot.exists()) {
                        Log.d("Shit", "Value is: " + "letezik");
                        Toast.makeText(getApplicationContext(), "****Fuck yeah****", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), "****NOT FOUND****", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(getApplicationContext(), "****NOT FOUND****", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "****NOT FOUND****", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}

