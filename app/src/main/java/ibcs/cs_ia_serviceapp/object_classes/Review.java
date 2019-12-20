package ibcs.cs_ia_serviceapp.object_classes;

import ibcs.cs_ia_serviceapp.utils.Constants;

public class Review
{
    private int score;
    private String comments;

    public Review()
    {
        comments = Constants.DUMMY_STRING;
    }

    public Review(int inScore, String inComments)
    {
        score = inScore;
        comments = inComments;
    }

    public int getScore()
    {
        return score;
    }

    public String getComments()
    {
        return comments;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }
}
