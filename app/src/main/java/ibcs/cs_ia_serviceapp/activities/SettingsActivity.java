package ibcs.cs_ia_serviceapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.fragments.ChangePassswordFragment;
import ibcs.cs_ia_serviceapp.fragments.ChangeUsernameFragment;

/**
 * opens when user clicks Settings button in Profile, allows user to change password.
 * change medication button exists but is not yet implemented due to lack of access to
 * Whitney's code/logic.
 */
public class SettingsActivity extends BaseActivity implements View.OnClickListener
{

    private FirebaseUser firebaseUser;
    private Button changePassword;
    private Button changeUsername;
    private Button sendEmail;

    /**
     * when activity is first opened, set content from settings_activity.xml,
     * assign views to fields, sets change password button to enabled or disabled
     * depending if they verified email or not.  change medication is disabled until
     * I merge Whitney's app with mine.  Adds onClickListener to password button
     *
     * @param savedInstanceState data saved from onSaveInstanceState, not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.settings_activity, (ViewGroup) findViewById(R.id.contents));
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser.reload();

        changeUsername = findViewById(R.id.settings_change_username);
        sendEmail = findViewById(R.id.settings_resend_email);

        changePassword = findViewById(R.id.settings_change_password);
        if (firebaseUser.isEmailVerified())
        {
            changePassword.setEnabled(true);
            changeUsername.setEnabled(true);
            sendEmail.setEnabled(false);
        }
        else
        {
            changePassword.setEnabled(false);
            changeUsername.setEnabled(false);
            sendEmail.setEnabled(true);
        }

        sendEmail.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        changeUsername.setOnClickListener(this);
    }

    /**
     * triggers when user clicks on view with onClickListener
     *
     * @param v view user clicked on
     */
    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == changePassword.getId())
        {
            openPassword();
        }
        else if (i == changeUsername.getId())
        {
            openUsername();
        }
        else if (i == sendEmail.getId())
        {
            sendEmail();
        }
    }

    /**
     * called when user clicks on password button, shows changePasswordFragment
     */
    private void openPassword()
    {
        ChangePassswordFragment passFrag = ChangePassswordFragment.newInstance();
        passFrag.show(this.getSupportFragmentManager(), "passwordFragment");
    }

    /**
     * called when user clicks on change username button, shows changeUsernameFragment
     */
    private void openUsername()
    {
        ChangeUsernameFragment usernameFrag = ChangeUsernameFragment.newInstance();
        usernameFrag.show(this.getSupportFragmentManager(), "usernameFragment");
    }

    private void sendEmail()
    {
        firebaseUser.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Log.d("resend email", "Email sent.");
                        }
                    }
                });
    }
}