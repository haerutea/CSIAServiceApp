package ibcs.cs_ia_serviceapp.object_classes;

import java.io.Serializable;
import java.util.HashMap;

public class Quota implements Serializable
{
    private int price;
    private HashMap<String, Boolean> providerUid;
    private String providerUsername;

    public Quota()
    {
        providerUid = new HashMap<>();
    }

    public Quota(int inPrice, HashMap<String, Boolean> inProviderUid, String inUsername)
    {
        price = inPrice;
        providerUid = inProviderUid;
        providerUsername = inUsername;
    }

    public int getPrice()
    {
        return price;
    }

    public HashMap<String, Boolean> getProviderUid()
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

    public void setProviderUid(HashMap<String, Boolean> providerUid)
    {
        this.providerUid = providerUid;
    }

    public void setProviderUsername(String providerUsername)
    {
        this.providerUsername = providerUsername;
    }
}