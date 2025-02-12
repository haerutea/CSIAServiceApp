package ibcs.cs_ia_serviceapp.object_classes;

import ibcs.cs_ia_serviceapp.utils.Constants;

public class Message
{
    private String mSenderName;
    private String mSenderId;
    private String mMessage;

    public Message()
    {
        this.mMessage = Constants.DUMMY_STRING;
    }

    public Message(String senderId, String senderName, String message)
    {
        this.mSenderId = senderId;
        this.mSenderName = senderName;
        this.mMessage = message;
    }

    public void setSenderId(String senderId)
    {
        this.mSenderId = senderId;
    }

    public void setSenderName(String senderName)
    {
        this.mSenderName = senderName;
    }

    public void setMessage(String message)
    {
        this.mMessage = message;
    }

    public String getMessage()
    {
        return mMessage;
    }

    public String getSenderId()
    {
        return mSenderId;
    }

    public String getSenderName()
    {
        return mSenderName;
    }
}
