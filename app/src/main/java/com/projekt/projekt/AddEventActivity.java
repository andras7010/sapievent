package com.projekt.projekt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.projekt.projekt.Tablak.Eventek;


public class AddEventActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final int GALLERY_INTENT = 2;
    public static final int PLACE_PICKER_REQUEST = 1;
    public final Eventek event = new Eventek();
    public Uri uri = null;
    public int isLocationChosen = 0;
    private StorageReference mStorage;

    final FirebaseAuth auth  =  FirebaseAuth.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_add_event);
        mStorage = FirebaseStorage.getInstance().getReference();

        ImageView eventimage  = (ImageView) this.findViewById(R.id.addimage);

        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(eventimage);
        Glide.with(this)
                .load(R.drawable.placeholder)
                .into(imageViewTarget);
        Log.d("pls","itt vagyok");

        eventimage.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Katt a kepre", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
                Log.d("pls","itt vagyok");
            }
        });

        Button button = (Button) findViewById(R.id.selectlocation);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try
                {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    startActivityForResult(builder.build(AddEventActivity.this), PLACE_PICKER_REQUEST);
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Valami miatt nem leget a terkepet elinditani", Toast.LENGTH_LONG).show();
                    Log.d("Jajj","Itt valami exception van");
                }

            }
        });

        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText eventTitleField = (EditText) findViewById(R.id.eventtitle);
                EditText eventDescriptionField = (EditText) findViewById(R.id.eventdescription);
                String eventTitle = eventTitleField.getText().toString();
                String eventDescription = eventDescriptionField.getText().toString();
                if(TextUtils.isEmpty(eventTitle) || TextUtils.isEmpty(eventDescription))
                {
                    Toast.makeText(getApplicationContext(), "Megkerlek tolts ki minden mezot", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    event.setEventDescription(eventDescription);
                    event.setEventName(eventTitle);
                }
                if(uri == null)
                {
                    Toast.makeText(getApplicationContext(), "Megkerlek tolts fel egy kepet az eventnek", Toast.LENGTH_LONG).show();
                    return;
                }
                if(isLocationChosen == 0)
                {
                    Toast.makeText(getApplicationContext(), "Megkerlek valasz ki egy helyseget", Toast.LENGTH_LONG).show();
                    return;
                }
                event.setCreatorUid(user.getUid());
                writeNewEvent(event);

            }
        });
    }

    private void writeNewEvent(Eventek event) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("eventek").push().getKey();
        mDatabase.child("eventek").child(key).setValue(event);
        startActivity(new Intent(AddEventActivity.this,MainActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            uri = data.getData();
            ImageView eventimage  = (ImageView) this.findViewById(R.id.addimage);
            Glide.with(this)
                    .load(uri)
                    .into(eventimage);
            Log.d("pls","itt vagyok");
            StorageReference filepath = mStorage.child("Eventfenykepek").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "A kep feltoltodott", Toast.LENGTH_LONG).show();
                }
            });
        }
        if(requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK){
            Log.d("google terkep",String.valueOf(resultCode));
            Place place = PlacePicker.getPlace(this, data);
            Button button = (Button) findViewById(R.id.selectlocation);
            button.setText(place.getName());
            LatLng locationdata = place.getLatLng();
            Log.d("google terkep",String.valueOf(locationdata.latitude));
            Log.d("google terkep",String.valueOf(locationdata.longitude));
            event.setEventLocationLatitude(locationdata.latitude);
            event.setEventLocationLongitude(locationdata.longitude);
            isLocationChosen = 1;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}

