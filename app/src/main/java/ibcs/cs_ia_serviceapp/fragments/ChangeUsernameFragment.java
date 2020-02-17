package ibcs.cs_ia_serviceapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.DialogUtils;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

/**
 * shown when user presses on change username button in settings, allows user to set their new username
 */
public class ChangeUsernameFragment extends DialogFragment implements View.OnClickListener
{
    private EditText newUsername;
    private Button confirm;
    private Button cancel;

    private String username;
    private String uid;

    /**
     * required empty constructor
     */
    public ChangeUsernameFragment()
    {
        // Required empty public constructor
    }

    /**
     * creates new object/instance of this class
     *
     * @return new object of this class
     */
    public static ChangeUsernameFragment newInstance()
    {
        return new ChangeUsernameFragment();
    }

    /**
     * called when fragment is first created
     *
     * @param savedInstanceState data saved from onSaveInstanceState, not used
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        uid = UserSharedPreferences.getInstance(this.getContext()).getStringInfo(Constants.UID_KEY);
    }

    /**
     * called to instantiate views onto layout and assign views to fields
     *
     * @param inflater           used to inflate views on fragment
     * @param container          the parent of where this frag will be shown
     * @param savedInstanceState not used.
     * @return view for this fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View baseView = inflater.inflate(R.layout.change_username_fragment, container, false);
        newUsername = baseView.findViewById(R.id.change_username_new_username);
        confirm = baseView.findViewById(R.id.change_username_confirm);
        cancel = baseView.findViewById(R.id.change_username_cancel);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        return baseView;
    }

    /**
     * checks if form is filled in correctly
     *
     * @return true or false depending on status
     */
    private boolean formFilled()
    {
        username = newUsername.getText().toString();
        //if username is less than 6 characters long
        if (TextUtils.isEmpty(username))
        {
            newUsername.setError("Required.");
        }
        //if username contains anything other than that
        else if (!username.matches("([A-Za-z0-9])+"))
        {
            newUsername.setError("Only alphabet and digits please.");
        }
        else
        {
            return true;
        }
        return false;
    }

    /**
     * called when user presses confirm, calls formFilled to check,
     * calls updateProfile method from FirebaseUser,
     * if successful, dismiss progress dialog and log the error message.
     */
    private void confirmChange()
    {
        AlertDialog loading = DialogUtils
                .makeDialog(getActivity(), false, "Attempting to update username...");
        //otherwise
        if (formFilled())
        {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            loading.show();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username).build();
            firebaseUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>()
            {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {
                        Log.d("New display name: ", username);
                        Toast.makeText(getActivity(), "Update username success", Toast.LENGTH_LONG).show();
                        UserSharedPreferences.getInstance(getContext()).setInfo(Constants.USERNAME_KEY, username);
                        Constants.USER_REFERENCE.child(uid).child(Constants.USERNAME_KEY).setValue(username);
                        dismiss();
                    }
                    else
                    {
                        try
                        {
                            throw task.getException();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
            loading.dismiss();
        }
    }

    /**
     * triggered when user clicks on a button with onClickListener
     *
     * @param v the view the user clicked on
     */
    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if (id == confirm.getId())
        {
            //https://stackoverflow.com/questions/37209157/hide-keyboard-when-button-click-fragment
            //hide keyboard
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            confirmChange();
        }
        else if (id == cancel.getId())
        {
            dismiss();
        }
    }

    /**
     * called when fragment is first attached, has to be overridden
     *
     * @param context the context it'll be attached to
     */
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    /**
     * called when fragment isn't attached to activity anymore
     */
    @Override
    public void onDetach()
    {
        super.onDetach();
    }
}
