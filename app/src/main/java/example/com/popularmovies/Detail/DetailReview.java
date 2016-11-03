package example.com.popularmovies.Detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import example.com.popularmovies.R;

/**
 * Created by WSIM on 7/10/2016.
 */

public class DetailReview extends RecyclerView.ViewHolder{

    TextView reviewTextview,usernameTextview;
    ImageView userProfile;

    public DetailReview(View parent){
        super(parent);

        reviewTextview = (TextView)parent.findViewById(R.id.review_textview);
        usernameTextview = (TextView)parent.findViewById(R.id.user_name);
        userProfile = (ImageView)parent.findViewById(R.id.default_user_profile);
    }

    public TextView getReviewTextview(){
        return reviewTextview;
    }

    public TextView getUsernameTextview() {
        return usernameTextview;
    }

    public ImageView getUserProfile() {
        return userProfile;
    }
}
