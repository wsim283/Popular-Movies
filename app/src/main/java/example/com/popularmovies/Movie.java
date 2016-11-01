package example.com.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Welly Mulyadi on 25/08/2016.
 * a Placeholder class for Movie
 * This class implements Parcelable so that it can be passed into other activities
 */

public class Movie implements Parcelable{

    private String title, synopsis, date, imageUrl;
    private double rating;
    private String id;


    public Movie(String id,String title, String synopsis, String date, String imageUrl, double rating){
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
        this.date = date;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    private Movie(Parcel in){
        this.id = in.readString();
        this.title = in.readString();
        this.synopsis = in.readString();
        this.date = in.readString();
        this.imageUrl = in.readString();
        this.rating = in.readDouble();
    }

    public String getId(){return id;}
    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getRating() {
        return rating;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(synopsis);
        parcel.writeString(date);
        parcel.writeString(imageUrl);
        parcel.writeDouble(rating);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
