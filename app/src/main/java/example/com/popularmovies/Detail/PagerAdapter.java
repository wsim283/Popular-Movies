package example.com.popularmovies.Detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import example.com.popularmovies.ErrorFragment;
import example.com.popularmovies.Movie;
import example.com.popularmovies.R;

/**
 * Created by WSIM on 31/10/2016.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numTabs;
    Fragment pageFirstFrag = null;
    Fragment pageSecondFrag = null;
    Movie selectedMovie;
    Context context;

    public PagerAdapter(FragmentManager fm, int numTabs, Movie selectedMovie, Context context){
        super(fm);
        this.context = context;
        this.numTabs = numTabs;
        this.selectedMovie = selectedMovie;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 1 :
                if(pageSecondFrag == null){
                    Bundle args = new Bundle();
                    if(selectedMovie != null) {
                        pageSecondFrag = new ReviewFragment();
                        args.putParcelable(context.getString(R.string.movie_extra),
                                selectedMovie);
                        pageSecondFrag.setArguments(args);
                    }else{
                        args.putString(context.getString(R.string.error_str_key),
                                context.getString(R.string.no_reviews_found_err));
                        pageSecondFrag = new ErrorFragment();
                        pageSecondFrag.setArguments(args);
                    }
                }

                return pageSecondFrag;
            default:

                if(pageFirstFrag == null) {
                    Bundle args = new Bundle();
                    if(selectedMovie != null) {
                        pageFirstFrag = new MovieInfoFragment();
                        args.putParcelable(context.getString(R.string.movie_extra),
                                selectedMovie);
                        pageFirstFrag.setArguments(args);
                    }else{
                        args.putString(context.getString(R.string.error_str_key),
                                context.getString(R.string.nothing_in_favourites_err));
                        pageFirstFrag = new ErrorFragment();
                        pageFirstFrag.setArguments(args);
                    }
                }
                return pageFirstFrag;
        }

    }

    @Override
    public int getCount() {
        return numTabs;
    }


}
