package ibcs.cs_ia_serviceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.DialogUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private FirebaseAuth mAuth;

    // UI references.
    private EditText emailField;
    private EditText passwordField;
    private Button bLogIn;

    /**
     * when activity is first opened, set content from login_activity.xml,
     * assign views to fields, add onClick listeners
     *
     * @param savedInstanceState data saved from onSaveInstanceState, not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.login_email);
        passwordField = findViewById(R.id.password);
        bLogIn = findViewById(R.id.email_sign_in_button);
        bLogIn.setOnClickListener(this);
    }

    /**
     * checks if form is correctly filled, if yes, calls FirebaseAuth's built-in signIn method,
     * pass email and password as parameters.  If successful, call goToProfile with user as parameter
     * if unsuccessful, Toast the error message.
     *
     * @param email    email from user's input
     * @param password password from user's input
     */
    private void signIn(String email, String password)
    {
        final AlertDialog progress = DialogUtils.makeDialog(this, false, getString(R.string.loading));
        progress.show();

        if (!formFilled())
        {
            progress.dismiss();
            return;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        //if user signed in successfully
                        if (task.isSuccessful())
                        {
                            //update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToProfile(user);
                        }
                        else
                        {
                            String errorMsg = "";
                            try
                            {
                                throw task.getException();
                            }
                            catch (FirebaseAuthInvalidCredentialsException e)
                            {
                                errorMsg = "Incorrect email or password.";
                            }
                            catch (Exception e)
                            {
                                errorMsg = e.getMessage();
                            }
                            progress.dismiss();
                            //display error message to user
                            Toast.makeText(LoginActivity.this, "Login failed. " + errorMsg,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * checks if the form is filled in correctly where all EditTexts are filled,
     * if not, show an error.
     *
     * @return true or false if form is filled in correctly.
     */
    private boolean formFilled()
    {
        boolean valid = true;

        String email = emailField.getText().toString();
        if (TextUtils.isEmpty(email))
        {
            emailField.setError("Required.");
            valid = false;
        }

        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(password))
        {
            passwordField.setError("Required.");
            valid = false;
        }
        return valid;
    }

    /**
     * sets user to be online on database, puts user's uid into intent and
     * starts ProfileActivity.
     *
     * @param user the signed in user
     */
    private void goToProfile(FirebaseUser user)
    {
        Constants.USER_REFERENCE.child(user.getUid())
                .child(Constants.ONLINE_KEY).setValue(true);
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        intent.putExtra(Constants.UID_KEY, user.getUid());
        startActivity(intent);
        finish();
    }

    /**
     * triggered when user clicks on view with onClickListener
     *
     * @param v the view user clicked on
     */
    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == bLogIn.getId())
        {
            signIn(emailField.getText().toString(), passwordField.getText().toString());
        }
    }
}