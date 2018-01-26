package id.azset.studio.data.repository.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.internal.LongHashMap;
import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;


import id.azset.studio.data.dao.DBCategory;
import id.azset.studio.data.dao.DBCategoryDao;
import id.azset.studio.data.dao.DBKaji;
import id.azset.studio.data.dao.DBKajiDao;
import id.azset.studio.data.dao.DBUstadz;
import id.azset.studio.data.dao.DBUstadzDao;
import id.azset.studio.data.dao.DaoMaster;
import id.azset.studio.data.dao.DaoSession;
import id.azset.studio.data.helper.KajiDataConfig;
import id.azset.studio.data.helper.KajiDataHelper;
import id.azset.studio.data.repository.IKajiRepository;


/**
 * Created by id_admchaset on 10/18/2017.
 */

public class KajiRepository implements IKajiRepository,AsyncOperationListener {
    private Context context;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AsyncSession asyncSession;
    private List<AsyncOperation> completedOperations;
    private static final String TAG = KajiRepository.class.getCanonicalName();
    public KajiRepository (Context context)
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
    public synchronized DBKaji getKajianById(Long kajianId) {
        DBKaji kajian = null;

        try {
            openReadableDb();
            DBKajiDao kajianDao = daoSession.getDBKajiDao();
            kajian =  kajianDao.loadByRowId(kajianId);
            daoSession.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return kajian;
    }

    @Override
    public synchronized List<DBKaji> SelectAll()
    {
        List<DBKaji> Kajians = null;
        try {
            openReadableDb();
            DBKajiDao kajianDao = daoSession.getDBKajiDao();
            Kajians = kajianDao.loadAll();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Kajians;
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

    @Override
    public synchronized boolean bulkInsertKajians(Set<DBKaji> kajians) {
        boolean result = false;
        try {
            if (kajians != null && kajians.size() > 0) {
                openWritableDb();
                //asyncSession.insertOrReplaceInTx(DBKaji.class, kajians);
                asyncSession.insertInTx(DBKaji.class, kajians);
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

    @Nullable
    public Set<DBKaji> InsertJsonKajian(String json) {
        UstadzRepository repoUstad = new UstadzRepository(context);
        KajiDataHelper kajiHelper = new KajiDataHelper(context);
        JSONArray jsonArray = null;
        Set<DBKaji> kajians = new HashSet<DBKaji>();
        try {
            jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject kajian1 = (JSONObject) jsonArray.get(i);
                DBUstadz ustadz = repoUstad.getUstadzById(kajian1.getLong("UstadzID"));
                DBKaji kajian = new DBKaji();
                kajian.setCategoryId(kajian1.getLong("CategoryID"));
                kajian.setUstadzId(kajian1.getLong("UstadzID"));
                kajian.setTitle(kajian1.getString("Title"));
                kajian.setPemateri(kajian1.getString("Pemateri"));
                kajian.setDuration(kajian1.getString("Duration"));
                kajian.setSize(kajian1.getString("Size"));
                kajian.setDescription(kajian1.getString("Description"));
                kajian.setUrl1(kajian1.getString("Url1"));
                kajian.setUrl2(kajian1.getString("Url2"));
                kajian.setFileName(kajian1.getString("FileName"));
                kajian.setTypeFile(kajian1.getString("TypeFile"));
                kajian.setPreview(kajian1.getString("Preview"));
                kajian.setQuality(kajian1.getInt("Quality"));
                kajian.setLocation(kajian1.getString("Location"));
                kajian.setDate(kajiHelper.GetDateFormat(kajian1.getString("Date")));
                kajian.setDBUstadz(ustadz);
                kajian.setCreatedDate(new Date());
                kajians.add(kajian);
            }
            return kajians;

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public synchronized List<DBKaji> GetDetailKajians(){
        List<DBKaji> detailKajians = null;
        try {
            openReadableDb();
            DBKajiDao kajianDao = daoSession.getDBKajiDao();
            DBUstadzDao ustadzDao = daoSession.getDBUstadzDao();
            /*long a = 136;
            List<DBKaji> kji = kajianDao.queryDeep("WHERE T.USTADZ_ID=1");
            List<DBUstadz> ust =  ustadzDao.loadAll();
            //QueryBuilder<DBKaji> kj = kajianDao.queryBuilder();//.where(DBKajiDao.Properties.UstadzID.eq(1));
            //Join ustadz = kj.join(DBKajiDao.Properties.UstadzId,DBUstadz.class,DBUstadzDao.Properties.UstadzId);
            //ustadz.where(DBUstadzDao.Properties.UstadzId.eq(1));
            Query<DBKaji> kj = kajianDao.queryRawCreate(
                    " LEFT JOIN "+DBUstadzDao.TABLENAME+" US"+
                            " ON T."+DBKajiDao.Properties.UstadzId.columnName+"= US."+DBUstadzDao.Properties.UstadzId.columnName+
                            " WHERE US."+DBUstadzDao.Properties.UstadzId.columnName+"=?",1);

            //.where(DBUstadzDao.Properties.Study.eq(""));
            //Join category = kj.join(DBKaji.class, DBKajiDao.Properties.CategoryID);
            detailKajians = kj.list();*/
            List<DBKaji> kji = kajianDao.loadAll();
            detailKajians = kji;
            daoSession.clear();
        } catch (Exception ex) {
            return null;
        }
        return detailKajians;
    }

    @Override
    public synchronized List<DBKaji> GetKajiansByUstadzId(long ustadzId){
        List<DBKaji> dataKajians = null;
        try {
            openReadableDb();
            DBKajiDao kajianDao = daoSession.getDBKajiDao();
            dataKajians = kajianDao.queryRaw("WHERE T.USTADZ_ID=" + ustadzId);
            daoSession.clear();
        }catch (Exception e){
           e.printStackTrace();
        }
        return dataKajians;
    }

    @Override
    public void onAsyncOperationCompleted(AsyncOperation operation) {
        completedOperations.add(operation);
    }
}
