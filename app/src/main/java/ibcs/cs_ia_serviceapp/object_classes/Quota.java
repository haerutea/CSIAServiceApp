package ibcs.cs_ia_serviceapp.object_classes;

import java.io.Serializable;

public class Quota implements Serializable
{
    public int price;
    public String providerUid;

    public Quota()
    {
    }

    public Quota(int inPrice, String inProviderUid)
    {
        price = inPrice;
        providerUid = inProviderUid;
    }

    public int getPrice()
    {
        return price;
    }

    public String getProviderUid()
    {
        return providerUid;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public void setProviderUid(String inproviderUid)
    {
        this.providerUid = inproviderUid;
    }
}
