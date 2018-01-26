package id.azset.studio.domain.model;


/**
 * Created by id_admchaset on 10/19/2017.
 */

public class CategoryModel {
    private long categoryId ;

    public CategoryModel(){}

    public CategoryModel(long categoryId)
    {
        this.categoryId = categoryId;
    }
    private String name;
    private Boolean isDeleted;

    public long getCategoryId(){return categoryId;}

    public String getName(){ return name; }

    public Boolean getIsDeleted(){return isDeleted;}

    public void setCategoryId(Long id) {
        this.categoryId = id;
    }

    public void setIsDeleted(Boolean isDeleted){ this.isDeleted = isDeleted;}

    public void setName(String name) { this.name = name; }

}
