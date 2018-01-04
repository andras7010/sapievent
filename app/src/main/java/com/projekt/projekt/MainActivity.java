package com.projekt.projekt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.projekt.projekt.AccountActivity.LoginActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FirebaseAuth auth  =  FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
        {
            Toast.makeText(getApplicationContext(), "Bevan jelentkezve a felhasznalo", Toast.LENGTH_LONG).show();
            CircleImageView headerImage  = (CircleImageView) this.findViewById(R.id.header_profile);;
            Glide.with(this)
                    .load("http://i.imgur.com/zuG2bGQ.jpg")
                    .into(headerImage);
            headerImage.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,UserDetails.class));
                }
            });
            View addButton = findViewById(R.id.add_event_button);
            addButton.setVisibility(View.VISIBLE);

            View signoutButton = findViewById(R.id.signout_button);
            signoutButton.setVisibility(View.VISIBLE);
            signoutButton.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    auth.signOut();
                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                    finish();
                }
            });
            addButton.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,AddEventActivity.class));
                }
            });

        }
        else
        {
            Toast.makeText(getApplicationContext(), "A felhasznalo nincs bejelentkezve", Toast.LENGTH_LONG).show();
            View loginButton = findViewById(R.id.login_button);
            loginButton.setVisibility(View.VISIBLE);
            loginButton.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                }
            });

        }



        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_images);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageGalleryAdapter adapter = new ImageGalleryAdapter(this, DataModel.getSpacePhotos());
        recyclerView.setAdapter(adapter);
    }



    private class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>  {

        @Override
        public ImageGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View photoView = inflater.inflate(R.layout.item_photo, parent, false);
            ImageGalleryAdapter.MyViewHolder viewHolder = new ImageGalleryAdapter.MyViewHolder(photoView);


            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ImageGalleryAdapter.MyViewHolder holder, int position) {

            DataModel spacePhoto = mSpacePhotos[position];
            TextView title = (TextView) holder.itemView.findViewById(R.id.eventtitle);
            ImageView imageView = holder.mPhotoImageView;
            CircleImageView profileImage = holder.mProfileImage;
            title.setText(spacePhoto.getTitle());
            Glide.with(mContext)
                    .load(spacePhoto.getUrl())
                    .into(imageView);

            Glide.with(mContext)
                    .load(spacePhoto.getUrl())
                    .into(profileImage);
        }

        @Override
        public int getItemCount() {
            return (mSpacePhotos.length);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView mPhotoImageView;
            public CircleImageView mProfileImage;

            public MyViewHolder(View itemView) {

                super(itemView);
                mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);
                mProfileImage = (CircleImageView) itemView.findViewById(R.id.profile_image);
                TextView etitle = (TextView) itemView.findViewById(R.id.eventtitle);
                //etitle.setText("Mukodjel pls");

                //DataModel spacePhoto = mSpacePhotos[this.mPhotoImageView.getId()];
                //etitle.setText(spacePhoto.getTitle());
                itemView.setOnClickListener(this);
                Log.d("image", String.valueOf(this.getLayoutPosition()));
            }

            @Override
            public void onClick(View view) {

                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    DataModel spacePhoto = mSpacePhotos[position];
                    Intent intent = new Intent(mContext, EventsActivity.class);
                    intent.putExtra(EventsActivity.EXTRA_SPACE_PHOTO, spacePhoto);
                    startActivity(intent);
                }
            }
        }

        private DataModel[] mSpacePhotos;
        private Context mContext;

        public ImageGalleryAdapter(Context context, DataModel[] spacePhotos) {
            mContext = context;
            mSpacePhotos = spacePhotos;
        }


    }
}
