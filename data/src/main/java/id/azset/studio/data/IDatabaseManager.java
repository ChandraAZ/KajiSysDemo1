package id.azset.studio.data;

import java.util.ArrayList;
import java.util.Set;

import id.azset.studio.data.dao.DBCategory;
import id.azset.studio.data.dao.DBKaji;
import id.azset.studio.data.dao.DBManageKaji;
import id.azset.studio.data.dao.DBUstadz;

/**
 * Created by id_admchaset on 10/18/2017.
 */

public interface IDatabaseManager {
    /**
     * List all the users from the DB
     *
     * @return list of users
     */

    void closeDbConnections();

    ArrayList<DBKaji> listKajians();

    ArrayList<DBManageKaji> listManageKajians();

    ArrayList<DBCategory> listCategories();

    ArrayList<DBUstadz> listUstadzs();


    DBKaji getKajianById(Long kajianId);

    DBManageKaji getManageKajianById(Long manageKajianId);

    DBCategory getCategoryById(Long categoryId);

    DBUstadz getUstadzById(Long ustadzId);


    void updateKajian(DBKaji kajian);

    void updateManageKajian(DBManageKaji manageKajian);

    void updateCategory(DBCategory categoryKajian);

    void updateUstadz(DBUstadz ustadz);


    boolean deleteKajianById(Long kajianId);

    boolean deleteManageKajianById(Long manageKajianId);

    boolean deleteCategoryById(Long categoryById);

    boolean deleteUstadzById(Long ustadzById);


    void deleteKajians();

    void deleteManageKajians();

    void deleteUstadzs();

    void deleteCategories();


    DBKaji insertKajian(DBKaji kajian);

    DBManageKaji insertManageKajian(DBManageKaji manageKajian);

    DBCategory insertCategory(DBCategory category);

    DBUstadz insertUstadz(DBUstadz ustadz);


    boolean bulkInsertKajians(Set<DBKaji> kajians);

    boolean bulkInsertManageKajians(Set<DBManageKaji> manageKajian);

    boolean bulkInsertCategories(Set<DBCategory> categories);

    boolean bulkInsertUstadzs(Set<DBUstadz> ustadzs);


    boolean KajianIsEmpty();

    boolean CategoriesIsEmpty();

    boolean UstadzsIsEmpty();
}

