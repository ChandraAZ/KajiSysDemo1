package id.azset.studio.domain.model;

/**
 * Created by id_admchaset on 10/19/2017.
 */

public class ManageKajiModel {
    private Long manageKajiId;
    public ManageKajiModel(){}
    public ManageKajiModel(long manageKajiId){ this.manageKajiId = manageKajiId;}

    private boolean isFavorite;
    private boolean isDonwload;
    private boolean isNew;
    private String localPath1;
    private String localPath2;
    private Integer rating;
    private boolean isDeleted;
    private long kajianId;

    public long getManageKajiId() {return manageKajiId;};
    public boolean getIsFavorite(){return isFavorite;}
    public boolean getIsDownload(){return isDonwload;}
    public boolean getIsNew(){return isNew;}
    public String getLocalPath1(){return localPath1;}
    public String getLocalPath2(){return localPath2;}
    public Integer getRating(){return rating;}
    public boolean getIsDeleted(){return isDeleted;}
    public long getKajianID(){return kajianId;}

    public void setManageKajiId (long manageKajiId) { this.manageKajiId = manageKajiId;}
    public void setIsFavorite(Boolean isFavorite){ this.isFavorite = isFavorite;}
    public void setIsDownload(Boolean isDonwload){ this.isDonwload = isDonwload;}
    public void setIsNew(Boolean isNew){ this.isNew = isNew;}
    public void setLocalPath1(String localPath1){ this.localPath1 = localPath1;}
    public void setLocalPath2(String localPath2){ this.localPath2 = localPath2;}
    public void setRating(Integer rating){ this.rating = rating;}
    public void setIsDeleted(Boolean isDeleted){ this.isDeleted = isDeleted;}
    public void setKajianID(long kajianId){ this.kajianId = kajianId;}


}
