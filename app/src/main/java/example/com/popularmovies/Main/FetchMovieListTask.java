package example.com.popularmovies.Main;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import example.com.popularmovies.Movie;
import example.com.popularmovies.PopularMoviesHelper;

/**
 * Created by WSIM on 4/10/2016.
 */

public class FetchMovieListTask extends AsyncTask<String,Void,ArrayList<Movie>> {

    Context context;
    MovieRecAdapter movieRecAdapter;
    boolean singleMovie;

    public interface DataListener{
        void onDataLoaded();
    }
    public FetchMovieListTask(Context context,  MovieRecAdapter movieRecAdapter, boolean singleMovie){
        this.context = context;
        this.movieRecAdapter = movieRecAdapter;
        this.singleMovie = singleMovie;
    }

    public void setSingle(boolean singleMovie){
        this.singleMovie = singleMovie;
    }
    private final String LOG_TAG = this.getClass().getSimpleName();
    @Override
    protected ArrayList<Movie> doInBackground(String... sortByPath) {

        HttpURLConnection urlConnection = null;

        ArrayList<Movie> fetchedMovies = new ArrayList<>();
        //sets up the base url. This includes the main url and the sort path given in the param
        for(int ix = 0; ix < sortByPath.length; ix++) {
            Uri.Builder baseUrl = PopularMoviesHelper.getUri(context, sortByPath[ix]);

            try {
                URL url = new URL(baseUrl.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                //after research, response code 200 means we did not get any problem in connecting.
                //that means we should handle the other codes such as 404 as errors,
                //I did this by logging it
                if (urlConnection.getResponseCode() == 200) {
                    String jsonResponseStr = PopularMoviesHelper.extractJsonResponseFromStream(urlConnection.getInputStream());
                    if(singleMovie){
                        fetchedMovies.add(PopularMoviesHelper.extractSingleMovieFromJson(new JSONObject(jsonResponseStr),context));
                    }else {
                        Log.v(LOG_TAG, "sortByPath size: " + sortByPath.length);
                        fetchedMovies = PopularMoviesHelper.extractMoviesFromJson(jsonResponseStr, context);
                    }
                } else {
                    String errorMessage = "problem getting into " + baseUrl.toString();
                    Log.e(LOG_TAG, errorMessage);
                }

            } catch (JSONException e) {
                Log.e(LOG_TAG, e.toString());
            } catch (IOException e) {
                Log.e(LOG_TAG, "could not open connection!");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
        return fetchedMovies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        //Took out the "movies.size == 0" logic from below
        //reason is because for favourites, you can delete a movie
        //if the logic still exist then when we have 0 favourite movies from 1 favourite movie,
        // it will just return and then upDateData is ignored
        if(movies == null){
            return;
        }


        movieRecAdapter.updateData(movies);
        ((DataListener)context).onDataLoaded();
    }
}
