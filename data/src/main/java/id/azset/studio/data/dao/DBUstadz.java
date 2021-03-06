package id.azset.studio.data.dao;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "DBUSTADZ".
 */
@Entity
public class DBUstadz {

    @Id
    private Long UstadzId;

    @NotNull
    private String Name;
    private String Study;
    private String Description;

    @Generated
    public DBUstadz() {
    }

    public DBUstadz(Long UstadzId) {
        this.UstadzId = UstadzId;
    }

    @Generated
    public DBUstadz(Long UstadzId, String Name, String Study, String Description) {
        this.UstadzId = UstadzId;
        this.Name = Name;
        this.Study = Study;
        this.Description = Description;
    }

    public Long getUstadzId() {
        return UstadzId;
    }

    public void setUstadzId(Long UstadzId) {
        this.UstadzId = UstadzId;
    }

    @NotNull
    public String getName() {
        return Name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(@NotNull String Name) {
        this.Name = Name;
    }

    public String getStudy() {
        return Study;
    }

    public void setStudy(String Study) {
        this.Study = Study;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

}
