package id.azset.studio.data.repository.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import id.azset.studio.data.dao.DBCategory;
import id.azset.studio.data.dao.DBCategoryDao;
import id.azset.studio.data.dao.DaoMaster;
import id.azset.studio.data.dao.DaoSession;
import id.azset.studio.data.helper.KajiDataConfig;
import id.azset.studio.data.helper.KajiDataHelper;
import id.azset.studio.data.repository.ICategoryRepository;


/**
 * Created by id_admchaset on 10/18/2017.
 */

public class CategoryRepository implements ICategoryRepository,AsyncOperationListener {
    private Context context;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AsyncSession asyncSession;
    private List<AsyncOperation> completedOperations;
    private static final String TAG = CategoryRepository.class.getCanonicalName();
    public CategoryRepository (Context context)
    {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(this.context, KajiDataConfig.DATABASE_NAME,null);
        completedOperations = new CopyOnWriteArrayList<AsyncOperation>();
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
    }

    @Override
    public synchronized DBCategory getCategoryById(Long categoryId) {
        DBCategory category = null;
        DBCategory categoryModel = null;

        try {
            openReadableDb();
            DBCategoryDao categoryDao = daoSession.getDBCategoryDao();
            category =  categoryDao.loadByRowId(categoryId);
            daoSession.clear();
            //categoryModel = categoryModelDataMapper.transform(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
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

    @Override
    public Boolean CategoryInsertfromJson(){
        boolean result = false;
        String categoryJson = null;

        try {
            KajiDataHelper helper = new KajiDataHelper(context);
            categoryJson = helper.GetAssstsFile(KajiDataConfig.CATEGORY_JSONFILE);
            if(categoryJson !=null) {
                Set<DBCategory> kajians =  InsertJsonCategories(categoryJson.toString());
                boolean isSaved = bulkInsertCategories(kajians);
                if (isSaved) {
                    result = true;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Nullable
    public Set<DBCategory> InsertJsonCategories(String json)  {
        JSONArray jsonArray = null;
        Set<DBCategory> categories = new HashSet<>();;
        try {
            jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject category1 = (JSONObject) jsonArray.get(i);
                DBCategory category = new DBCategory();
                category.setCategoryId(category1.getLong("ID"));
                category.setName(category1.getString("Name"));
                categories.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  return categories;
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
    public void onAsyncOperationCompleted(AsyncOperation operation) {
        completedOperations.add(operation);
    }
}
