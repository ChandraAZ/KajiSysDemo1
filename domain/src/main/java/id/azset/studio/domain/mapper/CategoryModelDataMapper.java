package id.azset.studio.domain.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import id.azset.studio.data.dao.DBCategory;
import id.azset.studio.domain.model.CategoryModel;

/**
 * Created by id_admchaset on 10/19/2017.
 */

public class CategoryModelDataMapper {
    public CategoryModelDataMapper() {}

    public CategoryModel transform(DBCategory dbCategory) {
        if (dbCategory == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final CategoryModel categoryModel = new CategoryModel(dbCategory.getCategoryId());
        categoryModel.setName(dbCategory.getName());
        categoryModel.setIsDeleted(dbCategory.getIsDeleted());
        return categoryModel;
    }


    public Collection<CategoryModel> transform(Collection<DBCategory> categoriesCollection) {
        Collection<CategoryModel> categoryModelsCollection;

        if (categoriesCollection != null && !categoriesCollection.isEmpty()) {
            categoryModelsCollection = new ArrayList<>();
            for (DBCategory category : categoriesCollection) {
                categoryModelsCollection.add(transform(category));
            }
        } else {
            categoryModelsCollection = Collections.emptyList();
        }

        return categoryModelsCollection;
    }
}
