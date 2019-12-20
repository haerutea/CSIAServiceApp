package ibcs.cs_ia_serviceapp.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.object_classes.Chat;
import ibcs.cs_ia_serviceapp.object_classes.Message;

//https://github.com/DeKoServidoni/FirebaseChatAndroid/blob/master/app/src/main/java/com/dekoservidoni/firebasechat/adapters/ChatAdapter.java

/**
 * adapter for ChatActivity's recycler view, contains ViewHolder class within to set each
 * message's view.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder>
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
         * @param v individual chat message view
         */
        public MessageViewHolder(View v)
        {
            super(v);
            wholeView = v;
            username = v.findViewById(R.id.chat_username);
            message = v.findViewById(R.id.chat_message);
        }

    }
    private Chat userChatlog;
    private ArrayList<Message> messageContent;

    /**
     * required empty constructor
     */
    public ChatAdapter()
    {

    }

    /**
     * constructor, instantiates fields
     * @param inputChatlog Chat object containing messages
     */
    public ChatAdapter(Chat inputChatlog)
    {
        userChatlog = inputChatlog;
        messageContent = userChatlog.getMessages();
    }

    /**
     * called when new messages are sent, needing to create more views of the new chat messages
     * @param viewParent where the new view will be added
     * @param type view type, not used
     * @return new MessageViewHolder object with the new inflated view
     */
    @NonNull
    public MessageViewHolder onCreateViewHolder(@NonNull  ViewGroup viewParent, int type)
    {
        LayoutInflater inflater = LayoutInflater.from(viewParent.getContext());
        View view = inflater.inflate(R.layout.chat_message_card, viewParent, false);
        return new MessageViewHolder(view);
    }

    /**
     * called when needed to display new data of the new chat message that was added
     * @param messageHolder the MessageViewHolder that needs to be updated
     * @param positionIndex position of new item in messageContent
     */
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageHolder, int positionIndex) {
        Message data = messageContent.get(positionIndex);
        messageHolder.username.setText(data.getSenderName());
        messageHolder.message.setText(data.getMessage());
    }

    /**
     * gets the amount of messages there are
     * @return size of messageContent
     */
    public int getItemCount()
    {
        return messageContent.size();
    }

    /**
     * adds new message to messageContent
     * @param message new Message object to be added
     */
    public void addChat(Message message)
    {
        messageContent.add(message);
    }

    /**
     * deletes everything in messageContent
     */
    public void clearContent()
    {
        messageContent.clear();
    }
}

