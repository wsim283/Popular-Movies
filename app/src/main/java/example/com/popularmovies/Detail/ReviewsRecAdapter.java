package example.com.popularmovies.Detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.com.popularmovies.R;

/**
 * Created by WSIM on 31/10/2016.
 */

public class ReviewsRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Review> reviews;
    Context context;
    TextView errorTextView;

    public ReviewsRecAdapter(Context context, ArrayList<Review> reviews, TextView errorTextView){
        this.context = context;
        this.reviews = reviews;
        this.errorTextView = errorTextView;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_recycler_view_item_review,parent, false);
        return new DetailReview(rootView);
    }

    public void updateData(ArrayList<Review> newReviews){
        if(newReviews == null){
            return;
        }
        reviews.clear();
        reviews.addAll(newReviews);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DetailReview detailReview = (DetailReview)holder;
        errorTextView.setVisibility(View.GONE);
        detailReview.getUserProfile().setBackgroundResource(R.drawable.ic_userprofiledefault);
        detailReview.getUsernameTextview().setText(reviews.get(position).getUsername());
        detailReview.getReviewTextview().setText(reviews.get(position).getReview());
    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
