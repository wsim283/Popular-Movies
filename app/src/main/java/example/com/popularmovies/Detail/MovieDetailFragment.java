package example.com.popularmovies.Detail;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import example.com.popularmovies.Movie;
import example.com.popularmovies.Orientation;
import example.com.popularmovies.PopularMoviesHelper;
import example.com.popularmovies.R;


public class MovieDetailFragment extends Fragment implements Orientation {

    private  final String LOG_TAG = this.getClass().getSimpleName();

    boolean twoPane;
    public MovieDetailFragment() {
        twoPane = false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);


        Movie selectedMovie = null;
        if(getArguments() != null) {
            selectedMovie = getArguments().getParcelable(getString(R.string.movie_extra));
            if(twoPane){
                TextView movieTitleview = (TextView)rootView.findViewById(R.id.movie_title);
                movieTitleview.setVisibility(View.VISIBLE);
                movieTitleview.setText(selectedMovie.getTitle());
            }
        }


        TabLayout detailsTab = PopularMoviesHelper.setLayoutTab(rootView, R.id.tab_layout);
        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), detailsTab.getTabCount(), selectedMovie, getContext());

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(detailsTab));
        detailsTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return rootView;
    }


    @Override
    public void setTwoPane(boolean twoPane) {
        this.twoPane = twoPane;
    }
}
