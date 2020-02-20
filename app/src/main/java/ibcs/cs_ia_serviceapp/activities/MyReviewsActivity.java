package ibcs.cs_ia_serviceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.object_classes.Request;
import ibcs.cs_ia_serviceapp.object_classes.Review;
import ibcs.cs_ia_serviceapp.utils.AllReviewsAdapter;
import ibcs.cs_ia_serviceapp.utils.CompletedRequestsAdapter;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

public class MyReviewsActivity extends BaseActivity
{
    private String uid;
    private ArrayList<Review> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.my_reviews_activity, (ViewGroup) findViewById(R.id.contents));

        uid = UserSharedPreferences.getInstance(MyReviewsActivity.this).getStringInfo(Constants.UID_KEY);
        reviews = new ArrayList<>();
        final String uid = UserSharedPreferences.getInstance(this).getStringInfo(Constants.UID_KEY);
        final TaskCompletionSource<String> getAllReviewsTask = new TaskCompletionSource<>();
        Constants.USER_REFERENCE.child(uid).child(Constants.REVIEW_PATH).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot review : dataSnapshot.getChildren())
                {
                    reviews.add(review.getValue(Review.class));
                }
                getAllReviewsTask.setResult(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                getAllReviewsTask.setException(databaseError.toException());
            }
        });
        getAllReviewsTask.getTask().addOnCompleteListener(new OnCompleteListener<String>()
        {
            @Override
            public void onComplete(@NonNull Task<String> task)
            {
                //https://stackoverflow.com/a/153785
                DecimalFormat df = new DecimalFormat("#.#");
                df.setRoundingMode(RoundingMode.FLOOR);
                double avg = 0;
                for(Review review : reviews)
                {
                    avg += review.getRating();
                }
                TextView avgScore = findViewById(R.id.my_reviews_avg_score);
                String roundedAvg = df.format(avg / reviews.size());
                avgScore.setText(getString(R.string.avg_score_format, roundedAvg));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyReviewsActivity.this);
                AllReviewsAdapter adapter = new AllReviewsAdapter(reviews);
                RecyclerView requests = findViewById(R.id.my_reviews_recycler);
                requests.setLayoutManager(linearLayoutManager);
                requests.setAdapter(adapter);
            }
        });
    }
}