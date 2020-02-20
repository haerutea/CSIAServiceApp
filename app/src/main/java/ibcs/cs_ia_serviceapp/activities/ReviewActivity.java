package ibcs.cs_ia_serviceapp.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.object_classes.Review;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.DialogUtils;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

public class ReviewActivity extends BaseActivity implements View.OnClickListener
{
    private String opposingUid;
    private String username;
    private RatingBar ratingBar;
    private EditText desc;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.review_activity, (ViewGroup) findViewById(R.id.contents));

        ratingBar = findViewById(R.id.review_rating);
        desc = findViewById(R.id.review_description);
        submit = findViewById(R.id.review_submit);
        submit.setOnClickListener(this);
        opposingUid = getIntent().getStringExtra(Constants.OPPOSING_USER_UID_KEY);
        username = UserSharedPreferences.getInstance(this).getStringInfo(Constants.USERNAME_KEY);
    }

    private void submitReview()
    {
        Log.d("reviewActivity", "submit review");
        //show loading dialog
        AlertDialog loading = DialogUtils.makeDialog(this, false, getString(R.string.loading));
        loading.show();
        //process input information
        final double rating = (double) ratingBar.getRating();
        String writtenDesc = desc.getText().toString();
        final Review tempReview = new Review(username, rating, writtenDesc);
        //add to database
        Constants.USER_REFERENCE.child(opposingUid).child(Constants.REVIEW_PATH).push().setValue(tempReview);
        final double[] nums = new double[2];
        final TaskCompletionSource<String> getNumbersTask = new TaskCompletionSource<>();
        Constants.USER_REFERENCE.child(opposingUid).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot data : dataSnapshot.getChildren())
                {

                    if(data.getKey().equals(Constants.AVG_RATING_KEY))
                    {
                        long tempNum = (long) data.getValue();
                        nums[0] = Long.valueOf(tempNum).doubleValue();
                    }
                    else if(data.getKey().equals(Constants.REVIEW_COUNT_KEY))
                    {
                        long tempNum = (long) data.getValue();
                        nums[1] = Long.valueOf(tempNum).doubleValue();
                    }
                }
                getNumbersTask.setResult(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        getNumbersTask.getTask().addOnCompleteListener(new OnCompleteListener<String>()
        {
            @Override
            public void onComplete(@NonNull Task<String> task)
            {
                double newAvg = calculateNewAvg(rating, nums[0], nums[1]);
                Constants.USER_REFERENCE.child(opposingUid).child(Constants.AVG_RATING_KEY).setValue(newAvg);
                double totalCount = nums[1] + 1;
                Constants.USER_REFERENCE.child(opposingUid).child(Constants.REVIEW_COUNT_KEY).setValue(totalCount);
            }
        });

        //return back to profile screen
        loading.dismiss();
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }

    private double calculateNewAvg(double newRating, double oldRating, double totalCount)
    {
        double newAvg = 0;
        double total = oldRating * totalCount;
        newAvg = (total + newRating) / (totalCount + 1);
        return newAvg;
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if (id == submit.getId())
        {
            submitReview();
        }
    }
}