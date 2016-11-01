package example.com.popularmovies.Detail;

import android.support.design.widget.TabLayout;
import android.support.v4.view.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import example.com.popularmovies.Movie;
import example.com.popularmovies.PopularMoviesHelper;
import example.com.popularmovies.R;


public class MovieDetailActivity extends AppCompatActivity {
    private final String LOG_TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if(savedInstanceState == null){

            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
            Bundle args = new Bundle();
            Movie selectedMovie =  getIntent().getParcelableExtra(getString(R.string.movie_extra));
            args.putParcelable(getString(R.string.movie_extra),selectedMovie);

            TextView movie_title =  ((TextView)findViewById(R.id.toolbar_title));
            movie_title.setSelected(true);
            movie_title.setText(selectedMovie.getTitle());
            Toolbar movieDetailToolbar = (Toolbar)findViewById(R.id.movie_detail_toolbar);
            movieDetailToolbar.setTitle("");
            setSupportActionBar(movieDetailToolbar);

            movieDetailFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container,movieDetailFragment,null)
                    .commit();





            /**
            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
            Bundle args = new Bundle();
            Movie selectedMovie =  getIntent().getParcelableExtra(getString(R.string.movie_extra));
            args.putParcelable(getString(R.string.movie_extra),
                   selectedMovie);

            TextView movie_title =  ((TextView)findViewById(R.id.toolbar_title));
            movie_title.setSelected(true);
            movie_title.setText(selectedMovie.getTitle());
            Toolbar movieDetailToolbar = (Toolbar)findViewById(R.id.movie_detail_toolbar);
            movieDetailToolbar.setTitle("");
            setSupportActionBar(movieDetailToolbar);


            movieDetailFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, movieDetailFragment)
                    .commit();
             **/


        }

    }


}
