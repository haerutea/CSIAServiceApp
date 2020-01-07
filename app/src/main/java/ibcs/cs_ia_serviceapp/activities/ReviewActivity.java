package ibcs.cs_ia_serviceapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

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
        ProgressDialog loading = DialogUtils.showProgressDialog(this, getString(R.string.loading));
        int rating = ratingBar.getNumStars();
        String writtenDesc = desc.getText().toString();
        final Review tempReview = new Review(username, rating, writtenDesc);
        Constants.USER_REFERENCE.child(opposingUid).child(Constants.REVIEW_PATH).push().setValue(tempReview);
        loading.dismiss();
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
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