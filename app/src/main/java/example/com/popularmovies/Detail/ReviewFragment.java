package example.com.popularmovies.Detail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import example.com.popularmovies.ErrorFragment;
import example.com.popularmovies.Movie;
import example.com.popularmovies.R;


public class ReviewFragment extends Fragment {

    final String LOG_TAG = getClass().getSimpleName();
    ReviewsRecAdapter reviewsRecAdapter;
    Movie clickedMovie;
    View container = null;
    Context context;

    public ReviewFragment(){
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        FetchMovieReviewsTask fetchMovieReviewsTask = new FetchMovieReviewsTask(getContext(),reviewsRecAdapter);
        String urlPath = String.format(getString(R.string.movie_reviews_path),clickedMovie.getId());
        fetchMovieReviewsTask.execute(urlPath);
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.container = container;
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);
        if(getArguments() != null) {
            clickedMovie = getArguments().getParcelable(getString(R.string.movie_extra));
            RecyclerView reviewsRec = (RecyclerView)rootView.findViewById(R.id.reviews_recycleView);
            reviewsRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
            reviewsRecAdapter = new ReviewsRecAdapter(getContext(),new ArrayList<Review>(),
                    (TextView)rootView.findViewById(R.id.no_reviews_label));
            reviewsRec.setAdapter(reviewsRecAdapter);
        }


        return rootView;


    }



}
