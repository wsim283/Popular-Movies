package example.com.popularmovies.Main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import example.com.popularmovies.Detail.MovieDetailActivity;
import example.com.popularmovies.Movie;
import example.com.popularmovies.R;

/**
 * Created by Welly Mulyadi on 25/08/2016.
 * Just a custom ArrayAdapter so that it takes the Movie placeholder as the type of adapter
 * In the Movie placeholder contains the image url taken from the API which we need to load
 */

public class MovieRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private List<Movie> movieList;
    private Context context;
    private boolean setFirstMovieClicked;
    boolean twoPane;

    public MovieRecAdapter(Context context, List<Movie> movieList){
        this.context = context;
        this.movieList = movieList;
    }
    /**
     *There isn't any "addAll()" function like ArrayAdapters one, so we need to update the movie list manually
     * @param updatedMovieList the new movie list that needs to be populated in the RecyclerView
     */
    public void updateData(List<Movie> updatedMovieList){
        movieList.clear();
        if(updatedMovieList != null) {
            movieList.addAll(updatedMovieList);
        }
        notifyDataSetChanged();
        setFirstMovieClicked = true;
    }

    public void setFirstMovieClicked(boolean twoPane){
        this.twoPane = twoPane;
       setFirstMovieClicked = twoPane;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_poster,parent,false);
        return new Poster(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        Poster poster = (Poster)holder;
        Picasso.with(context).load(movieList.get(position).getImageUrl()).into(poster.getPosterView());


        final int pos = position;
        poster.getPosterView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MovieMainFragment.MovieMainListener)context).itemClicked(movieList.get(pos));
            }
        });

        if(twoPane) {
            if (setFirstMovieClicked && pos == 0) {
                ((MovieMainFragment.MovieMainListener) context).itemClicked(movieList.get(pos));
                setFirstMovieClicked = false;
            }
        }

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}



