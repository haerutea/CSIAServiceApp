package ibcs.cs_ia_serviceapp;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.HashMap;

import ibcs.cs_ia_serviceapp.object_classes.Quota;
import ibcs.cs_ia_serviceapp.object_classes.Request;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;


/**
 * fragment shown when the service provider wants to press on send quota
 */
public class AcceptQuotaFragment extends DialogFragment implements View.OnClickListener {
    //UI
    private EditText pricingField;
    private Button bReject;
    private Button bAccept;

    //Fields
    private Request requestObj;
    private Quota quotaObj;
    private String userUid;
    private int price;

    public AcceptQuotaFragment()
    {
        // Required empty public constructor
    }

    /**
     * Create a new instance of this fragment with parameters
     * @param inQuota quota object user is trying to accept or reject
     * @return A new instance of fragment SendQuotaFragment.
     */
    public static AcceptQuotaFragment newInstance(Quota inQuota)
    {
        AcceptQuotaFragment fragment = new AcceptQuotaFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.QUOTA_KEY, inQuota);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        userUid = UserSharedPreferences.getInstance(getContext()).getStringInfo(Constants.UID_KEY);
        if (getArguments() != null) {
            quotaObj = (Quota) getArguments().getSerializable(Constants.QUOTA_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View baseView = inflater.inflate(R.layout.send_quota_fragment, container, false);
        pricingField = baseView.findViewById(R.id.send_pricing_field);
        bReject = baseView.findViewById(R.id.accept_quota_reject_button);
        bAccept = baseView.findViewById(R.id.accept_quota_accept_button);

        bReject.setOnClickListener(this);
        bAccept.setOnClickListener(this);
        return baseView;
    }

    private void acceptQuota()
    {
        Constants.REQUEST_REFERENCE.child(requestObj.getSubmitterUid())
                .child(requestObj.getTitle()).child(Constants.ACCEPTED_KEY).setValue(true);
        Toast.makeText(getContext(), "Your quota has been sent.", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    private void rejectQuota()
    {
        ArrayList<Quota> tempList = requestObj.getQuotas();
        tempList.remove(quotaObj);
        Constants.REQUEST_REFERENCE.child(requestObj.getSubmitterUid())
                .child(requestObj.getTitle()).child(Constants.QUOTA_PATH).setValue(tempList);
        Toast.makeText(getContext(), "The quota has been rejected", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if(i == bAccept.getId())
        {
            acceptQuota();
        }
        else if( i == bReject.getId())
        {
            rejectQuota();
        }
    }

    /**
     * called when fragment is first attached, has to be overridden
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
