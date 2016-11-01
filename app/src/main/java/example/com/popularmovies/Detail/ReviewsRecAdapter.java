package example.com.popularmovies.Detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.com.popularmovies.R;

/**
 * Created by WSIM on 31/10/2016.
 */

public class ReviewsRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Review> reviews;
    Context context;

    public ReviewsRecAdapter(Context context, ArrayList<Review> reviews){
        this.context = context;
        this.reviews = reviews;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_recycler_view_item_review,parent, false);
        return new DetailReview(rootView);
    }

    public void updateData(ArrayList<Review> newReviews){
        if(newReviews == null || newReviews.size() == 0){
            return;
        }

        reviews.clear();
        reviews.addAll(newReviews);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DetailReview detailReview = (DetailReview)holder;

        detailReview.getReviewLabelTextview().setVisibility(View.GONE);
        detailReview.getUserProfile().setBackgroundResource(R.drawable.ic_userprofiledefault);
        detailReview.getUsernameTextview().setText(reviews.get(position).getUsername());
        detailReview.getReviewTextview().setText(reviews.get(position).getReview());
    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
