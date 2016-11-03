package example.com.popularmovies.Main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import example.com.popularmovies.Movie;
import example.com.popularmovies.Orientation;
import example.com.popularmovies.PopularMoviesHelper;
import example.com.popularmovies.R;


public class MovieMainFragment extends Fragment implements Orientation {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private MovieRecAdapter movieRecAdapter;
    final int INVALID_POSITION = -1;
    RecyclerView moviesRecView;
    boolean categoryChanged = false;
    int clickedPosition;
    GridLayoutManager gLManager;
    String movieId = "";



    public interface MovieMainListener {
        void itemClicked(Movie movie, int position);
    }

    public MovieMainFragment() {
        // Required empty public constructor
    }

    public void setTwoPane(boolean twoPane) {
        movieRecAdapter.setFirstMovieClicked(twoPane);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        updateMovieList();
        super.onStart();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        moviesRecView = (RecyclerView) rootView.findViewById(R.id.movies_recyclerview);
        gLManager = new GridLayoutManager(moviesRecView.getContext(),
                getResources().getInteger(R.integer.grid_columns));

        moviesRecView.setLayoutManager(gLManager);

        movieRecAdapter = new MovieRecAdapter(getActivity(), new ArrayList<Movie>());
        moviesRecView.setAdapter(movieRecAdapter);

        if(savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.clicked_position))
                && savedInstanceState.containsKey(getString(R.string.clicked_movie_id))){
            clickedPosition = savedInstanceState.getInt(getString(R.string.clicked_position));
            movieId = savedInstanceState.getString(getString(R.string.clicked_movie_id));


        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (clickedPosition != INVALID_POSITION) {
                outState.putInt(getString(R.string.clicked_position), clickedPosition);
            outState.putString(getString(R.string.clicked_movie_id), movieId);

        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Fetch movie list and its details.
     * It is used in onStart so that the list gets updated when user changed
     * the sort order from popularity to top-rated and vice versa.
     */
    private void updateMovieList() {
        FetchMovieListTask fetchMoviesTask = new FetchMovieListTask(getContext(), movieRecAdapter, false);
        //for convenience, the entry values of each entry are set to the file_path for the url,
        //for e.g "Popularity" entry is set to "movie/popular?" entry value
        String sortByPath = PreferenceManager.getDefaultSharedPreferences(getContext())
                .getString(getString(R.string.pref_sort_key), getString(R.string.movie_popular_file_path));
        if (sortByPath.equals(getString(R.string.favourites))) {
            String favouritesKey = getString(R.string.favourites);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            Set<String> favouritesSet = sharedPreferences.getStringSet(
                    favouritesKey,
                    new HashSet<String>()
            );

            fetchMoviesTask.setSingle(true);
            fetchMoviesTask.execute(favouritesSet.toArray(new String[favouritesSet.size()]));
        } else {
            fetchMoviesTask.execute(sortByPath);
        }

    }

    /**
     * for favourites list. user has a choice to un-mark a movie from favourites.
     * This function only gets called when we are currently showing the favourites movie list
     * and when the user hit un-mark.
     */
    public void updateMovieListOffline(String movieId) {
        movieRecAdapter.removeMovieData(movieId);
    }

    public void savePosition(int clickedPosition, String movieId) {
        Log.v(LOG_TAG, "savePosition: "+ clickedPosition);
        this.clickedPosition = clickedPosition;
        this.movieId = movieId;

    }

    public void scrollToPosition(){
        List<Movie> movies = movieRecAdapter.getMovieList();
        if(clickedPosition >= movies.size() || !movieId.equals(movies.get(clickedPosition).getId())){
            clickedPosition = 0;
        }
        moviesRecView.scrollToPosition(clickedPosition);
        movieRecAdapter.setRestoreItemClicked(clickedPosition);

    }

}
