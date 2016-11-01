package example.com.popularmovies.Detail;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import example.com.popularmovies.Main.MovieRecAdapter;
import example.com.popularmovies.Movie;
import example.com.popularmovies.PopularMoviesHelper;

/**
 * Created by WSIM on 4/10/2016.
 */

public class FetchMovieTrailerTask extends AsyncTask<String,Void,ArrayList<Trailer>> {

    Context context;
    DetailRecAdapter detailRecAdapter;

    public FetchMovieTrailerTask(Context context, DetailRecAdapter detailRecAdapter){
        this.context = context;
        this.detailRecAdapter = detailRecAdapter;

    }
    private final String LOG_TAG = this.getClass().getSimpleName();
    @Override
    protected ArrayList<Trailer> doInBackground(String... urlPath) {

        ArrayList<Trailer> trailers = new ArrayList<>();
        HttpURLConnection urlConnection = null;

        //sets up the base url. This includes the main url and the sort path given in the param
        Uri.Builder baseUrl = PopularMoviesHelper.getUri(context, urlPath[0]);

        try {
            URL url = new URL(baseUrl.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            //after research, response code 200 means we did not get any problem in connecting.
            //that means we should handle the other codes such as 404 as errors,
            //I did this by logging it
            if (urlConnection.getResponseCode() == 200) {
                String jsonResponseStr = PopularMoviesHelper.extractJsonResponseFromStream(urlConnection.getInputStream());

                trailers = PopularMoviesHelper.extractMovieTrailersFromJson(jsonResponseStr, context);


            } else {
                String errorMessage = "problem getting into " + baseUrl.toString();
                Log.e(LOG_TAG, errorMessage);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSON is either empty or format is changed!");
        } catch (IOException e) {
            Log.e(LOG_TAG, "could not open connection!");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return trailers;
    }

    @Override
    protected void onPostExecute(ArrayList<Trailer> trailers) {
        if(trailers == null || trailers.size() == 0){

            return;
        }

        this.detailRecAdapter.updateData(trailers);
    }
}
