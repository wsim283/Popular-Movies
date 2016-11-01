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
import java.util.Set;

import example.com.popularmovies.Movie;
import example.com.popularmovies.PopularMoviesHelper;
import example.com.popularmovies.R;


public class MovieMainFragment extends Fragment {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private MovieRecAdapter movieRecAdapter;
    boolean twoPane;


    public interface MovieMainListener{
       void itemClicked(Movie movie);
    }
    public MovieMainFragment() {
        // Required empty public constructor
    }

    public void setTwoPane(boolean twoPane){
        this.twoPane = twoPane;
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
        RecyclerView moviesRecView = (RecyclerView)rootView.findViewById(R.id.movies_recyclerview);

        moviesRecView.setLayoutManager(new GridLayoutManager(moviesRecView.getContext(),
                getResources().getInteger(R.integer.grid_columns)));

        movieRecAdapter = new MovieRecAdapter(getActivity(),new ArrayList<Movie>());
        moviesRecView.setAdapter(movieRecAdapter);

        return rootView;
    }

    /**
     * Fetch movie list and its details.
     * It is used in onStart so that the list gets updated when user changed
     * the sort order from popularity to top-rated and vice versa.
     */
    private void updateMovieList(){
        FetchMovieListTask fetchMoviesTask = new FetchMovieListTask(getContext(),movieRecAdapter,false);
        //for convenience, the entry values of each entry are set to the file_path for the url,
        //for e.g "Popularity" entry is set to "movie/popular?" entry value
        String sortByPath = PreferenceManager.getDefaultSharedPreferences(getContext())
                .getString(getString(R.string.pref_sort_key),getString(R.string.movie_popular_file_path));

        if(sortByPath.equals(getString(R.string.favourites))){
            String favouritesKey = getString(R.string.favourites);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            Set<String> favouritesSet=  sharedPreferences.getStringSet(
                    favouritesKey,
                    new HashSet<String>()
            );

            fetchMoviesTask.setSingle(true);
            fetchMoviesTask.execute(favouritesSet.toArray(new String[favouritesSet.size()]));
        }else {
            fetchMoviesTask.execute(sortByPath);
        }

    }



}
