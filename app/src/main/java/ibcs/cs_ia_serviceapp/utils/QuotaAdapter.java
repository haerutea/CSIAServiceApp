package ibcs.cs_ia_serviceapp.utils;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import ibcs.cs_ia_serviceapp.AcceptQuotaFragment;
import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.object_classes.Quota;
import ibcs.cs_ia_serviceapp.object_classes.Request;

//https://github.com/DeKoServidoni/FirebaseChatAndroid/blob/master/app/src/main/java/com/dekoservidoni/firebasechat/adapters/ChatAdapter.java

/**
 * adapter for ChatActivity's recycler view, contains ViewHolder class within to set each
 * message's view.
 */
public class QuotaAdapter extends RecyclerView.Adapter<QuotaAdapter.MessageViewHolder>
{
    /**
     * class for each individual chat message view object
     */
    public static class MessageViewHolder extends RecyclerView.ViewHolder
    {
        private View wholeView;
        private TextView username;
        private TextView message;

        /**
         * assigns view for each field
         *
         * @param v individual chat message view
         */
        public MessageViewHolder(View v)
        {
            super(v);
            wholeView = v;
            username = v.findViewById(R.id.chat_username);
            message = v.findViewById(R.id.chat_message);
        }

        /**
         * allows user to go to the request they clicked on
         *
         * @param quotaObj Request object contained in the view user clicked on
         */
        private void openQuotaFrag(final Quota quotaObj, final Request requestObj)
        {
            if (quotaObj != null)
            {
                wholeView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Log.d("quota", "clicked!");

                        AcceptQuotaFragment acceptQuotaFrag = AcceptQuotaFragment.newInstance(quotaObj, requestObj);

                        //https://stackoverflow.com/a/51802596
                        acceptQuotaFrag.show(((AppCompatActivity) v.getContext()).getSupportFragmentManager(), "acceptQuotaFrag");
                    }
                });
            }
        }
    }

    private Request requestObj;
    private ArrayList<Quota> quotaList;

    /**
     * required empty constructor
     */
    public QuotaAdapter()
    {

    }

    /**
     * constructor, instantiates fields
     *
     * @param inQuotaMap hashmaps of quotas containing quotas from different providers
     */
    public QuotaAdapter(HashMap<String, Quota> inQuotaMap, Request inRequestObj)
    {
        //https://stackoverflow.com/questions/5483330/how-does-one-convert-a-hashmap-to-a-list-in-java
        quotaList = new ArrayList<Quota>(inQuotaMap.values());
        this.requestObj = inRequestObj;
    }

    /**
     * called when new messages are sent, needing to create more views of the new chat messages
     *
     * @param viewParent where the new view will be added
     * @param type       view type, not used
     * @return new MessageViewHolder object with the new inflated view
     */
    @NonNull
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewParent, int type)
    {
        LayoutInflater inflater = LayoutInflater.from(viewParent.getContext());
        View view = inflater.inflate(R.layout.chat_message_card, viewParent, false);
        return new MessageViewHolder(view);
    }

    /**
     * called when needed to display new data of the new chat message that was added
     *
     * @param messageHolder the MessageViewHolder that needs to be updated
     * @param positionIndex position of new item in quotaList
     */
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageHolder, int positionIndex)
    {
        Quota data = quotaList.get(positionIndex);
        messageHolder.username.setText(data.getProviderUsername());
        String price = "Price: " + data.getPrice();
        messageHolder.message.setText(price);
        messageHolder.openQuotaFrag(data, requestObj);
    }

    /**
     * gets the amount of messages there are
     *
     * @return size of quotaList
     */
    public int getItemCount()
    {
        return quotaList.size();
    }

    /**
     * deletes everything in quotaList
     */
    public void clearContent()
    {
        quotaList.clear();
    }
}