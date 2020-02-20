package ibcs.cs_ia_serviceapp.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.object_classes.Review;

public class AllReviewsAdapter extends RecyclerView.Adapter<AllReviewsAdapter.ReviewViewHolder>
{
    /**
     * class for each individual review view object
     */
    public static class ReviewViewHolder extends RecyclerView.ViewHolder
    {
        private View wholeView;
        private TextView comment;
        private TextView submitter;
        private TextView score;

        /**
         * assigns view for each field
         *
         * @param v individual review view
         */
        public ReviewViewHolder(View v)
        {
            super(v);
            wholeView = v;
            comment = v.findViewById(R.id.single_review_comment);
            submitter = v.findViewById(R.id.single_review_submitter);
            score = v.findViewById(R.id.single_review_score);
        }
    }

    private ArrayList<Review> reviewsList;

    /**
     * required empty constructor
     */
    public AllReviewsAdapter()
    {
    }

    /**
     * constructor, instantiates fields
     *
     * @param inReviews arraylist containing all reviews
     */
    public AllReviewsAdapter(ArrayList<Review> inReviews)
    {
        reviewsList = inReviews;
    }


    /**
     * called when new reviews are added, needing to create more views of the new reviews
     *
     * @param viewParent where the new view will be added
     * @param type       view type, not used
     * @return new ReviewViewHolder object with the new inflated view
     */
    @NonNull
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewParent, int type)
    {
        LayoutInflater inflater = LayoutInflater.from(viewParent.getContext());
        View view = inflater.inflate(R.layout.single_review, viewParent, false);
        return new ReviewViewHolder(view);
    }

    /**
     * called when needed to display new data of the new review that was added
     *
     * @param reviewHolder the MessageViewHolder that needs to be updated
     * @param positionIndex position of new item in reviewsList
     */
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewHolder, int positionIndex)
    {
        Review data = reviewsList.get(positionIndex);
        reviewHolder.comment.setText(data.getComments());
        reviewHolder.submitter.setText(data.getSubmitter());
        String score = data.getRating() + "";
        reviewHolder.score.setText(score);
    }

    /**
     * gets the amount of reviews there are
     *
     * @return size of reviewsList
     */
    public int getItemCount()
    {
        return reviewsList.size();
    }

    /**
     * adds new review to reviewsList
     *
     * @param inReview new review object to be added
     */
    public void addRequest(Review inReview)
    {
        reviewsList.add(inReview);
    }

    /**
     * deletes everything in reviewsList
     */
    public void clearContent()
    {
        reviewsList.clear();
    }
}
