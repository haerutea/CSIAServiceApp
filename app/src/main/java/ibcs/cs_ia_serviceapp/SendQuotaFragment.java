package ibcs.cs_ia_serviceapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import ibcs.cs_ia_serviceapp.object_classes.Quota;
import ibcs.cs_ia_serviceapp.object_classes.Request;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;


/**
 * fragment shown when the service provider wants to press on send quota
 */
public class SendQuotaFragment extends DialogFragment implements View.OnClickListener {
    //UI
    private EditText pricingField;
    private Button bCancel;
    private Button bSend;

    //Fields
    private Request requestObj;
    private String userUid;
    private int price;

    public SendQuotaFragment()
    {
        // Required empty public constructor
    }

    /**
     * Create a new instance of this fragment with parameters
     * @param reqObject request object user is trying to send quota of
     * @return A new instance of fragment SendQuotaFragment.
     */
    public static SendQuotaFragment newInstance(Request reqObject)
    {
        SendQuotaFragment fragment = new SendQuotaFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.REQUEST_KEY, reqObject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        userUid = UserSharedPreferences.getInstance(getContext()).getStringInfo(Constants.UID_KEY);
        if (getArguments() != null) {
            requestObj = (Request) getArguments().getSerializable(Constants.REQUEST_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View baseView = inflater.inflate(R.layout.send_quota_fragment, container, false);
        pricingField = baseView.findViewById(R.id.send_pricing_field);
        bCancel = baseView.findViewById(R.id.send_quota_cancel_button);
        bSend = baseView.findViewById(R.id.send_quota_confirm_button);

        bCancel.setOnClickListener(this);
        bSend.setOnClickListener(this);
        return baseView;
    }

    private void sendQuota()
    {
        if(formFilled())
        {
            Quota newQuota = new Quota(price, userUid);
            //https://stackoverflow.com/questions/54643550/arraylist-of-string-not-being-created-in-firebase
            ArrayList<Quota> tempList = requestObj.getQuotas();
            tempList.add(newQuota);
            Constants.REQUEST_REFERENCE.child(requestObj.getSubmitterUid())
                    .child(requestObj.getTitle()).child(Constants.QUOTA_PATH).setValue(tempList);
            Toast.makeText(getContext(), "Your quota has been sent.", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }

    /**
     * checks if form was filled in correctly
     * @return status of true or false
     */
    private boolean formFilled()
    {
        String input = pricingField.getText().toString();
        if(input.matches("^[0-9]*$"))
        {
            price = Integer.parseInt(input);
            return true;
        }
        else if(TextUtils.isEmpty(input))
        {
            pricingField.setError("Required.");
        }
        else
        {
            pricingField.setError("Invalid pricing");
        }
        return false;
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if(i == bSend.getId())
        {
            sendQuota();
        }
        else if( i == bCancel.getId())
        {
            dismiss();
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
