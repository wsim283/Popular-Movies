package example.com.popularmovies.Detail;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import example.com.popularmovies.R;

/**
 * Created by WSIM on 5/10/2016.
 */

public class DetailInfo extends RecyclerView.ViewHolder{


    private TextView year,date,synopsis, rating;
    private ImageView thumbnailView;
    private Button favButton;
    public DetailInfo(View parent) {
        super(parent);
        date = (TextView)parent.findViewById(R.id.date);
        year = (TextView)parent.findViewById(R.id.year);
        synopsis = (TextView)parent.findViewById(R.id.synopsis);
        rating = (TextView)parent.findViewById(R.id.rating_textview);
        thumbnailView = (ImageView)parent.findViewById(R.id.movie_poster_thumbnail);
        favButton = (Button)parent.findViewById(R.id.favourite_button);

    }

    public TextView getYearView(){return year;}
    public TextView getDateView(){return date;}
    public TextView getSynopsisView(){return synopsis;}
    public TextView getRatingView(){return rating;}
    public ImageView getThumbnailView(){return thumbnailView;}
    public Button getFavBtnView(){return favButton;}
}
