package ibcs.cs_ia_serviceapp.object_classes;

import java.io.Serializable;

public class Quota implements Serializable
{
    private String quotaId;
    private int price;
    private String providerUid;
    private String providerUsername;
    private boolean accepted;

    public Quota()
    {
        providerUid = "";
    }

    public Quota(String inQid, int inPrice, String inProviderUid, String inUsername)
    {
        quotaId = inQid;
        price = inPrice;
        providerUid = inProviderUid;
        providerUsername = inUsername;
        accepted = false;
    }

    public String getQuotaId()
    {
        return quotaId;
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

    public boolean isAccepted()
    {
        return accepted;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public void setProviderUid(String providerUid)
    {
        this.providerUid = providerUid;
    }

    public void setQuotaId(String quotaId)
    {
        this.quotaId = quotaId;
    }

    public void setAccepted(boolean accepted)
    {
        this.accepted = accepted;
    }
}