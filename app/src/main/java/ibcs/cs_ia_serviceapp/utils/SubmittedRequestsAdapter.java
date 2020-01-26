package ibcs.cs_ia_serviceapp.utils;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.activities.SingleSubmittedRequestActivity;
import ibcs.cs_ia_serviceapp.object_classes.Request;

public class SubmittedRequestsAdapter extends RecyclerView.Adapter<SubmittedRequestsAdapter.RequestViewHolder>
{
    /**
     * class for each individual request view object
     */
    public static class RequestViewHolder extends RecyclerView.ViewHolder
    {
        private View wholeView;
        private TextView title;
        private TextView service;
        private TextView date;

        /**
         * assigns view for each field
         *
         * @param v individual request view
         */
        public RequestViewHolder(View v)
        {
            super(v);
            wholeView = v;
            title = v.findViewById(R.id.single_request_title);
            service = v.findViewById(R.id.single_request_service);
            date = v.findViewById(R.id.single_request_date);
        }

        /**
         * allows user to go to the request they clicked on
         *
         * @param dataObj Request object contained in the view user clicked on
         */
        private void goToRequest(final Request dataObj)
        {
            if (dataObj != null)
            {
                wholeView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Log.d("requestObj", "clicked!");

                        String accountType = UserSharedPreferences.getInstance(v.getContext()).getStringInfo(Constants.ACCOUNT_TYPE_KEY);
                        Intent intent = new Intent(v.getContext(), SingleSubmittedRequestActivity.class);
                        /*
                        if(accountType.equals(Constants.ACCOUNT_CUSTOMER))
                        {
                            intent = new Intent(v.getContext(), CustomerSingleRequestActivity.class);
                        }
                        else if(accountType.equals(Constants.ACCOUNT_PROVIDER))
                        {
                            intent = new Intent(v.getContext(), SingleSubmittedRequestActivity.class);
                        }*/
                        intent.putExtra(Constants.REQUEST_KEY, dataObj);
                        v.getContext().startActivity(intent);
                    }
                });
            }
        }
    }

    private ArrayList<Request> requestList;

    /**
     * required empty constructor
     */
    public SubmittedRequestsAdapter()
    {
    }

    /**
     * constructor, instantiates fields
     *
     * @param inRequests arraylist containing all requests
     */
    public SubmittedRequestsAdapter(ArrayList<Request> inRequests)
    {
        requestList = inRequests;
    }


    /**
     * called when new requests are added, needing to create more views of the new requests
     *
     * @param viewParent where the new view will be added
     * @param type       view type, not used
     * @return new RequestViewHolder object with the new inflated view
     */
    @NonNull
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewParent, int type)
    {
        LayoutInflater inflater = LayoutInflater.from(viewParent.getContext());
        View view = inflater.inflate(R.layout.single_request, viewParent, false);
        return new RequestViewHolder(view);
    }

    /**
     * called when needed to display new data of the new request that was added
     *
     * @param requestHolder the MessageViewHolder that needs to be updated
     * @param positionIndex position of new item in requestList
     */
    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder requestHolder, int positionIndex)
    {
        Request data = requestList.get(positionIndex);
        requestHolder.title.setText(data.getTitle());
        requestHolder.service.setText(data.getService());
        //https://stackoverflow.com/questions/5683728/convert-java-util-date-to-string
        String date = new SimpleDateFormat("dd/MM/yyyy").format(data.getCurrentDate()).toString();
        requestHolder.date.setText(date);
        requestHolder.goToRequest(data);
    }

    /**
     * gets the amount of requests there are
     *
     * @return size of requestList
     */
    public int getItemCount()
    {
        return requestList.size();
    }

    /**
     * adds new request to requestList
     *
     * @param inRequest new Request object to be added
     */
    public void addRequest(Request inRequest)
    {
        requestList.add(inRequest);
    }

    /**
     * deletes everything in requestList
     */
    public void clearContent()
    {
        requestList.clear();
    }
}
