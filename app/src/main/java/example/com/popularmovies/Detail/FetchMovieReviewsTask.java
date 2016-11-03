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

import example.com.popularmovies.PopularMoviesHelper;

/**
 * Created by WSIM on 31/10/2016.
 */

public class FetchMovieReviewsTask extends AsyncTask<String,Void,ArrayList<Review>> {

    Context context;
    ReviewsRecAdapter movieRevRecAdapter;

    public FetchMovieReviewsTask(Context context, ReviewsRecAdapter movieRecAdapter){
        this.context = context;
        this.movieRevRecAdapter = movieRecAdapter;

    }
    private final String LOG_TAG = this.getClass().getSimpleName();
    @Override
    protected ArrayList<Review> doInBackground(String... urlPath) {

        ArrayList<Review> reviews = null;
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
                reviews = PopularMoviesHelper.extractMovieReviewsFromJson(jsonResponseStr, context);

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

        return reviews;
    }

    @Override
    protected void onPostExecute(ArrayList<Review> reviews) {
        if(reviews == null){
            return;
        }

        this.movieRevRecAdapter.updateData(reviews);
    }
    }
