package ibcs.cs_ia_serviceapp.object_classes;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import ibcs.cs_ia_serviceapp.utils.Constants;

public class Request implements Serializable {

    private String rid;
    private String submitterUid;
    private String providerUid;
    private String title;
    private String language;
    private String service;
    private String priority;
    private String location;
    private String description;
    private String filename;
    private Date currentDate;
    private HashMap<String, Quota> quotas;
    private boolean accepted;
    private boolean completedCustomer;
    private boolean completedProvider;

    public Request()
    {

    }

    public Request(String inRid, String inUid, String inTitle, String inLang, String inServ, String inPri, String inLoc, String inDesc, String inNames)
    {
        rid = inRid;
        submitterUid = inUid;
        providerUid = "";
        title = inTitle;
        language = inLang;
        service = inServ;
        priority = inPri;
        location = inLoc;
        description = inDesc;
        filename = inNames;
        currentDate = new Date();
        quotas = new HashMap<>();
        quotas.put(Constants.DUMMY_STRING, new Quota());
        accepted = false;
        completedCustomer = false;
        completedProvider = false;
    }

    public String getRid()
    {
        return rid;
    }

    public String getSubmitterUid()
    {
        return submitterUid;
    }

    public String getProviderUid()
    {
        return providerUid;
    }

    public String getTitle()
    {
        return title;
    }

    public String getLanguage()
    {
        return language;
    }

    public String getService()
    {
        return service;
    }

    public String getPriority()
    {
        return priority;
    }

    public String getLocation()
    {
        return location;
    }

    public String getDescription()
    {
        return description;
    }

    public String getFilename()
    {
        return filename;
    }

    public boolean isAccepted()
    {
        return accepted;
    }

    public boolean isCompletedCustomer()
    {
        return completedCustomer;
    }

    public boolean isCompletedProvider()
    {
        return completedProvider;
    }

    public Date getCurrentDate()
    {
        return currentDate;
    }

    public HashMap<String, Quota> getQuotas()
    {
        return quotas;
    }

    public void setRid(String rid)
    {
        this.rid = rid;
    }

    public void setSubmitterUid(String submitterUid)
    {
        this.submitterUid = submitterUid;
    }

    public void setProviderUid(String providerUid)
    {
        this.providerUid = providerUid;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setLanguage(String inLang)
    {
        this.language = inLang;
    }

    public void setService(String inService)
    {
        this.service = inService;
    }

    public void setPriority(String inPriority)
    {
        this.priority = inPriority;
    }

    public void setLocation(String inLocation)
    {
        this.location = inLocation;
    }

    public void setDescription(String inDesc)
    {
        this.description = inDesc;
    }

    public void setFilename(String inFilename)
    {
        this.filename = inFilename;
    }

    public void setAccepted(boolean accepted)
    {
        this.accepted = accepted;
    }

    public void setCompletedCustomer(boolean completedCustomer)
    {
        this.completedCustomer = completedCustomer;
    }

    public void setCompletedProvider(boolean completedProvider)
    {
        this.completedProvider = completedProvider;
    }

    public void addQuota(Quota inQuota)
    {
        this.quotas.put(inQuota.getQuotaId(), inQuota);
    }

}
