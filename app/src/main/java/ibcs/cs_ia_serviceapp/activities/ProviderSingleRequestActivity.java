package ibcs.cs_ia_serviceapp.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.SendQuotaFragment;
import ibcs.cs_ia_serviceapp.object_classes.Request;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

public class ProviderSingleRequestActivity extends BaseActivity implements View.OnClickListener
{
    //UI
    private TextView requestView;
    private TextView languageView;
    private TextView serviceView;
    private TextView priorityView;
    private TextView locationView;
    private TextView descriptionView;
    private ImageView imageView;
    private Button bSendQuota;

    //Fields
    private String uid;
    private Request inRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.provider_single_request_activity, (ViewGroup) findViewById(R.id.contents));

        inRequest = (Request) getIntent().getSerializableExtra(Constants.REQUEST_KEY);
        uid = UserSharedPreferences.getInstance(ProviderSingleRequestActivity.this).getStringInfo(Constants.UID_KEY);
        if (inRequest != null)
        {
            requestView = findViewById(R.id.request_title_view);
            languageView = findViewById(R.id.language_title);
            serviceView = findViewById(R.id.service_title);
            priorityView = findViewById(R.id.priority_title);
            locationView = findViewById(R.id.location_title);
            descriptionView = findViewById(R.id.description);
            imageView = findViewById(R.id.viewall_img_view);
            bSendQuota = findViewById(R.id.send_quota_button);
            bSendQuota.setOnClickListener(this);

            requestView.setText(getString(R.string.title_format, inRequest.getTitle()));
            languageView.setText(getString(R.string.lang_format, inRequest.getLanguage()));
            serviceView.setText(getString(R.string.service_format, inRequest.getService()));
            priorityView.setText(getString(R.string.priority_format, inRequest.getPriority()));
            locationView.setText(getString(R.string.location_format, inRequest.getLocation()));
            descriptionView.setText(inRequest.getDescription());
            //https://stackoverflow.com/questions/50816557/storing-and-displaying-image-using-glide-firebase-android
            Constants.STORAGE_REFERENCE.child(inRequest.getSubmitterUid()).child(inRequest.getFilename())
                    .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
            {
                //https://github.com/bumptech/glide#how-do-i-use-glide
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(ProviderSingleRequestActivity.this).load(uri).into(imageView);
                }
            });
        }
    }

    private void pullUpQuotaScreen()
    {
        SendQuotaFragment sendQuotaFrag = SendQuotaFragment.newInstance(inRequest);
        sendQuotaFrag.show(this.getSupportFragmentManager(), "sendQuotaFrag");
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if(i == bSendQuota.getId())
        {
            pullUpQuotaScreen();
        }
    }
}