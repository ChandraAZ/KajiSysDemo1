package id.azset.studio.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import id.azset.studio.data.dao.DBCategory;
import id.azset.studio.data.dao.DBCategoryDao;
import id.azset.studio.data.dao.DBKaji;
import id.azset.studio.data.dao.DBKajiDao;
import id.azset.studio.data.dao.DBManageKaji;
import id.azset.studio.data.dao.DBManageKajiDao;
import id.azset.studio.data.dao.DBUstadz;
import id.azset.studio.data.dao.DBUstadzDao;
import id.azset.studio.data.dao.DaoMaster;
import id.azset.studio.data.dao.DaoSession;

/**
 * Created by id_admchaset on 10/18/2017.
 */

public class DatabaseManager implements IDatabaseManager,AsyncOperationListener {
    /**
     * Class tag. Used for debug.
     */
    private static final String TAG = DatabaseManager.class.getCanonicalName();
    /* Instance of DatabaseManager */
    private static DatabaseManager instance;

    private Context context;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AsyncSession asyncSession;
    private List<AsyncOperation> completedOperations;
    public String URL = "";
    public DatabaseManager(final Context context){
        this.context = context  ;
        mHelper = new DaoMaster.DevOpenHelper(this.context,"Kajian1-database",null);
        completedOperations = new CopyOnWriteArrayList<AsyncOperation>();
    }
    /**
     * @param context The Android {@link android.content.Context}.
     * @return this.instance
     */
    public static DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

    @Override public void onAsyncOperationCompleted(AsyncOperation operation) {
        completedOperations.add(operation);
    }

