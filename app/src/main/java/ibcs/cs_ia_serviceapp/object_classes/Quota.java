package ibcs.cs_ia_serviceapp.object_classes;

import java.io.Serializable;

public class Quota implements Serializable
{
    private int price;
    private String providerUid;
    private String providerUsername;
    private String submitterUid;

    public Quota()
    {
        providerUid = "";
    }

    public Quota(int inPrice, String inProviderUid, String inUsername, String inSubmitterUid)
    {
        price = inPrice;
        providerUid = inProviderUid;
        providerUsername = inUsername;
        submitterUid = inSubmitterUid;
    }

    public int getPrice()
    {
        return price;
    }

    public String getProviderUid()
    {
        return providerUid;
    }

    public String getProviderUsername()
    {
        return providerUsername;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }


    public void setProviderUid(String providerUid)
    {
        this.providerUid = providerUid;
    }

    public void setSubmitterUid(String submitterUid)
    {
        this.submitterUid = submitterUid;
    }
}