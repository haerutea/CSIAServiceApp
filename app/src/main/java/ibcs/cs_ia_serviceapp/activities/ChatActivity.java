package ibcs.cs_ia_serviceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ibcs.cs_ia_serviceapp.R;
import ibcs.cs_ia_serviceapp.object_classes.Chat;
import ibcs.cs_ia_serviceapp.object_classes.Message;
import ibcs.cs_ia_serviceapp.utils.ChatAdapter;
import ibcs.cs_ia_serviceapp.utils.Constants;
import ibcs.cs_ia_serviceapp.utils.UserSharedPreferences;

/**
 * screen where user can chat with another user that was connected
 * through their chat request.
 */
public class ChatActivity extends BaseActivity implements TextView.OnEditorActionListener
{
    private final String LOG_TAG = "ChatFragmentDatabase";
    private DatabaseReference roomReference;
    private DatabaseReference messageRef;
    private ChatAdapter adapter;

    //UI references
    //https://developer.android.com/reference/android/support/v7/widget/LinearLayoutManager
    private LinearLayoutManager linearLayoutManager;
    private EditText messageInput;

    //objects referenced
    private String userUid;
    private DatabaseReference chatLogRef;
    private String rid;
    private String username;
    private Chat chatLog;

    /**
     * when activity is first created, set content from chat_activity.xml,
     * creates a new linearLayoutManager for chat messages to be displayed.
     * gets current user's uid and username from sharedPreferences,
     * sets up chatAdapter for recyclerView,
     * assigns views to fields, also changes user chat status to true.
     *
     * @param savedInstanceState data saved from onSaveInstanceState, not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.chat_activity, (ViewGroup) findViewById(R.id.contents));
        Log.d("inChatActivity", "here");

        //sets EditText
        messageInput = findViewById(R.id.chat_input);
        messageInput.setOnEditorActionListener(this);

        userUid = UserSharedPreferences.getInstance(this).getStringInfo(Constants.UID_KEY);
        username = UserSharedPreferences.getInstance(this).getStringInfo(Constants.USERNAME_KEY);

        //for recyclerView
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        //gets request rid to access chat log
        rid = getIntent().getStringExtra(Constants.RID_KEY);
        Log.d("chats", rid);
        chatLogRef = Constants.REQUEST_REFERENCE.child(rid).child(Constants.CHAT_PATH);

        final TaskCompletionSource<String> getChatLogTask = new TaskCompletionSource<>();
        chatLogRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                chatLog = dataSnapshot.getValue(Chat.class);
                getChatLogTask.setResult(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                getChatLogTask.setException(databaseError.toException());
                Log.d("chatActivity", "onCancelled: " + databaseError.getMessage());
            }
        });
        getChatLogTask.getTask().addOnCompleteListener(new OnCompleteListener<String>()
        {
            @Override
            public void onComplete(@NonNull Task<String> task)
            {
                adapter = new ChatAdapter(chatLog);
                RecyclerView chat = findViewById(R.id.chat_recycler_view);
                chat.setLayoutManager(linearLayoutManager);
                chat.setAdapter(adapter);

                setupConnection();
            }
        });
    }

    /**
     * triggered when user presses enter on keyboard after typing message
     *
     * @param textView textView containing text, not used
     * @param i        identifier for action, not used
     * @param keyEvent event triggered by enter key
     * @return true after action is processed by code
     */
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
    {
        if (!messageInput.getText().toString().isEmpty())
        {
            //creates new message object
            Message message = new Message(userUid,
                    username, messageInput.getText().toString());

            //adds to database
            //TODO: CHECK WHY TIMESTAMP DISAPPEARS???
            ArrayList<Message> tempMsgLog = chatLog.getMessages();
            tempMsgLog.add(message);
            messageRef.setValue(tempMsgLog);

            //scroll to latest message
            linearLayoutManager.scrollToPosition(adapter.getItemCount() - 1);

            //clear editText
            messageInput.setText("");
        }
        return true;
    }

    /**
     * connects to database with the chat room name, constantly listens for new message,
     * updates UI (RecyclerView) through adapter when new message is added to database.
     */
    private void setupConnection()
    {
        messageRef = chatLogRef.child(Constants.MESSAGE_PATH);
        messageRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Log.d(LOG_TAG, "Success");

                adapter.clearContent();

                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    //https://firebase.google.com/docs/reference/android/com/google/firebase/database/DataSnapshot#getValue(java.lang.Class%3CT%3E)
                    Message data = item.getValue(Message.class);
                    if (data.getMessage().equals(Constants.DUMMY_STRING))
                    {
                        continue;
                    }
                    adapter.addChat(data);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Log.e(LOG_TAG, "Failed. Error: " + databaseError.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //https://guides.codepath.com/android/using-the-app-toolbar
    //https://stackoverflow.com/questions/35648913/how-to-set-menu-to-toolbar-in-android/35649219
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //this adds items to the action bar
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //https://developer.android.com/training/appbar/actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.menu_request)
        {
            Intent intent = new Intent(getApplicationContext(), SingleOngoingRequestActivity.class);
            intent.putExtra(Constants.RID_KEY, rid);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}