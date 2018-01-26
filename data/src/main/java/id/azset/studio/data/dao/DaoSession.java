package id.azset.studio.data.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import id.azset.studio.data.dao.DBKaji;
import id.azset.studio.data.dao.DBManageKaji;
import id.azset.studio.data.dao.DBCategory;
import id.azset.studio.data.dao.DBUstadz;

import id.azset.studio.data.dao.DBKajiDao;
import id.azset.studio.data.dao.DBManageKajiDao;
import id.azset.studio.data.dao.DBCategoryDao;
import id.azset.studio.data.dao.DBUstadzDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig dBKajiDaoConfig;
    private final DaoConfig dBManageKajiDaoConfig;
    private final DaoConfig dBCategoryDaoConfig;
    private final DaoConfig dBUstadzDaoConfig;

    private final DBKajiDao dBKajiDao;
    private final DBManageKajiDao dBManageKajiDao;
    private final DBCategoryDao dBCategoryDao;
    private final DBUstadzDao dBUstadzDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        dBKajiDaoConfig = daoConfigMap.get(DBKajiDao.class).clone();
        dBKajiDaoConfig.initIdentityScope(type);

        dBManageKajiDaoConfig = daoConfigMap.get(DBManageKajiDao.class).clone();
        dBManageKajiDaoConfig.initIdentityScope(type);

        dBCategoryDaoConfig = daoConfigMap.get(DBCategoryDao.class).clone();
        dBCategoryDaoConfig.initIdentityScope(type);

        dBUstadzDaoConfig = daoConfigMap.get(DBUstadzDao.class).clone();
        dBUstadzDaoConfig.initIdentityScope(type);

        dBKajiDao = new DBKajiDao(dBKajiDaoConfig, this);
        dBManageKajiDao = new DBManageKajiDao(dBManageKajiDaoConfig, this);
        dBCategoryDao = new DBCategoryDao(dBCategoryDaoConfig, this);
        dBUstadzDao = new DBUstadzDao(dBUstadzDaoConfig, this);

        registerDao(DBKaji.class, dBKajiDao);
        registerDao(DBManageKaji.class, dBManageKajiDao);
        registerDao(DBCategory.class, dBCategoryDao);
        registerDao(DBUstadz.class, dBUstadzDao);
    }
    
    public void clear() {
        dBKajiDaoConfig.clearIdentityScope();
        dBManageKajiDaoConfig.clearIdentityScope();
        dBCategoryDaoConfig.clearIdentityScope();
        dBUstadzDaoConfig.clearIdentityScope();
    }

    public DBKajiDao getDBKajiDao() {
        return dBKajiDao;
    }

    public DBManageKajiDao getDBManageKajiDao() {
        return dBManageKajiDao;
    }

    public DBCategoryDao getDBCategoryDao() {
        return dBCategoryDao;
    }

    public DBUstadzDao getDBUstadzDao() {
        return dBUstadzDao;
    }

}
