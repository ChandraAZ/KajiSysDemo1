package id.azset.studio.data.repository.impl;

import android.app.Activity;
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

import id.azset.studio.data.dao.DBManageKaji;
import id.azset.studio.data.dao.DBManageKajiDao;
import id.azset.studio.data.dao.DaoMaster;
import id.azset.studio.data.dao.DaoSession;
import id.azset.studio.data.helper.KajiDataConfig;
import id.azset.studio.data.repository.IManageKajiRepository;

/**
 * Created by id_admchaset on 10/18/2017.
 */

public class ManageKajiRepository implements IManageKajiRepository,AsyncOperationListener {
        private Context context;
        private DaoMaster.DevOpenHelper mHelper;
        private SQLiteDatabase database;
        private DaoMaster daoMaster;
        private DaoSession daoSession;
        private AsyncSession asyncSession;
        private List<AsyncOperation> completedOperations;
        private static final String TAG = ManageKajiRepository.class.getCanonicalName();
        public ManageKajiRepository (Activity context) {
            this.context  = context;
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
    public DBManageKaji getManageKajiById(Long manageKajiId) {
        DBManageKaji manageKaji = null;

        try {
            openReadableDb();
            DBManageKajiDao manageKajiDao = daoSession.getDBManageKajiDao();
            manageKaji =  manageKajiDao.loadByRowId(manageKajiId);
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return manageKaji;
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
            DBManageKajiDao manageKajianDao = daoSession.getDBManageKajiDao();
            manageKajianDao.deleteAll();
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

    @Override
    public void onAsyncOperationCompleted(AsyncOperation operation) {
        completedOperations.add(operation);
    }
}
