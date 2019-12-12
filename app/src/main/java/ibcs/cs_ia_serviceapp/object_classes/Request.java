package ibcs.cs_ia_serviceapp.object_classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Request implements Serializable {

    private String rid;
    private String submitterUid;
    private String title;
    private String language;
    private String service;
    private String priority;
    private String location;
    private String description;
    private String filename;
    private Date currentDate;
    private ArrayList<Quota> quotas;
    private boolean accepted;

    public Request()
    {

    }

    public Request(String inRid, String inUid, String inTitle, String inLang, String inServ, String inPri, String inLoc, String inDesc, String inNames)
    {
        rid = inRid;
        submitterUid = inUid;
        title = inTitle;
        language = inLang;
        service = inServ;
        priority = inPri;
        location = inLoc;
        description = inDesc;
        filename = inNames;
        currentDate = new Date();
        quotas = new ArrayList<>();
        quotas.add(new Quota());
        accepted = false;
    }

    public String getRid()
    {
        return rid;
    }

    public String getSubmitterUid()
    {
        return submitterUid;
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

    public Date getCurrentDate()
    {
        return currentDate;
    }

    public ArrayList<Quota> getQuotas()
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

    public void addQuota(Quota inQuota)
    {
        this.quotas.add(inQuota);
    }

}
