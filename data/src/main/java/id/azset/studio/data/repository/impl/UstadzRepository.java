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
import id.azset.studio.data.dao.DBUstadz;
import id.azset.studio.data.dao.DBUstadzDao;
import id.azset.studio.data.dao.DaoMaster;
import id.azset.studio.data.dao.DaoSession;
import id.azset.studio.data.helper.KajiDataConfig;
import id.azset.studio.data.repository.IUstadzRepository;


/**
 * Created by id_admchaset on 10/18/2017.
 */

public class UstadzRepository implements IUstadzRepository,AsyncOperationListener {
    private Context context;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AsyncSession asyncSession;
    private List<AsyncOperation> completedOperations;
    private static final String TAG = UstadzRepository.class.getCanonicalName();
    public UstadzRepository (Context context){
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
    public synchronized DBUstadz getUstadzById(Long UstadzId) {
        DBUstadz ustadz = null;

        try {
            openReadableDb();
            DBUstadzDao UstadzDao = daoSession.getDBUstadzDao();
            ustadz =  UstadzDao.loadByRowId(UstadzId);
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

    @Nullable
    public Set<DBUstadz> InsertJsonUstadzs(String json) {
        JSONArray jsonArray = null;
        Set<DBUstadz> ustadzs = new HashSet<DBUstadz>();
        try {
            jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject ustadz1 = (JSONObject) jsonArray.get(i);
                DBUstadz ustadz = new DBUstadz();
                ustadz.setUstadzId(ustadz1.getLong("ID"));
                ustadz.setName(ustadz1.getString("Name"));
                ustadz.setStudy(ustadz1.getString("Study"));
                ustadz.setDescription(ustadz1.getString("Description"));
                ustadzs.add(ustadz);
            }
            return ustadzs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void onAsyncOperationCompleted(AsyncOperation operation) {
        completedOperations.add(operation);
    }
}
