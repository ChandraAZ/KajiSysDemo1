package id.azset.studio.data.dao;

import org.greenrobot.greendao.annotation.*;

import id.azset.studio.data.dao.DaoSession;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "DBKAJI".
 */
@Entity(active = true)
public class DBKaji {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String Title;

    @NotNull
    private String Pemateri;

    @NotNull
    private String Duration;

    @NotNull
    private String Size;
    private String Description;

    @NotNull
    private String Url1;
    private String Url2;
    private String FileName;
    private String TypeFile;
    private String Preview;
    private Integer Quality;
    private String Location;
    private java.util.Date Date;

    @NotNull
    private java.util.Date CreatedDate;
    private Long UstadzId;
    private Long CategoryId;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient DBKajiDao myDao;

    @ToOne(joinProperty = "UstadzId")
    private DBUstadz dBUstadz;

    @Generated
    private transient Long dBUstadz__resolvedKey;

    @ToOne(joinProperty = "CategoryId")
    private DBCategory dBCategory;

    @Generated
    private transient Long dBCategory__resolvedKey;

    @Generated
    public DBKaji() {
    }

    public DBKaji(Long id) {
        this.id = id;
    }

    @Generated
    public DBKaji(Long id, String Title, String Pemateri, String Duration, String Size, String Description, String Url1, String Url2, String FileName, String TypeFile, String Preview, Integer Quality, String Location, java.util.Date Date, java.util.Date CreatedDate, Long UstadzId, Long CategoryId) {
        this.id = id;
        this.Title = Title;
        this.Pemateri = Pemateri;
        this.Duration = Duration;
        this.Size = Size;
        this.Description = Description;
        this.Url1 = Url1;
        this.Url2 = Url2;
        this.FileName = FileName;
        this.TypeFile = TypeFile;
        this.Preview = Preview;
        this.Quality = Quality;
        this.Location = Location;
        this.Date = Date;
        this.CreatedDate = CreatedDate;
        this.UstadzId = UstadzId;
        this.CategoryId = CategoryId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDBKajiDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getTitle() {
        return Title;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTitle(@NotNull String Title) {
        this.Title = Title;
    }

    @NotNull
    public String getPemateri() {
        return Pemateri;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPemateri(@NotNull String Pemateri) {
        this.Pemateri = Pemateri;
    }

    @NotNull
    public String getDuration() {
        return Duration;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDuration(@NotNull String Duration) {
        this.Duration = Duration;
    }

    @NotNull
    public String getSize() {
        return Size;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setSize(@NotNull String Size) {
        this.Size = Size;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @NotNull
    public String getUrl1() {
        return Url1;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setUrl1(@NotNull String Url1) {
        this.Url1 = Url1;
    }

    public String getUrl2() {
        return Url2;
    }

    public void setUrl2(String Url2) {
        this.Url2 = Url2;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public String getTypeFile() {
        return TypeFile;
    }

    public void setTypeFile(String TypeFile) {
        this.TypeFile = TypeFile;
    }

    public String getPreview() {
        return Preview;
    }

    public void setPreview(String Preview) {
        this.Preview = Preview;
    }

    public Integer getQuality() {
        return Quality;
    }

    public void setQuality(Integer Quality) {
        this.Quality = Quality;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date Date) {
        this.Date = Date;
    }

    @NotNull
    public java.util.Date getCreatedDate() {
        return CreatedDate;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCreatedDate(@NotNull java.util.Date CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public Long getUstadzId() {
        return UstadzId;
    }

    public void setUstadzId(Long UstadzId) {
        this.UstadzId = UstadzId;
    }

    public Long getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Long CategoryId) {
        this.CategoryId = CategoryId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public DBUstadz getDBUstadz() {
        Long __key = this.UstadzId;
        if (dBUstadz__resolvedKey == null || !dBUstadz__resolvedKey.equals(__key)) {
            __throwIfDetached();
            DBUstadzDao targetDao = daoSession.getDBUstadzDao();
            DBUstadz dBUstadzNew = targetDao.load(__key);
            synchronized (this) {
                dBUstadz = dBUstadzNew;
            	dBUstadz__resolvedKey = __key;
            }
        }
        return dBUstadz;
    }

    @Generated
    public void setDBUstadz(DBUstadz dBUstadz) {
        synchronized (this) {
            this.dBUstadz = dBUstadz;
            UstadzId = dBUstadz == null ? null : dBUstadz.getUstadzId();
            dBUstadz__resolvedKey = UstadzId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public DBCategory getDBCategory() {
        Long __key = this.CategoryId;
        if (dBCategory__resolvedKey == null || !dBCategory__resolvedKey.equals(__key)) {
            __throwIfDetached();
            DBCategoryDao targetDao = daoSession.getDBCategoryDao();
            DBCategory dBCategoryNew = targetDao.load(__key);
            synchronized (this) {
                dBCategory = dBCategoryNew;
            	dBCategory__resolvedKey = __key;
            }
        }
        return dBCategory;
    }

    @Generated
    public void setDBCategory(DBCategory dBCategory) {
        synchronized (this) {
            this.dBCategory = dBCategory;
            CategoryId = dBCategory == null ? null : dBCategory.getCategoryId();
            dBCategory__resolvedKey = CategoryId;
        }
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void delete() {
        __throwIfDetached();
        myDao.delete(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void update() {
        __throwIfDetached();
        myDao.update(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void refresh() {
        __throwIfDetached();
        myDao.refresh(this);
    }

    @Generated
    private void __throwIfDetached() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
    }

}