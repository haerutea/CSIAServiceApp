package ibcs.cs_ia_serviceapp.utils;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ibcs.cs_ia_serviceapp.activities.ProfileActivity;
import ibcs.cs_ia_serviceapp.activities.SubmittedRequestsListActivity;

/**
 * extends FirebaseMessageService, this class handles the message sent to this device from
 * Cloud Function.  Also handles new device tokens.
 */
public class FirebaseNotificationMessaging extends FirebaseMessagingService
{

    //this whole class is basically from the quickstart file by Firebase.
    //https://github.com/firebase/quickstart-android/blob/4017aac2bdc591dc8b9702953702f09921a4e76d/messaging/app/src/main/java/com/google/firebase/quickstart/fcm/java/MyFirebaseMessagingService.java
    private final String LOG_TAG = "messagingService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        //log who the message is from
        Log.d(LOG_TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0)
        {
            Log.d(LOG_TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null)
        {
            Log.d(LOG_TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Class destinationClass = null;

            boolean special = false;
            if (remoteMessage.getNotification().getTitle().matches("You have a new quota request!(.*)"))
            {
                destinationClass = SubmittedRequestsListActivity.class;
                special = true;
            }
            else if (remoteMessage.getNotification().getTitle().equals("Deleted request"))
            {
                destinationClass = ProfileActivity.class;
            }

            //send a notification with the message's title and body
            NotificationSender.setNotif(this, destinationClass,
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody(), false);
        }
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token)
    {
        Log.d(LOG_TAG, "new token: " + token);

        //have to do this because UID might not have been saved to sharedPreferences
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            String uid = user.getUid();
            Constants.USER_REFERENCE.child(uid)
                    .child(Constants.TOKEN_KEY).child(token).setValue(true);
            //change token value in sharedPref
            UserSharedPreferences.getInstance(this).setInfo(Constants.TOKEN_KEY, token);
        }
    }
}