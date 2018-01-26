package id.azset.studio.domain.model;

import android.content.Context;

import java.util.Date;

import id.azset.studio.data.dao.DBUstadz;
import id.azset.studio.data.repository.impl.UstadzRepository;
import id.azset.studio.domain.business.impl.MainBusiness;

/**
 * Created by id_admchaset on 10/19/2017.
 */

public class KajiModel {
    private long kajianId;
    private Context context;

    public KajiModel(Context context){this.context = context;}

    public KajiModel(long kajianId){this.kajianId = kajianId;}

    private long id;
    private String title;
    private String pemateri;
    private String duration;
    private String size;
    private String description;
    private String url1;
    private String url2;
    private String fileName;
    private String typeFile;
    private String preview;
    private Integer quality;
    private String location;
    private Date date;
    private Date createdDate;
    private long categoryID;
    private long ustadzID;
    private UstadzModel ustadz;
    private CategoryModel category;

    public Long getID() {return  kajianId;}
    public String getTitle(){return title;}
    public String getpemateri(){return pemateri;}
    public String getDuration(){return duration;}
    public String getSize(){return size;}
    public String getDescription(){return description;}
    public String getUrl1(){return url1;}
    public String getUrl2(){return url2;}
    public String getFileName(){return fileName;}
    public String getTypeFile(){return typeFile;}
    public String getPreview(){return preview;}
    public Integer getQuality(){return quality;}
    public String getLocation(){return location;}
    public Date getDate(){return date;}
    public Date getCreatedDate(){return createdDate;}
    public Long getCategoryID(){return categoryID;}
    public Long getUstadzID(){return ustadzID;}
    public UstadzModel getUstadz() {
        return ustadz;
    }
    public CategoryModel getCategory(){
        return category;
    }

    public void setId(Long kajianId) {this.kajianId = kajianId;}
    public void setTitle(String title){this.title = title;}
    public void setpemateri(String pemateri){this.pemateri = pemateri;}
    public void setDuration(String duration){this.duration = duration;}
    public void setSize(String size){this.size = size;}
    public void setDescription(String description){this.description = description;}
    public void setUrl1(String url1){this.url1 = url1;}
    public void setUrl2(String url2){this.url2 = url2;}
    public void setFileName(String fileName){this.fileName = fileName;}
    public void setTypeFile(String typeFile){this.typeFile = typeFile;}
    public void setPreview(String preview){this.preview = preview;}
    public void setQuality(Integer quality){this.quality = quality;}
    public void setLocation(String location){this.location = location;}
    public void setDate(Date date){this.date = date;}
    public void setCreatedDate(Date createdDate){this.createdDate = createdDate;}
    public void setCategoryID(Long categoryID,Context _contaxt){
        this.categoryID = categoryID;
        Long __key = categoryID;
        MainBusiness targetDao = new MainBusiness(_contaxt);
        CategoryModel dbCategory = targetDao.GetCategoryById(__key);
        this.category = dbCategory;
    }
    public void setUstadzID(Long ustadzID,Context _contaxt){
        this.ustadzID = ustadzID;
        Long __key = ustadzID;
        MainBusiness targetDao = new MainBusiness(_contaxt);
        UstadzModel dBUstadzNew = targetDao.GetUstadzById(__key);
        this.ustadz = dBUstadzNew;
    }
}

