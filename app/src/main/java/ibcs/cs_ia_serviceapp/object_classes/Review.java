package ibcs.cs_ia_serviceapp.object_classes;

import ibcs.cs_ia_serviceapp.utils.Constants;

public class Review
{
    private String submitter;
    private int rating;
    private String comments;

    public Review()
    {
        comments = Constants.DUMMY_STRING;
    }

    public Review(String inSubmitter, int inRating, String inComments)
    {
        submitter = inSubmitter;
        rating = inRating;
        comments = inComments;
    }

    public String getSubmitter()
    {
        return submitter;
    }

    public int getRating()
    {
        return rating;
    }

    public String getComments()
    {
        return comments;
    }

    public void setSubmitter(String submitter)
    {
        this.submitter = submitter;
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
