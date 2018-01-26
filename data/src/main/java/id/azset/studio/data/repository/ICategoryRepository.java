package id.azset.studio.data.repository;

import java.util.ArrayList;
import java.util.Set;

import id.azset.studio.data.dao.DBCategory;

/**
 * Created by id_admchaset on 10/18/2017.
 */

public interface ICategoryRepository {
    void closeDbConnections();
    Boolean CategoryInsertfromJson();
    DBCategory getCategoryById(Long categoryId);
    boolean bulkInsertCategories(Set<DBCategory> categories);
    DBCategory insertCategory(DBCategory category);
    void updateCategory(DBCategory categoryKajian);
    boolean deleteCategoryById(Long categoryById);
    void deleteCategories();
    ArrayList<DBCategory> listCategories();
    boolean CategoriesIsEmpty();
    Set<DBCategory> InsertJsonCategories(String json);
}
