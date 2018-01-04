package com.projekt.projekt;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andras on 1/3/2018.
 */

public class DataModel implements Parcelable{

    private String mUrl;
    private String mTitle;

    public DataModel(String url, String title) {
        mUrl = url;
        mTitle = title;
    }

    protected DataModel(Parcel in) {
        mUrl = in.readString();
        mTitle = in.readString();
    }

    public static final Creator<DataModel> CREATOR = new Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel in) {
            return new DataModel(in);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public static  DataModel[] getSpacePhotos() {

        return new DataModel[]{
                new DataModel("http://i.imgur.com/zuG2bGQ.jpg", "Galaxy"),
                new DataModel("http://i.imgur.com/ovr0NAF.jpg", "Space Shuttle"),
                new DataModel("http://i.imgur.com/n6RfJX2.jpg", "Galaxy Orion"),
                new DataModel("http://i.imgur.com/qpr5LR2.jpg", "Earth"),
                new DataModel("http://i.imgur.com/pSHXfu5.jpg", "Astronaut"),
                new DataModel("http://i.imgur.com/3wQcZeY.jpg", "Satellite"),
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUrl);
        parcel.writeString(mTitle);
    }
}
