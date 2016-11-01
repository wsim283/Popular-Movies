package example.com.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import example.com.popularmovies.Detail.Review;
import example.com.popularmovies.Detail.Trailer;

/**
 * Created by Welly Mulyadi on 26/08/2016.
 * Contains all the helper methods needed for this app
 *
 */

public class PopularMoviesHelper {


    public  static final int YEAR_INDEX = 0;
    public static final int MONTH_INDEX = 1;
    public static final int DAY_INDEX = 2;
    static final int MAX_DATE_INDICES = 3;
    static final String[] TABS = {"Info", "Reviews"};
    static final int MAX_TABS = 2;


    /**
     * Firstly, this method wraps the given inputStream into an InputStreamReader then BufferedReader for more efficient reading
     * @param inputStream contains response Json string from a recent connection to the API
     * @return Json String that will be used to extract movies
     * @throws IOException
     */
    public static String extractJsonResponseFromStream(InputStream inputStream) throws IOException{
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = bufferedReader.readLine();
        StringBuffer jsonResponseStr = new StringBuffer();
        while(line != null){
            jsonResponseStr.append(line);
            line = bufferedReader.readLine();
        }

        return jsonResponseStr.toString();
    }
    /**
     * Extract movies from the given jsonStr
     * @param jsonStr extracted Json response string taken from input stream
     * @param context we need this to access resource strings.
     *                Since this is called from MovieMainFragment class, this context is given from there
     * @return an Arraylist of movies extracted from Json
     * @throws JSONException
     */
    public static ArrayList<Movie> extractMoviesFromJson(String jsonStr, Context context) throws JSONException{

        if(jsonStr == null || jsonStr.equals("")){
            return null;
        }
        ArrayList<Movie> movies = new ArrayList<>();

        JSONArray results;
        JSONObject jsonResponse = new JSONObject(jsonStr);
        results = jsonResponse.getJSONArray(context.getString(R.string.results_json_key));

        for(int index = 0; index < results.length(); index++) {
            JSONObject movieJSON = results.getJSONObject(index);

            movies.add(extractSingleMovieFromJson(movieJSON,context));
        }
        return movies;
    }

    public static Movie extractSingleMovieFromJson(JSONObject movieJSON, Context context) throws JSONException{
        String id = movieJSON.getString(context.getString(R.string.id_json_key));
        String title =  movieJSON.getString(context.getString(R.string.title_json_key));
        String synopsis =  movieJSON.getString(context.getString(R.string.synopsis_json_key));
        String date = movieJSON.getString(context.getString(R.string.date_json_key));


        String poster_path = movieJSON.getString(context.getString(R.string.poster_json_key));
        StringBuilder imageUrl = new StringBuilder(context.getString(R.string.image_base_url));
        imageUrl.append(context.getString(R.string.image_default_size_path));
        imageUrl.append(poster_path);

        Double rating =  Double.parseDouble(movieJSON.getString(context.getString(R.string.rating_json_key)));
        return new Movie(id, title, synopsis, date, imageUrl.toString(), rating);
    }
    public static ArrayList<Trailer> extractMovieTrailersFromJson(String jsonStr, Context context) throws JSONException{

        if(jsonStr == null || jsonStr.equals("")){
            return null;
        }
        ArrayList<Trailer> trailers = new ArrayList<>();


        JSONObject jsonResponse = new JSONObject(jsonStr);
        JSONArray results = jsonResponse.getJSONArray(context.getString(R.string.results_json_key));


        for(int index = 0; index < results.length(); index++) {
            JSONObject movieJSON = results.getJSONObject(index);

            Trailer  trailer = new Trailer(movieJSON.getString(context.getString(R.string.key_json_key)),
                    movieJSON.getString(context.getString(R.string.trailer_name_json_key)));

            trailers.add(trailer);

        }
        return trailers;
    }

    public static String[] extractDateComponents(String dateStr){

        String[] dateComponents =  new String[MAX_DATE_INDICES];
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        try {
            Date date = simpleDateFormat.parse(dateStr);
            dateComponents[YEAR_INDEX] = ""+(date.getYear()+1900);
            dateComponents[MONTH_INDEX] = getMonthInStr(date.getMonth());
            dateComponents[DAY_INDEX] = "" + date.getDate() + ", ";

        }catch (ParseException pe){
            Log.v("ERROR", "parsing date");
        }

        return dateComponents;

    }

    private static String getMonthInStr(int month){
        String monthStr;

        switch (month){
            case 0:
                monthStr = "January";
                break;
            case 1:
                monthStr = "February";
                break;
            case 2:
                monthStr = "March";
                break;
            case 3:
                monthStr = "April";
                break;
            case 4:
                monthStr = "May";
                break;
            case 5:
                monthStr = "June";
                break;
            case 6:
                monthStr = "July";
                break;
            case 7:
                monthStr = "August";
                break;
            case 8:
                monthStr = "September";
                break;
            case 9:
                monthStr = "October";
                break;
            case 10:
                monthStr = "November";
                break;
            default:
                monthStr = "December";
        }

        return monthStr;
    }
    public static ArrayList<Review> extractMovieReviewsFromJson(String jsonStr, Context context) throws JSONException{

        if(jsonStr == null || jsonStr.equals("")){
            return null;
        }
        ArrayList<Review> reviews = new ArrayList<>();

        JSONObject jsonResponse = new JSONObject(jsonStr);
        JSONArray results = jsonResponse.getJSONArray(context.getString(R.string.results_json_key));


        for(int index = 0; index < results.length(); index++) {
            JSONObject reviewJSON = results.getJSONObject(index);

            reviews.add(new Review(reviewJSON.getString(context.getString(R.string.content_json_key)),
                    reviewJSON.getString(context.getString(R.string.review_author_param))
            ));

        }
        return reviews;
    }



    public static Uri.Builder getUri(Context context,String sortPath){
        Uri.Builder uri =  Uri.parse(context.getString(R.string.api_base_url) +
                sortPath)
                .buildUpon();

        //appends the API key to the url, please see README.md for more details for setting up API key
        uri.appendQueryParameter(context.getString(R.string.api_key_param), BuildConfig.THE_MOVIE_DB_API_KEY);

        return uri;
    }

    public static TabLayout setLayoutTab(View parent, int layoutId){

        TabLayout assembledTabLayout = (TabLayout)parent.findViewById(layoutId);

        for(int ix = 0; ix < MAX_TABS; ix++){
            assembledTabLayout.addTab(assembledTabLayout.newTab().setText(TABS[ix]));
        }

        return assembledTabLayout;
    }


}
