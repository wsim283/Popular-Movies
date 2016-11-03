package example.com.popularmovies.Detail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import example.com.popularmovies.Movie;
import example.com.popularmovies.R;

/**
 *fragment of the first Tab that contains info such as ratings, release date, synopsis and trailers
 */
public class MovieInfoFragment extends Fragment {

    Movie clickedMovie;
    DetailRecAdapter detailRecAdapter;
    ArrayList<Trailer> trailers;
    private  final String LOG_TAG = this.getClass().getSimpleName();
    public MovieInfoFragment() {
        // Required empty public constructor
    }

    public interface FavouriteChangeListener{
        void favouritesUnMarkClicked(boolean isFavourite, String movieId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trailers = new ArrayList<>();
        clickedMovie = null;
    }

    @Override
    public void onStart() {
        if(clickedMovie != null) {
            retrieveTrailer();
        }
        super.onStart();
    }


    private void retrieveTrailer() {

        Log.v(LOG_TAG,String.format(
                getString(R.string.movie_trailers_path),
                clickedMovie.getId()));
        FetchMovieTrailerTask fetchTrailerTask = new FetchMovieTrailerTask(getContext(),detailRecAdapter);
        fetchTrailerTask.execute(String.format(
                getString(R.string.movie_trailers_path),
                clickedMovie.getId()
        ));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_info, container, false);

        if(getArguments() != null) {
            clickedMovie = getArguments().getParcelable(getString(R.string.movie_extra));
        }

        RecyclerView detailRecView = (RecyclerView) rootView.findViewById(R.id.detail_recycler_view);

        detailRecView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        detailRecAdapter = new DetailRecAdapter(getContext(),clickedMovie, trailers);
        detailRecView.setAdapter(detailRecAdapter);

        return rootView;
    }


}
