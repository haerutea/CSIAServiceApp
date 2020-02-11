package ibcs.cs_ia_serviceapp.activities;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.object_classes.Request;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.DialogUtils;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

public class SingleCompletedRequestActivity extends BaseActivity
{
    //UI
    private TextView requestView;
    private TextView languageView;
    private TextView serviceView;
    private TextView priorityView;
    private TextView locationView;
    private TextView descriptionView;
    private TextView opposingUserView;
    private TextView pricePaidView;
    private ImageView imageView;

    //Fields
    private String uid;
    private Request inRequest;
    private AlertDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.single_completed_request_activity, (ViewGroup) findViewById(R.id.contents));
        loading = DialogUtils.makeDialog(this, false, getString(R.string.loading));
        loading.show();
        requestView = findViewById(R.id.single_completed_request_title);
        languageView = findViewById(R.id.single_completed_language);
        serviceView = findViewById(R.id.single_completed_service);
        priorityView = findViewById(R.id.single_completed_priority);
        locationView = findViewById(R.id.single_completed_location);
        descriptionView = findViewById(R.id.single_completed_description);
        opposingUserView = findViewById(R.id.single_completed_opposing_user);
        pricePaidView = findViewById(R.id.single_completed_price_paid);
        imageView = findViewById(R.id.single_completed_img_view);

        final String accountType = UserSharedPreferences.getInstance(getApplicationContext())
                .getStringInfo(Constants.ACCOUNT_TYPE_KEY);

        inRequest = (Request) getIntent().getSerializableExtra(Constants.REQUEST_KEY);
        uid = UserSharedPreferences.getInstance(SingleCompletedRequestActivity.this).getStringInfo(Constants.UID_KEY);
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
                    Glide.with(SingleCompletedRequestActivity.this).load(uri).into(imageView);
                    requestView.setText(getString(R.string.title_format, inRequest.getTitle()));
                    languageView.setText(getString(R.string.lang_format, inRequest.getLanguage()));
                    serviceView.setText(getString(R.string.service_format, inRequest.getService()));
                    priorityView.setText(getString(R.string.priority_format, inRequest.getPriority()));
                    locationView.setText(getString(R.string.location_format, inRequest.getLocation()));
                    descriptionView.setText(inRequest.getDescription());
                    String opposingUid;
                    if (accountType.equals(Constants.ACCOUNT_CUSTOMER)) //if the user is a customer, show provider's username
                    {
                        opposingUid = inRequest.getProviderUid();
                    }
                    else
                    {
                        opposingUid = inRequest.getSubmitterUid();
                    }
                    Constants.USER_REFERENCE.child(opposingUid).child(Constants.USERNAME_KEY)
                            .addListenerForSingleValueEvent(new ValueEventListener()
                            {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    String opposingUsername = dataSnapshot.getValue(String.class);
                                    opposingUserView.setText(getString(R.string.opposing_user_format, opposingUsername));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError)
                                {

                                }
                            });
                    int pricePaid = inRequest.getPricePaid();
                    pricePaidView.setText(getString(R.string.price_paid_format, pricePaid));
                    loading.dismiss();
                }
            });
        }
    }
}