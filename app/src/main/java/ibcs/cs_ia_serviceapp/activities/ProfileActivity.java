package ibcs.cs_ia_serviceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.object_classes.User;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //UI
    private TextView tUsername;
    private TextView tEmail;
    private Button bSend;
    private Button bView;
    private Button bLogout;

    //Firebase
    private DatabaseReference getUserRef;

    //Fields
    private String uid;
    private User userAccount;
    private String LOG = "profileActivity";
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        tUsername = findViewById(R.id.profile_username);
        tEmail = findViewById(R.id.profile_email);
        bSend = findViewById(R.id.submit_request);
        bSend.setOnClickListener(this);
        bView = findViewById(R.id.view_requests);
        bView.setOnClickListener(this);
        bLogout = findViewById(R.id.log_out);
        bLogout.setOnClickListener(this);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = mUser.getUid();
        UserSharedPreferences.getInstance(this).setInfo(Constants.UID_KEY, uid);
        getUserRef = Constants.USER_REFERENCE.child(uid);
        getUserRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                userAccount = dataSnapshot.getValue(User.class);
                tUsername.setText(userAccount.getUsername());
                tEmail.setText(userAccount.getEmail());
                //getUserSource.setResult(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                //getUserSource.setException(databaseError.toException());
                Log.d(LOG, "onCancelled: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(bSend.getId() == id)
        {
            Intent intent = new Intent(getApplicationContext(), SendRequestActivity.class);
            startActivity(intent);
        }
        else if(bView.getId() == id)
        {
            Intent intent = new Intent(getApplicationContext(), AllRequestsActivity.class);
            startActivity(intent);
        }
        else if(bLogout.getId() == id)
        {

        }
    }
}
