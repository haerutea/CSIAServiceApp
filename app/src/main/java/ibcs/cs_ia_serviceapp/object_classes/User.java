package ibcs.cs_ia_serviceapp.object_classes;

import java.io.Serializable;
import java.util.ArrayList;

//https://stackoverflow.com/a/2736612
public class User implements Serializable
{
    private String uid;
    private String username;
    private String email;
    private String accountType;
    private boolean onlineStatus;

    public User()
    {

    }

    public User(String inUid, String inName, String inEmail, String inType, boolean online)
    {
        uid = inUid;
        username = inName;
        email = inEmail;
        accountType = inType;
        onlineStatus = online;
    }

    //TODO: IS ALL THIS NECCESARY IF THERE'S USERSHAREDPREF
    public void setUid(String inUid)
    {
        this.uid = inUid;
    }

    public void setUsername(String inUsername)
    {
        this.username = inUsername;
    }

    public void setEmail(String inEmail)
    {
        this.email = inEmail;
    }

    public void setType(String inType)
    {
        this.accountType = inType;
    }

    public void setOnline(boolean inputOnline)
    {
        this.onlineStatus = inputOnline;
    }

    public String getUid()
    {
        return uid;
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }

    public String getAccountType()
    {
        return accountType;
    }

    public boolean getOnline()
    {
        return onlineStatus;
    }
}
