package example.com.popularmovies.Detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import example.com.popularmovies.Movie;
import example.com.popularmovies.R;

/**
 * Created by WSIM on 31/10/2016.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numTabs;
    MovieInfoFragment movieInfoFragment = null;
    ReviewFragment reviewFragment = null;
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
                if(reviewFragment == null){
                    Bundle args = new Bundle();
                    reviewFragment = new ReviewFragment();
                    args.putParcelable(context.getString(R.string.movie_extra),
                            selectedMovie);
                    reviewFragment.setArguments(args);
                }

                return reviewFragment;
            default:
                if(movieInfoFragment == null) {
                    Bundle args = new Bundle();
                    movieInfoFragment = new MovieInfoFragment();
                    args.putParcelable(context.getString(R.string.movie_extra),
                            selectedMovie);
                    movieInfoFragment.setArguments(args);
                }
                return movieInfoFragment;
        }

    }

    @Override
    public int getCount() {
        return numTabs;
    }


}
