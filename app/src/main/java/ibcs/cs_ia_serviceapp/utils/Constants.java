package ibcs.cs_ia_serviceapp.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Constants {

    public static final String TYPE_PROVIDER = "Service Provider";
    public static final String TYPE_CUSTOMER = "Customer";

    //Database
    public static final DatabaseReference BASE_INSTANCE = FirebaseDatabase.getInstance().getReference();
    public static final String USER_PATH = "users";
    public static final String TOKEN_KEY = "token";
}
