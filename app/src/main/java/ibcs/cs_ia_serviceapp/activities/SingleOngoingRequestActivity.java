package ibcs.cs_ia_serviceapp.activities;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import ibcs.cs_ia_serviceapp.fragments.CompleteRequestFragment;
import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.object_classes.Request;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.DialogUtils;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

public class SingleOngoingRequestActivity extends BaseActivity implements View.OnClickListener
{
    //UI
    private TextView requestView;
    private TextView languageView;
    private TextView serviceView;
    private TextView priorityView;
    private TextView locationView;
    private TextView descriptionTitleView;
    private TextView descriptionView;
    private ImageView imageView;
    private Button bComplete;

    //Fields
    private String uid;
    private String rid;
    private Request inRequest;
    private AlertDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.single_ongoing_request_activity, (ViewGroup) findViewById(R.id.contents));
        loading = DialogUtils.makeDialog(this, false, getString(R.string.loading));
        loading.show();
        requestView = findViewById(R.id.request_title_view);
        languageView = findViewById(R.id.language_title);
        serviceView = findViewById(R.id.service_title);
        priorityView = findViewById(R.id.priority_title);
        locationView = findViewById(R.id.location_title);
        descriptionTitleView = findViewById(R.id.description_title);
        descriptionView = findViewById(R.id.description);
        imageView = findViewById(R.id.viewall_img_view);
        bComplete = findViewById(R.id.complete_request_button);
        bComplete.setOnClickListener(this);

        uid = UserSharedPreferences.getInstance(SingleOngoingRequestActivity.this).getStringInfo(Constants.UID_KEY);

        //get request from DB
        rid = getIntent().getStringExtra(Constants.RID_KEY);
        final TaskCompletionSource<String> getRequestTask = new TaskCompletionSource<>();
        Constants.REQUEST_REFERENCE.child(rid).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                inRequest = dataSnapshot.getValue(Request.class);
                getRequestTask.setResult(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                getRequestTask.setException(databaseError.toException());
            }
        });

        //when done getting the request from DB, populate views
        getRequestTask.getTask().addOnCompleteListener(new OnCompleteListener<String>()
        {
            @Override
            public void onComplete(@NonNull Task<String> task)
            {
                if (inRequest != null)
                {
                    //https://stackoverflow.com/questions/50816557/storing-and-displaying-image-using-glide-firebase-android
                    Constants.STORAGE_REFERENCE.child(inRequest.getSubmitterUid()).child(inRequest.getFilename())
                            .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                    {
                        //https://github.com/bumptech/glide#how-do-i-use-glide
                        @Override
                        public void onSuccess(Uri uri)
                        {
                            Glide.with(SingleOngoingRequestActivity.this).load(uri).into(imageView);
                            requestView.setText(getString(R.string.title_format, inRequest.getTitle()));
                            languageView.setText(getString(R.string.lang_format, inRequest.getLanguage()));
                            serviceView.setText(getString(R.string.service_format, inRequest.getService()));
                            priorityView.setText(getString(R.string.priority_format, inRequest.getPriority()));
                            locationView.setText(getString(R.string.location_format, inRequest.getLocation()));
                            descriptionTitleView.setText(getString(R.string.description));
                            descriptionView.setText(inRequest.getDescription());
                            bComplete.setVisibility(View.VISIBLE);
                            loading.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void pullUpCompleteFrag()
    {
        CompleteRequestFragment completeRequestFrag = CompleteRequestFragment.newInstance(inRequest.getRid());
        completeRequestFrag.show(this.getSupportFragmentManager(), "completeRequestFrag");
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == bComplete.getId())
        {
            pullUpCompleteFrag();
        }
    }
}