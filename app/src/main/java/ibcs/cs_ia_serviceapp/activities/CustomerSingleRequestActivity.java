package ibcs.cs_ia_serviceapp.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.object_classes.Quota;
import ibcs.cs_ia_serviceapp.object_classes.Request;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.QuotaAdapter;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

public class CustomerSingleRequestActivity extends BaseActivity
{
    //UI
    private TextView requestView;
    private TextView languageView;
    private TextView serviceView;
    private TextView priorityView;
    private TextView locationView;
    private TextView descriptionView;
    private ImageView imageView;

    //Fields
    private String uid;
    private Request inRequest;
    private LinearLayoutManager linearLayoutManager;
    private QuotaAdapter adapter;
    private RecyclerView quotasView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.customer_single_request_activity, (ViewGroup) findViewById(R.id.contents));

        inRequest = (Request) getIntent().getSerializableExtra(Constants.REQUEST_KEY);
        uid = UserSharedPreferences.getInstance(CustomerSingleRequestActivity.this).getStringInfo(Constants.UID_KEY);
        if (inRequest != null)
        {
            requestView = findViewById(R.id.request_title_view);
            languageView = findViewById(R.id.language_title);
            serviceView = findViewById(R.id.service_title);
            priorityView = findViewById(R.id.priority_title);
            locationView = findViewById(R.id.location_title);
            descriptionView = findViewById(R.id.description);
            imageView = findViewById(R.id.viewall_img_view);

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
                public void onSuccess(Uri uri)
                {
                    Glide.with(CustomerSingleRequestActivity.this).load(uri).into(imageView);
                }
            });

            linearLayoutManager = new LinearLayoutManager(CustomerSingleRequestActivity.this);
            HashMap<String, Quota> quotas = inRequest.getQuotas();
            quotas.remove(Constants.DUMMY_STRING);
            adapter = new QuotaAdapter(quotas, inRequest);
            quotasView = findViewById(R.id.all_quotas_recycler);
            quotasView.setLayoutManager(linearLayoutManager);
            quotasView.setAdapter(adapter);
        }
    }
}