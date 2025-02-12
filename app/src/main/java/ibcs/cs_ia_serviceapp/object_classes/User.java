package ibcs.cs_ia_serviceapp.object_classes;

import java.io.Serializable;
import java.util.ArrayList;

//https://stackoverflow.com/a/2736612
public class User implements Serializable
{
    //all fields in User class
    private String uid;
    private String username;
    private String email;
    private String accountType;
    private String avgRating;
    private String reviewCount;
    private ArrayList<Review> reviews;

    //required default no-argument constructor
    public User()
    {

    }

    public User(String inUid, String inName, String inEmail, String inType, boolean online)
    {
        uid = inUid;
        username = inName;
        email = inEmail;
        accountType = inType;
        avgRating = "0";
        reviewCount = "0";
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

    public void setAvgRating(String avgRating)
    {
        this.avgRating = avgRating;
    }

    public void setReviewCount(String reviewCount)
    {
        this.reviewCount = reviewCount;
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

    public String getAvgRating()
    {
        return avgRating;
    }

    public String getReviewCount()
    {
        return reviewCount;
    }
}
