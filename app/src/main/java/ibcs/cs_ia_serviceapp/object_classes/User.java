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
    private double avgRating;
    private double reviewCount;
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
        avgRating = 0;
        reviewCount = 0;
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

    public void setAvgRating(int avgRating)
    {
        this.avgRating = avgRating;
    }

    public void setReviewCount(int reviewCount)
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

    public double getAvgRating()
    {
        return avgRating;
    }

    public double getReviewCount()
    {
        return reviewCount;
    }
}
