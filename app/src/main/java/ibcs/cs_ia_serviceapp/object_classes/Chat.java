package ibcs.cs_ia_serviceapp.object_classes;

import java.io.Serializable;
import java.util.ArrayList;

//https://stackoverflow.com/a/2736612
public class Chat implements Serializable
{
    //uid of customer
    private String customerUid;
    //uid of provider
    private String providerUid;
    //list of all messages
    private ArrayList<Message> messages;

    public Chat()
    {
    }

    public Chat(String customer, String provider)
    {
        customerUid = customer;
        providerUid = provider;
        messages = new ArrayList<>();
        //dummy element
        messages.add(new Message());
    }

    public void addMessage(Message inputMessage)
    {
        messages.add(inputMessage);
    }

    public void setUserOne(String inputUserOne)
    {
        this.customerUid = inputUserOne;
    }

    public void setUserTwo(String inputUserTwo)
    {
        this.providerUid = inputUserTwo;
    }

    public ArrayList<Message> getMessages()
    {
        return messages;
    }

    public String getUserOne()
    {
        return customerUid;
    }

    public String getUserTwo()
    {
        return providerUid;
    }
}
