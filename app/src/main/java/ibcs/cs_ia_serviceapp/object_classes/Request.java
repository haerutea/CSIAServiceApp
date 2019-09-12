package ibcs.cs_ia_serviceapp.object_classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Request implements Serializable {

    private String language;
    private String service;
    private String priority;
    private String location;
    private String description;
    private String filename;

    public Request()
    {

    }

    public Request(String inLang, String inServ, String inPri, String inLoc, String inDesc, String inNames)
    {
        language = inLang;
        service = inServ;
        priority = inPri;
        location = inLoc;
        description = inDesc;
        filename = inNames;
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
