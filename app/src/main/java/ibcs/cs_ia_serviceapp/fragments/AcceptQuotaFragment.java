package ibcs.cs_ia_serviceapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.activities.ChatActivity;
import ibcs.cs_ia_serviceapp.object_classes.Chat;
import ibcs.cs_ia_serviceapp.object_classes.Quota;
import ibcs.cs_ia_serviceapp.object_classes.Request;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;


/**
 * fragment shown when the submitter wants to accept or reject quota
 */
public class AcceptQuotaFragment extends DialogFragment implements View.OnClickListener
{
    //UI
    private TextView pricingField;
    private Button bReject;
    private Button bAccept;

    //Fields
    private Request requestObj;
    private String requestId;
    private Quota quotaObj;
    private String userUid;

    public AcceptQuotaFragment()
    {
        // Required empty public constructor
    }

    /**
     * Create a new instance of this fragment with parameters
     *
     * @param inQuota quota object user is trying to accept or reject
     * @return A new instance of fragment SendQuotaFragment.
     */
    public static AcceptQuotaFragment newInstance(Quota inQuota, Request inRequest)
    {
        AcceptQuotaFragment fragment = new AcceptQuotaFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.QUOTA_KEY, inQuota);
        args.putSerializable(Constants.REQUEST_KEY, inRequest);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        userUid = UserSharedPreferences.getInstance(getContext()).getStringInfo(Constants.UID_KEY);
        if (getArguments() != null)
        {
            quotaObj = (Quota) getArguments().getSerializable(Constants.QUOTA_KEY);
            requestObj = (Request) getArguments().getSerializable(Constants.REQUEST_KEY);
            requestId = requestObj.getRid();
        }
        userUid = UserSharedPreferences.getInstance(getContext()).getStringInfo(Constants.UID_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View baseView = inflater.inflate(R.layout.accept_quota_fragment, container, false);
        pricingField = baseView.findViewById(R.id.textView_show_pricing);
        pricingField.setText(getString(R.string.show_price_format, quotaObj.getPrice()));
        bReject = baseView.findViewById(R.id.button_accept_quota_reject);
        bAccept = baseView.findViewById(R.id.button_accept_quota_accept);

        bReject.setOnClickListener(this);
        bAccept.setOnClickListener(this);
        return baseView;
    }

    private void acceptQuota()
    {
        DatabaseReference currentRequestRef = Constants.REQUEST_REFERENCE.child(requestId);
        //delete all quotas but the accepted one, change quota to accepted
        HashMap<String, Quota> quotas = new HashMap<>();
        quotaObj.setAccepted(true);
        quotas.put(quotaObj.getQuotaId(), quotaObj);
        currentRequestRef.child(Constants.QUOTA_PATH).setValue(quotas);

        //update request info: set accepted to true, update providerUid
        currentRequestRef.child(Constants.ACCEPTED_KEY).setValue(true);
        currentRequestRef.child(Constants.PROVIDER_UID_KEY).setValue(quotaObj.getProviderUid());

        //remove requestId from submitted and move it to ongoing
        Constants.USER_REFERENCE.child(userUid).child(Constants.REQUESTS_SUBMITTED_PATH).child(requestId).removeValue();
        Constants.USER_REFERENCE.child(userUid).child(Constants.REQUESTS_ONGOING_PATH).child(requestId).setValue(true);

        //move requestId to ongoing for provider too
        Constants.USER_REFERENCE.child(quotaObj.getProviderUid())
                .child(Constants.REQUESTS_ONGOING_PATH).child(requestId).setValue(true);

        //make chat object and start chatActivity
        Toast.makeText(getContext(), "Quota has been accepted.", Toast.LENGTH_SHORT).show();
        Chat newChatlog = new Chat(userUid, quotaObj.getProviderUid());
        currentRequestRef.child(Constants.CHAT_PATH).setValue(newChatlog);
        Intent intent = new Intent(getContext(), ChatActivity.class);
        intent.putExtra(Constants.RID_KEY, requestObj.getRid());
        startActivity(intent);
        dismiss();

    }

    private void rejectQuota()
    {
        HashMap<String, Quota> tempMap = requestObj.getQuotas();
        tempMap.remove(quotaObj.getQuotaId());
        Constants.REQUEST_REFERENCE.child(requestObj.getRid()).child(Constants.QUOTA_PATH).setValue(tempMap);

        //remove quotaId from submitted for provider
        Constants.USER_REFERENCE.child(quotaObj.getProviderUid())
                .child(Constants.QUOTAS_SUBMITTED_PATH).child(quotaObj.getQuotaId()).removeValue();
        Toast.makeText(getContext(), "Quota has been rejected.", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == bAccept.getId())
        {
            acceptQuota();
        }
        else if (i == bReject.getId())
        {
            rejectQuota();
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
