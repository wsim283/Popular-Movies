package example.com.popularmovies.Main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import example.com.popularmovies.R;

/**
 * Created by WSIM on 4/10/2016.
 * A ViewHolder for RecyclerView
 */

public class Poster extends RecyclerView.ViewHolder{

    private ImageView posterView;

    public Poster(View parent){
        super(parent);
        posterView = (ImageView)parent.findViewById(R.id.movie_poster_imageview);
    }

    public ImageView getPosterView() {
        return posterView;
    }
}
