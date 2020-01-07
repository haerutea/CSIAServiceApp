package ibcs.cs_ia_serviceapp.activities;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.object_classes.User;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

public class ProfileActivity extends BaseActivity implements View.OnClickListener
{

    //UI
    private TextView tUsername;
    private TextView tEmail;
    private Button bSend;
    private Button bLogout;

    //Firebase
    private DatabaseReference getUserRef;

    //Fields
    private String uid;
    private User userAccount;
    private String LOG = "profileActivity";
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.profile_activity, (ViewGroup) findViewById(R.id.contents));

        tUsername = findViewById(R.id.profile_username);
        tEmail = findViewById(R.id.profile_email);
        bSend = findViewById(R.id.submit_request);
        bSend.setOnClickListener(this);
        bLogout = findViewById(R.id.log_out);
        bLogout.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        uid = mUser.getUid();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Get new Instance ID token
                            String token = task.getResult().getToken();
                            Constants.USER_REFERENCE.child(uid)
                                    .child(Constants.TOKEN_KEY).child(token).setValue(true);
                        }
                    }
                });

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
                UserSharedPreferences.getInstance(getApplicationContext()).setInfo(Constants.USERNAME_KEY, userAccount.getUsername());
                UserSharedPreferences.getInstance(getApplicationContext()).setInfo(Constants.ACCOUNT_TYPE_KEY, userAccount.getAccountType());
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.d(LOG, "onCancelled: " + databaseError.getMessage());
            }
        });

    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if (bSend.getId() == id)
        {
            Intent intent = new Intent(getApplicationContext(), SendRequestActivity.class);
            startActivity(intent);
        }
        else if (bLogout.getId() == id)
        {
            mAuth.signOut();
            Intent intent = new Intent(getApplicationContext(), ChooseAuthenticationActivity.class);
            startActivity(intent);
        }
    }
}