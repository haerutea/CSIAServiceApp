package ibcs.cs_ia_serviceapp.object_classes;

import java.util.ArrayList;

public class Request {

    private String language;
    private String service;
    private String priority;
    private String location;
    private String description;
    private ArrayList<String> filenames;

    public Request()
    {

    }

    public Request(String inLang, String inServ, String inPri, String inLoc, String inDesc, ArrayList<String> inNames)
    {
        language = inLang;
        service = inServ;
        priority = inPri;
        location = inLoc;
        description = inDesc;
        filenames = inNames;
    }
}
