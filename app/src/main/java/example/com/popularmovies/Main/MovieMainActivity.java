package example.com.popularmovies.Main;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import example.com.popularmovies.Detail.MovieDetailActivity;
import example.com.popularmovies.Detail.MovieDetailFragment;
import example.com.popularmovies.Detail.MovieInfoFragment;
import example.com.popularmovies.Movie;
import example.com.popularmovies.R;
import example.com.popularmovies.Settings.SettingsActivity;

public class MovieMainActivity extends AppCompatActivity implements MovieMainFragment.MovieMainListener,
        MovieInfoFragment.FavouriteChangeListener, FetchMovieListTask.DataListener{


    private  final String LOG_TAG = this.getClass().getSimpleName();
    private String DETAIL_TAG = "MOVIE_DETAIL_FRAG";
    boolean twoPane = false;
    MovieMainFragment movieMainFragment;
    FragmentManager fgm;
    Bundle savedInstanceState;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.settings){
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fgm = getSupportFragmentManager();

        this.savedInstanceState = savedInstanceState;
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
        //make sure we have internet connection, if not display error message
        if(netInfo != null && netInfo.isConnected()) {



            if(findViewById(R.id.movie_detail_container)!= null) {
                twoPane = true;
                if (savedInstanceState == null) {
                    MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                    movieDetailFragment.setTwoPane(twoPane);
                    fgm.beginTransaction().add(R.id.movie_detail_container, movieDetailFragment, DETAIL_TAG).commit();

                }
            }else{
                twoPane = false;
            }


        }


        movieMainFragment = (MovieMainFragment) fgm.findFragmentById(R.id.movie_container);
        movieMainFragment.setTwoPane(twoPane);

    }

    @Override
    public void onDataLoaded() {
        movieMainFragment.scrollToPosition();
    }

    @Override
    public void itemClicked(Movie movie, int position){
        if(!twoPane) {
            Intent movieDetailIntent = new Intent(this, MovieDetailActivity.class);
            movieDetailIntent.putExtra(getString(R.string.movie_extra), movie);
            startActivity(movieDetailIntent);

        }else{


            if(position != -1)
            movieMainFragment.savePosition(position, movie.getId());

            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
            movieDetailFragment.setTwoPane(twoPane);
            Bundle args = new Bundle();
            args.putParcelable(getString(R.string.movie_extra), movie);
            movieDetailFragment.setArguments(args);
            fgm.beginTransaction().replace(R.id.movie_detail_container, movieDetailFragment,DETAIL_TAG).commit();
        }
    }

    @Override
    public void favouritesUnMarkClicked(boolean isFavourite, String movieId) {
        if(twoPane && isFavourite){
            movieMainFragment.updateMovieListOffline(movieId);
        }

    }


}
