package ibcs.cs_ia_serviceapp.object_classes;

public class Quota
{
    public int price;
    public String providerUid;
    public Request requestObj;

    public Quota()
    {
    }

    public Quota(int inPrice, String inProviderUid, Request inRequest)
    {
        price = inPrice;
        providerUid = inProviderUid;
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

    public void setRequestObj(Request requestObj)
    {
        this.requestObj = requestObj;
    }
}