    /**
     * Query for readable DB
     */
    public void openReadableDb() throws SQLiteException {
        database = mHelper.getReadableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    /**
     * Query for writable DB
     */
    public void openWritableDb() throws SQLiteException {
        database = mHelper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    @Override
    public void closeDbConnections() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        if (instance != null) {
            instance = null;
        }
    }

    //region Manage Kajian Databases
    @Override
    public synchronized DBKaji getKajianById(Long kajianId) {
        DBKaji kajian = null;
        try {
            openReadableDb();
            DBKajiDao userDao = daoSession.getDBKajiDao();
            kajian = userDao.load(kajianId);
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kajian;
    }

    @Override
    public synchronized DBKaji insertKajian(DBKaji kajian) {
        try {
            if (kajian != null) {
                openWritableDb();
                DBKajiDao kajianDao = daoSession.getDBKajiDao();
                kajianDao.insert(kajian);
                Log.d(TAG, "Inserted Kajian: " + kajian.getId() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kajian;
    }

    @Override
    public synchronized void updateKajian(DBKaji kajian) {
        try {
            if (kajian != null) {
                openWritableDb();
                daoSession.update(kajian);
                Log.d(TAG, "Updated Kajian : " + kajian.getTitle() + " from the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized boolean deleteKajianById(Long kajianId) {
        try {
            openWritableDb();
            DBKajiDao kajiDao = daoSession.getDBKajiDao();
            kajiDao.deleteByKey(kajianId);
            daoSession.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public synchronized void deleteKajians() {
        try {
            openWritableDb();
            DBKajiDao kajianDao = daoSession.getDBKajiDao();
            kajianDao.deleteAll();
            daoSession.clear();
            Log.d(TAG, "Delete all Kanjians from the schema.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized ArrayList<DBKaji> listKajians() {
        List<DBKaji> Kajians = null;
        try {
            openReadableDb();
            DBKajiDao kajianDao = daoSession.getDBKajiDao();
            Kajians = kajianDao.loadAll();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Kajians != null) {
            return new ArrayList<>(Kajians);
        }
        return null;
    }

    @Override
    public synchronized boolean bulkInsertKajians(Set<DBKaji> kajians) {
        boolean result = false;
        try {
            if (kajians != null && kajians.size() > 0) {
                openWritableDb();
                asyncSession.insertOrReplaceInTx(DBKaji.class, kajians);
                daoSession.clear();
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  result;
        }
        return  result;
    }

    @Override
    public synchronized boolean KajianIsEmpty()
    {
        boolean result = true;
        List<DBKaji> Kajians = null;
        try {
            openReadableDb();
            DBKajiDao kajianDao = daoSession.getDBKajiDao();
            Kajians = kajianDao.loadAll();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Kajians != null && Kajians.size() > 0) {
            return false;
        }
        return result;

    }
    //endregion

    //region Manage ManageKajians Databases
    @Override
    public synchronized DBManageKaji getManageKajianById(Long manageKajianId) {
        DBManageKaji manageKajian = null;
        try {
            openReadableDb();
            DBManageKajiDao manageKajianDao = daoSession.getDBManageKajiDao();
            manageKajian = manageKajianDao.load(manageKajianId);
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return manageKajian;
    }

    @Override
    public synchronized DBManageKaji insertManageKajian(DBManageKaji manageKajian) {
        try {
            if (manageKajian != null) {
                openWritableDb();
                DBManageKajiDao manageKajianDao = daoSession.getDBManageKajiDao();
                manageKajianDao.insert(manageKajian);
                Log.d(TAG, "Inserted ManageKajian: " + manageKajian.getId() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return manageKajian;
    }

    @Override
    public synchronized void updateManageKajian(DBManageKaji manageKajian) {
        try {
            if (manageKajian != null) {
                openWritableDb();
                daoSession.update(manageKajian);
                Log.d(TAG, "Updated ManageKajian : " + manageKajian.getId() + " from the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized boolean deleteManageKajianById(Long manageKajianId) {
        try {
            openWritableDb();
            DBManageKajiDao manageKajianDao = daoSession.getDBManageKajiDao();
            manageKajianDao.deleteByKey(manageKajianId);
            daoSession.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public synchronized void deleteManageKajians() {
        try {
            openWritableDb();
            DBKajiDao kajianDao = daoSession.getDBKajiDao();
            kajianDao.deleteAll();
            daoSession.clear();
            Log.d(TAG, "Delete all ManageKajians from the schema.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized ArrayList<DBManageKaji> listManageKajians() {
        List<DBManageKaji> ManageKajians = null;
        try {
            openReadableDb();
            DBManageKajiDao manageKajianDao = daoSession.getDBManageKajiDao();
            ManageKajians = manageKajianDao.loadAll();
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ManageKajians != null) {
            return new ArrayList<>(ManageKajians);
        }
        return null;
    }

    @Override
    public synchronized boolean bulkInsertManageKajians(Set<DBManageKaji> manageKajian) {
        boolean result = false;
        try {
            if (manageKajian != null && manageKajian.size() > 0) {
                openWritableDb();
                asyncSession.insertOrReplaceInTx(DBManageKaji.class, manageKajian);
                daoSession.clear();
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }
    //endregion

    //region Manage Categories Databases
    @Override
    public synchronized DBCategory getCategoryById(Long categoryId) {
        DBCategory category = null;
        try {
            openReadableDb();
            DBCategoryDao categoryDao = daoSession.getDBCategoryDao();
            category = categoryDao.loadByRowId(categoryId);
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public synchronized DBCategory insertCategory(DBCategory category) {
        try {
            if (category != null) {
                openWritableDb();
                DBCategoryDao categoryDao = daoSession.getDBCategoryDao();
                categoryDao.insert(category);
                Log.d(TAG, "Inserted Category: " + category.getName() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public synchronized void updateCategory(DBCategory categoryKajian) {
        try {
            if (categoryKajian != null) {
                openWritableDb();
                daoSession.update(categoryKajian);
                Log.d(TAG, "Updated Category : " + categoryKajian.getCategoryId() + " from the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized boolean deleteCategoryById(Long categoryById) {
        try {
            openWritableDb();
            DBCategoryDao categoryDao = daoSession.getDBCategoryDao();
            categoryDao.deleteByKey(categoryById);
            daoSession.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public synchronized void deleteCategories() {
        try {
            openWritableDb();
            DBCategoryDao categoryDao = daoSession.getDBCategoryDao();
            categoryDao.deleteAll();
            daoSession.clear();
            Log.d(TAG, "Delete all Categories from the schema.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized ArrayList<DBCategory> listCategories() {
        List<DBCategory> Kategories = null;
        try {
            openReadableDb();
            DBCategoryDao categoryDao = daoSession.getDBCategoryDao();
            Kategories = categoryDao.loadAll();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Kategories != null) {
            return new ArrayList<>(Kategories);
        }
        return null;
    }

    @Override
    public synchronized boolean CategoriesIsEmpty() {
        boolean result = true;
        List<DBCategory> Categories = null;
        try {
            openReadableDb();
            DBCategoryDao categoriesDao = daoSession.getDBCategoryDao();
            Categories = categoriesDao.loadAll();
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Categories != null && Categories.size()>0) {
            return false;
        }
        return result;
    }

    @Override
    public synchronized boolean bulkInsertCategories(Set<DBCategory> categories) {
        boolean result = false;
        try {
            if (categories != null && categories.size() > 0) {
                openWritableDb();
                asyncSession.insertOrReplaceInTx(DBCategory.class, categories);
                daoSession.clear();
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }
    //endregion

    //region Manage Ustadz Databases
    @Override
    public synchronized DBUstadz getUstadzById(Long ustadzId) {
        DBUstadz ustadz = null;
        try {
            openReadableDb();
            DBUstadzDao ustadzDao = daoSession.getDBUstadzDao();
            ustadz = ustadzDao.loadByRowId(ustadzId);
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ustadz;
    }

    @Override
    public synchronized DBUstadz insertUstadz(DBUstadz ustadz) {
        try {
            if (ustadz != null) {
                openWritableDb();
                DBUstadzDao uztadzDao = daoSession.getDBUstadzDao();
                uztadzDao.insert(ustadz);
                Log.d(TAG, "Inserted Uztads: " + ustadz.getName() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ustadz;
    }

    @Override
    public synchronized void updateUstadz(DBUstadz ustadz) {
        try {
            if (ustadz != null) {
                openWritableDb();
                daoSession.update(ustadz);
                Log.d(TAG, "Updated Ustadz : " + ustadz.getName() + " from the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized boolean deleteUstadzById(Long ustadzById) {
        try {
            openWritableDb();
            DBUstadzDao ustadzDao = daoSession.getDBUstadzDao();
            ustadzDao.deleteByKey(ustadzById);
            daoSession.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public synchronized void deleteUstadzs() {
        try {
            openWritableDb();
            DBUstadzDao ustadzDao = daoSession.getDBUstadzDao();
            ustadzDao.deleteAll();
            daoSession.clear();
            Log.d(TAG, "Delete all ustadzs from the schema.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized ArrayList<DBUstadz> listUstadzs() {
        List<DBUstadz> Ustadzs = null;
        try {
            openReadableDb();
            DBUstadzDao ustadzDao = daoSession.getDBUstadzDao();
            Ustadzs = ustadzDao.loadAll();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Ustadzs != null) {
            return new ArrayList<>(Ustadzs);
        }
        return null;
    }

    @Override
    public synchronized boolean UstadzsIsEmpty() {
        boolean result = true;
        List<DBUstadz> Ustadzs = null;
        try {
            openReadableDb();
            DBUstadzDao ustadzsDao = daoSession.getDBUstadzDao();
            Ustadzs = ustadzsDao.loadAll();
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Ustadzs != null && Ustadzs.size()>0) {
            return false;
        }
        return result;
    }

    @Override
    public synchronized boolean bulkInsertUstadzs(Set<DBUstadz> ustadzs) {
        boolean result = true;
        try {
            if (ustadzs != null && ustadzs.size() > 0) {
                openWritableDb();
                asyncSession.insertOrReplaceInTx(DBUstadz.class, ustadzs);
                daoSession.clear();
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }
    //endregion
}
