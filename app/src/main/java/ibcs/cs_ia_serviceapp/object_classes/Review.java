package ibcs.cs_ia_serviceapp.object_classes;

import ibcs.cs_ia_serviceapp.utils.Constants;

public class Review
{
    private int rating;
    private String comments;

    public Review()
    {
        comments = Constants.DUMMY_STRING;
    }

    public Review(int inRating, String inComments)
    {
        rating = inRating;
        comments = inComments;
    }

    public int getRating()
    {
        return rating;
    }

    public String getComments()
    {
        return comments;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }
}
