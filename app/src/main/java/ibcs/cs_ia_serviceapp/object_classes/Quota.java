package ibcs.cs_ia_serviceapp.object_classes;

import java.io.Serializable;
import java.util.HashMap;

public class Quota implements Serializable
{
    public int price;
    public HashMap<String, Boolean> providerUid;

    public Quota()
    {
    }

    public Quota(int inPrice, String inProviderUid)
    {
        price = inPrice;
        providerUid = new HashMap<>();
        providerUid.put(inProviderUid, true);
    }

    public int getPrice()
    {
        return price;
    }

    public String getProviderUid()
    {
        //https://stackoverflow.com/questions/10462819/get-keys-from-hashmap-in-java
        return (String) providerUid.keySet().toArray()[0];
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public void setProviderUid(String inProviderUid)
    {
        this.providerUid.clear();
        this.providerUid.put(inProviderUid, true);
    }
}
