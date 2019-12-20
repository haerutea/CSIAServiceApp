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
    private ArrayList<Review> reviews;

    public User()
    {

    }

    public User(String inUid, String inName, String inEmail, String inType, boolean online)
    {
        uid = inUid;
        username = inName;
        email = inEmail;
        accountType = inType;
        reviews = new ArrayList<>();
        reviews.add(new Review());
    }

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
}
