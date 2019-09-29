package ibcs.cs_ia_serviceapp.object_classes;

import java.io.Serializable;
import java.util.HashMap;

public class Quota implements Serializable
{
    public int price;
    public HashMap<String, Boolean> providerUid;

    public Quota()
    {
        providerUid = new HashMap<>();
    }

    public Quota(int inPrice, HashMap<String, Boolean> inProviderUid)
    {
        price = inPrice;
        providerUid = inProviderUid;
        //providerUid = "";
    }

    public int getPrice()
    {
        return price;
    }

    public HashMap<String, Boolean> getProviderUid() {
        return providerUid;
    }

    /*
    public String getProviderUid()
    {
        return providerUid;
    }*/

    public void setPrice(int price)
    {
        this.price = price;
    }

    public void setProviderUid(HashMap<String, Boolean> providerUid) {
        this.providerUid = providerUid;
    }

    /*
    public void setProviderUid(String inproviderUid)
    {
        this.providerUid = inproviderUid;
    }*/
}
