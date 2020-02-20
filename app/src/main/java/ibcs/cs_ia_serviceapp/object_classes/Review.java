package ibcs.cs_ia_serviceapp.object_classes;

import ibcs.cs_ia_serviceapp.utils.Constants;

public class Review
{
    private String submitter;
    private double rating;
    private String comments;

    public Review()
    {
        comments = Constants.DUMMY_STRING;
    }

    public Review(String inSubmitter, double inRating, String inComments)
    {
        submitter = inSubmitter;
        rating = inRating;
        comments = inComments;
    }

    public String getSubmitter()
    {
        return submitter;
    }

    public double getRating()
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

    public void setRating(double rating)
    {
        this.rating = rating;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }
}
