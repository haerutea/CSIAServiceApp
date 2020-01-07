package ibcs.cs_ia_serviceapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import ibcs.cs_ia_serviceapp.activities.ChooseAuthenticationActivity;
import ibcs.cs_ia_serviceapp.activities.ReviewActivity;
import ibcs.cs_ia_serviceapp.object_classes.Request;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

public class CompleteRequestFragment extends DialogFragment implements View.OnClickListener
{
    //UI
    private Button bCancel;
    private Button bComplete;

    //Fields
    private String rid;
    private Request requestObj;
    private String accountType;
    private int price;
    private String opposingUid;

    public CompleteRequestFragment()
    {
        // Required empty public constructor
    }

    /**
     * Create a new instance of this fragment with parameters
     *
     * @param rid request ID of request user is trying to update status of
     * @return A new instance of fragment CompleteRequestFragment
     */
    public static CompleteRequestFragment newInstance(String rid)
    {
        CompleteRequestFragment fragment = new CompleteRequestFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.RID_KEY, rid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        opposingUid = "";
        accountType = UserSharedPreferences.getInstance(getContext()).getStringInfo(Constants.ACCOUNT_TYPE_KEY);
        if (getArguments() != null)
        {
            rid = getArguments().getString(Constants.RID_KEY);
        }
        Constants.REQUEST_REFERENCE.child(rid).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                requestObj = dataSnapshot.getValue(Request.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View baseView = inflater.inflate(R.layout.complete_request_fragment, container, false);
        bCancel = baseView.findViewById(R.id.complete_cancel_button);
        bComplete = baseView.findViewById(R.id.complete_confirm_button);

        bCancel.setOnClickListener(this);
        bComplete.setOnClickListener(this);
        return baseView;
    }

    private void setRequestStatus()
    {
        DatabaseReference currentRequestRef = Constants.REQUEST_REFERENCE.child(requestObj.getRid());
        if (accountType.equals(Constants.ACCOUNT_CUSTOMER))
        {
            currentRequestRef.child(Constants.COMPLETED_CUSTOMER).setValue(true);
            opposingUid = requestObj.getProviderUid();
            if (requestObj.isCompletedProvider())
            {
                updateDatabase();
            }
        }
        else if (accountType.equals(Constants.ACCOUNT_PROVIDER))
        {
            currentRequestRef.child(Constants.COMPLETED_PROVIDER).setValue(true);
            opposingUid = requestObj.getSubmitterUid();
            if (requestObj.isCompletedCustomer())
            {
                updateDatabase();
            }
        }
        Toast.makeText(getContext(), "You have declared this request to be completed.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), ReviewActivity.class);
        intent.putExtra(Constants.OPPOSING_USER_UID_KEY, opposingUid);
        startActivity(intent);
        dismiss();
    }

    private void updateDatabase()
    {
        DatabaseReference submitterUserRef = Constants.USER_REFERENCE.child(requestObj.getSubmitterUid());
        DatabaseReference providerUserRef = Constants.USER_REFERENCE.child(requestObj.getProviderUid());

        //remove rid from requests ongoing
        submitterUserRef.child(Constants.REQUESTS_ONGOING_PATH).child(requestObj.getRid()).removeValue();
        providerUserRef.child(Constants.REQUESTS_ONGOING_PATH).child(requestObj.getRid()).removeValue();
        //add rid to completed requests
        submitterUserRef.child(Constants.REQUESTS_COMPLETED_PATH).child(requestObj.getRid()).setValue(true);
        providerUserRef.child(Constants.REQUESTS_COMPLETED_PATH).child(requestObj.getRid()).setValue(true);
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == bComplete.getId())
        {
            setRequestStatus();
        }
        else if (i == bCancel.getId())
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