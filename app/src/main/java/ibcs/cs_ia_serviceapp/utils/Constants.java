package ibcs.cs_ia_serviceapp.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Constants {

    //sign up
    public static final String ACCOUNT_PROVIDER = "Service Provider";
    public static final String ACCOUNT_CUSTOMER = "Customer";
    public static final String[] ACCOUNT_TYPES = {ACCOUNT_CUSTOMER, ACCOUNT_PROVIDER};

    //send request GUI
    public static final String LANG_CANTO = "Cantonese";
    public static final String LANG_ENG = "English";
    public static final String LANG_MANDO = "Mandarin";
    public static final String[] LANGUAGES = {LANG_CANTO, LANG_ENG, LANG_MANDO};
    public static final String TYPE_CLEANING = "Cleaning";
    public static final String TYPE_ELECTRIC= "Electrical Services";
    public static final String TYPE_PLUMBING = "Plumbing";
    public static final String TYPE_REPAIR = "Simple Repair";
    public static final String[] SERVICES = {TYPE_CLEANING, TYPE_ELECTRIC, TYPE_PLUMBING, TYPE_REPAIR};
    public static final String PRIOR_CRIT = "Critical";
    public static final String PRIOR_HIGH = "High";
    public static final String PRIOR_MED = "Medium";
    public static final String PRIOR_LOW = "Low";
    public static final String[] PRIORITY = {PRIOR_CRIT, PRIOR_HIGH, PRIOR_MED, PRIOR_LOW};
    public static final String LOC_HKI = "Hong Kong Islands";
    public static final String LOC_KWLN = "Kowloon";
    public static final String LOC_NT = "New Territories";
    public static final String LOC_OUT = "Outlying Islands";
    public static final String[] LOCATIONS = {LOC_HKI, LOC_KWLN, LOC_NT, LOC_OUT};

    //userSharedPreferences
    public static final String SHARED_PREF_KEY = "userdetails";

    //Database and IntentExtra
    public static final String USER_PATH = "users";
    public static final String CHAT_PATH = "chats";
    public static final String MESSAGE_PATH = "messages";
    public static final String REQUEST_PATH = "requests";
    public static final String QUOTA_PATH = "quotas";
    public static final String TOKEN_KEY = "token";
    public static final String UID_KEY = "uid";
    public static final String USERNAME_KEY = "username";
    public static final String ACCOUNT_TYPE_KEY = "accountType";
    public static final String ONLINE_KEY = "online";
    public static final String REQUEST_KEY = "request";
    public static final String CHAT_ROOM_ID_KEY = "chatRoomID";
    public static final DatabaseReference BASE_REFERENCE = FirebaseDatabase.getInstance().getReference();
    public static final DatabaseReference USER_REFERENCE = BASE_REFERENCE.child(USER_PATH);
    public static final DatabaseReference REQUEST_REFERENCE = BASE_REFERENCE.child(REQUEST_PATH);
    public static final DatabaseReference CHAT_REFERENCE = BASE_REFERENCE.child(CHAT_PATH);
    public static final StorageReference STORAGE_REFERENCE = FirebaseStorage.getInstance().getReference();

    //for notifications
    public static final String CHANNEL_ID = "ServiceChatID";
    public static final String CHANNEL_NAME = "ServiceChat";
    public static final String CHANNEL_DES = "temp";
    public static final int CHAT_NOTIF_ID = 1;
}
