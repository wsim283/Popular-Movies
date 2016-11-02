package example.com.popularmovies.Detail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import example.com.popularmovies.Movie;
import example.com.popularmovies.PopularMoviesHelper;
import example.com.popularmovies.R;



/**
 * Created by WSIM on 5/10/2016.
 */

public class DetailRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final String LOG_TAG = this.getClass().getSimpleName();
    private  final int ITEMS_EMPTY = 0;
    private  final int TOTAL_DEFAULT_ITEMS = 1; //title and info is compulsory
    private final int LIST_ITEM_INFO = 0;
    private final int LIST_ITEM_TRAILER = 1;


    private Context context;
    private Movie clickedMovie;
    private ArrayList<Trailer> trailers;
    boolean fetchComplete = false;
    SharedPreferences sharedPreferences;
    String favouritesKey;
    Set<String> favouritesSet;


    public DetailRecAdapter(Context context, Movie clickedMovie, ArrayList<Trailer> trailers){
        super();
        this.context = context;
        this.clickedMovie = clickedMovie;
        this.trailers = trailers;
        favouritesKey = context.getString(R.string.favourites);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        favouritesSet =  sharedPreferences.getStringSet( favouritesKey, new HashSet<String>());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView;
        RecyclerView.ViewHolder viewHolder = null;

            switch (viewType) {
                case LIST_ITEM_INFO:
                    rootView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.detail_recycler_view_item_info, parent, false);
                    viewHolder = new DetailInfo(rootView);
                    break;
                case LIST_ITEM_TRAILER:
                    rootView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.detail_recycler_view_item_trailers, parent, false);
                    viewHolder = new DetailTrailer(rootView);
                    break;
            }



        return viewHolder;
    }

    public void updateData(ArrayList<Trailer> trailers){
        fetchComplete = true;
        this.trailers.clear();
        this.trailers.addAll(trailers);

        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int currentViewType = getItemViewType(position);
        switch (currentViewType){
            case LIST_ITEM_INFO:
                DetailInfo detailInfo = (DetailInfo) holder;
                String[] dateComponents = PopularMoviesHelper.extractDateComponents(clickedMovie.getDate());
                String dayMonth = dateComponents[PopularMoviesHelper.DAY_INDEX] +
                        dateComponents[PopularMoviesHelper.MONTH_INDEX];
                detailInfo.getYearView().setText(dateComponents[PopularMoviesHelper.YEAR_INDEX]);
                detailInfo.getDateView().setText(dayMonth);
                detailInfo.getSynopsisView().setText(clickedMovie.getSynopsis());
                String rating = ""+clickedMovie.getRating() + context.getString(R.string.max_rating);
                detailInfo.getRatingView().setText(rating);
                final String idFavFormat = String.format(context.getString(R.string.movie_id),clickedMovie.getId());

                if(favouritesSet.contains(idFavFormat)){
                    detailInfo.getFavBtnView().setActivated(true);
                    detailInfo.getFavBtnView().setText(context.getString(R.string.unmark_favourites_button));
                }else{
                    detailInfo.getFavBtnView().setActivated(false);
                }



                detailInfo.getFavBtnView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String toastMsg;
                        if(favouritesSet.contains(idFavFormat)){
                            v.setActivated(false);
                            ((Button)v).setText(context.getString(R.string.mark_favourites_button));
                            favouritesSet.remove(idFavFormat);
                            toastMsg = String.format(context.getString(R.string.favourite_toast_msg),
                                    clickedMovie.getTitle(),
                                    context.getString(R.string.favourite_unmark_toast_msg)
                            );
                        }else{
                            v.setActivated(true);
                            ((Button)v).setText(context.getString(R.string.unmark_favourites_button));
                            favouritesSet.add(idFavFormat);
                            toastMsg = String.format(context.getString(R.string.favourite_toast_msg),
                                    clickedMovie.getTitle(),
                                    context.getString(R.string.favourite_mark_toast_msg)
                            );

                        }

                        Iterator<String> iterator = favouritesSet.iterator();

                        while(iterator.hasNext()){
                            Log.v(LOG_TAG, iterator.next());
                        }

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(favouritesKey).commit();
                        editor.putStringSet(favouritesKey, favouritesSet).commit();

                        Toast.makeText(context, toastMsg,Toast.LENGTH_SHORT).show();

                        ((MovieInfoFragment.FavouriteChangeListener)context).favouritesClicked();
                    }
                });
                Picasso.with(context).load(clickedMovie.getImageUrl()).into(detailInfo.getThumbnailView());

                break;
            case LIST_ITEM_TRAILER:
                //// if trailerKey is 0, we need to keep "No trailer available" message available
                //we can't use the customised getTrailerKeySize() because by now, the "No trailer available"
                //message has appeared and is regarded to be a trailer item in our layout.
                //so we need to check for real trailers here
                //also this is the same logic as for reviews
                if(trailers.size() != 0) {
                  setTrailerViews(holder, position);
                }
                break;

        }

    }

    private void setTrailerViews(RecyclerView.ViewHolder holder, int position) {
        DetailTrailer detailTrailer = (DetailTrailer) holder;
        detailTrailer.getNoTrailer().setVisibility(View.GONE);

        final int index = position - TOTAL_DEFAULT_ITEMS;
        if (index == 0) {
            detailTrailer.getTrailerLabel().setVisibility(View.VISIBLE);
        }else{
            detailTrailer.getTrailerLabel().setVisibility(View.GONE);
        }

        detailTrailer.getTrailerTitle().setText(trailers.get(index).getTitle());
        detailTrailer.getTrailerPlay().setVisibility(View.VISIBLE);
        detailTrailer.getTrailerPlay().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent watchIntent = new Intent(Intent.ACTION_VIEW);
                Uri.Builder youtubeUrl = Uri.parse(context.getString(R.string.youtube_watch_path)).buildUpon();
                youtubeUrl.appendQueryParameter(context.getString(R.string.youtube_video_param), trailers.get(index).getUrl());
                Log.v(LOG_TAG,youtubeUrl.toString());
                watchIntent.setData(youtubeUrl.build());
                context.startActivity(watchIntent);
            }
        });
    }



    @Override
    public int getItemViewType(int position) {


        if(position >= TOTAL_DEFAULT_ITEMS){
            if(position < (TOTAL_DEFAULT_ITEMS + getTrailerKeySize())) {
                //trailers position
                return LIST_ITEM_TRAILER;
            }
        }
        //if we get here, just return position back as it will be either a title or info item
        //since it will be < TOTAL_DEFAULT_ITEMS
        return position;
    }

    @Override
    public int getItemCount() {

        return (clickedMovie == null)?ITEMS_EMPTY : (TOTAL_DEFAULT_ITEMS + getTrailerKeySize());
    }


    /**
     * A customised function to get size of trailerKey ArrayList
     * Since it is best to show a "no Trailer available"  message if there aren't any trailers,
     * the message it self will be counted as a view and especially for my logic, this no trailer view
     * is regarded to be a part of a trailer item view
     * @return number of trailers. 1 if  there aren't any trailers (for showing no trailer available message)
     */
    private int getTrailerKeySize(){
        int defaultNoTrailerItem = 1;

        return (trailers.size() == 0)? defaultNoTrailerItem : trailers.size();
    }

}
