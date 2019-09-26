package ibcs.cs_ia_serviceapp.object_classes;

public class Quota
{
    public int price;
    public String providerUid;
    public String receiverUid;
    public Request requestObj;

    public Quota()
    {
    }

    public Quota(int inPrice, String inProviderUid, String inReceiverUid, Request inRequest)
    {
        price = inPrice;
        providerUid = inProviderUid;
        receiverUid = inReceiverUid;
        requestObj = inRequest;
    }

    public int getPrice()
    {
        return price;
    }

    public String getProviderUid()
    {
        return providerUid;
    }

    public String getReceiverUid()
    {
        return receiverUid;
    }

    public Request getRequestObj()
    {
        return requestObj;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public void setProviderUid(String providerUid)
    {
        this.providerUid = providerUid;
    }

    public void setReceiverUid(String receiverUid)
    {
        this.receiverUid = receiverUid;
    }

    public void setRequestObj(Request requestObj)
    {
        this.requestObj = requestObj;
    }
}
