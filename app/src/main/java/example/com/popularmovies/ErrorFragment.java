package example.com.popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ErrorFragment extends Fragment {


    public ErrorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_error, container, false);

        if(getArguments() != null) {
            String errorMsg = getArguments().getString(getContext().getString(R.string.error_str_key));
            TextView errTextview = (TextView) rootView.findViewById(R.id.error_textview);

            errTextview.setText(errorMsg);
        }

        return rootView;
    }

}
