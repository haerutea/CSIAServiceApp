package ibcs.cs_ia_serviceapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.DialogUtils;

//https://developers.google.com/identity/sign-in/android/sign-in
public class ChooseAuthenticationActivity extends AppCompatActivity implements View.OnClickListener
{
    private String LOG_TAG = "ChooseAuthenticationActivity";

    private FirebaseAuth mAuth;

    //UI
    private Button bSignUp;
    private Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_authentication_activity);

        mAuth = FirebaseAuth.getInstance();

        bSignUp = findViewById(R.id.bSign_up);
        bLogin = findViewById(R.id.bLogin);

        bSignUp.setOnClickListener(this);
        bLogin.setOnClickListener(this);
    }

    /**
     * whenever app is opened, if there is already a user logged in,
     * call updateUI with that user.
     */
    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /**
     * opens up ProfileActivity directly is user is present
     *
     * @param user is the user that's logged in
     */
    private void updateUI(FirebaseUser user)
    {
        //show loading popup
        ProgressDialog loadingWindow = DialogUtils.makeProgressDialog(this, "loading");
        loadingWindow.show();
        if (user != null)
        {
            //start profileActivity if user is logged in
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            intent.putExtra(Constants.UID_KEY, user.getUid());
            startActivity(intent);
        }
        //get rid of popup
        loadingWindow.dismiss();
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if (id == bLogin.getId())
        {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
        else if (id == bSignUp.getId())
        {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        }
    }
}