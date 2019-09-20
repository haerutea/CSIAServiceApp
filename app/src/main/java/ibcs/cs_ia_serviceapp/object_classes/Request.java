package ibcs.cs_ia_serviceapp.object_classes;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {

    private String submitterUid;
    private String title;
    private String language;
    private String service;
    private String priority;
    private String location;
    private String description;
    private String filename;
    private Date currentDate;

    public Request()
    {

    }

    public Request(String inUid, String inTitle, String inLang, String inServ, String inPri, String inLoc, String inDesc, String inNames)
    {
        submitterUid = inUid;
        title = inTitle;
        language = inLang;
        service = inServ;
        priority = inPri;
        location = inLoc;
        description = inDesc;
        filename = inNames;
        currentDate = new Date();
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

    public Date getCurrentDate()
    {
        return currentDate;
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
}
