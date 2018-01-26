package id.azset.studio.domain.model;

/**
 * Created by id_admchaset on 10/19/2017.
 */

public class UstadzModel {
    private final long ustadzId ;

    public UstadzModel(long categoryId)
    {
        this.ustadzId = categoryId;
    }
    private String name;
    private String study;
    private String description;


    public long getUstadzId(){return ustadzId;}

    public String getName(){ return name; }

    public String getStudy(){ return study; }

    public String getDescription(){ return description; }

    public void setName(String name) { this.name = name; }
    public void setStudy(String study){ this.study= study; }
    public void setDescription(String description){ this.description = description; }

}
