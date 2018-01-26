package id.azset.studio.data.dao;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "DBMANAGE_KAJI".
 */
@Entity
public class DBManageKaji {

    @Id(autoincrement = true)
    private Long id;
    private boolean IsFavorite;
    private boolean IsDownload;
    private boolean IsNew;
    private String LocalPath1;
    private String LocalPath2;
    private Integer Rating;
    private boolean IsDeleted;

    @Generated
    public DBManageKaji() {
    }

    public DBManageKaji(Long id) {
        this.id = id;
    }

    @Generated
    public DBManageKaji(Long id, boolean IsFavorite, boolean IsDownload, boolean IsNew, String LocalPath1, String LocalPath2, Integer Rating, boolean IsDeleted) {
        this.id = id;
        this.IsFavorite = IsFavorite;
        this.IsDownload = IsDownload;
        this.IsNew = IsNew;
        this.LocalPath1 = LocalPath1;
        this.LocalPath2 = LocalPath2;
        this.Rating = Rating;
        this.IsDeleted = IsDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsFavorite() {
        return IsFavorite;
    }

    public void setIsFavorite(boolean IsFavorite) {
        this.IsFavorite = IsFavorite;
    }

    public boolean getIsDownload() {
        return IsDownload;
    }

    public void setIsDownload(boolean IsDownload) {
        this.IsDownload = IsDownload;
    }

    public boolean getIsNew() {
        return IsNew;
    }

    public void setIsNew(boolean IsNew) {
        this.IsNew = IsNew;
    }

    public String getLocalPath1() {
        return LocalPath1;
    }

    public void setLocalPath1(String LocalPath1) {
        this.LocalPath1 = LocalPath1;
    }

    public String getLocalPath2() {
        return LocalPath2;
    }

    public void setLocalPath2(String LocalPath2) {
        this.LocalPath2 = LocalPath2;
    }

    public Integer getRating() {
        return Rating;
    }

    public void setRating(Integer Rating) {
        this.Rating = Rating;
    }

    public boolean getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(boolean IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

}
