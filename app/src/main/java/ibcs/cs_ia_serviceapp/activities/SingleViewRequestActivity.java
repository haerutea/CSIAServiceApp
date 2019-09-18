package ibcs.cs_ia_serviceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.object_classes.Request;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

public class SingleViewRequestActivity extends AppCompatActivity
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_view_requests_activity);

        uid = UserSharedPreferences.getInstance(SingleViewRequestActivity.this).getStringInfo(Constants.UID_KEY);
        requestView = findViewById(R.id.request_title_view);
        languageView = findViewById(R.id.language_title);
        serviceView = findViewById(R.id.service_title);
        priorityView = findViewById(R.id.priority_title);
        locationView = findViewById(R.id.location_title);
        descriptionView = findViewById(R.id.description);
        imageView = findViewById(R.id.viewall_img_view);

        Constants.REQUEST_REFERENCE.child(uid).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Request req = dataSnapshot.getValue(Request.class);
                if(req != null)
                {
                    requestView.setText(getString(R.string.title_format, req.getTitle()));
                    languageView.setText(getString(R.string.lang_format, req.getLanguage()));
                    serviceView.setText(getString(R.string.service_format, req.getService()));
                    priorityView.setText(getString(R.string.priority_format, req.getPriority()));
                    locationView.setText(getString(R.string.location_format, req.getLocation()));
                    descriptionView.setText(req.getDescription());
                    Constants.STORAGE_REFERENCE.child(uid).child(req.getFilename()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                    {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(SingleViewRequestActivity.this).load(uri).into(imageView);
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }
}