package id.azset.studio.data.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DBCATEGORY".
*/
public class DBCategoryDao extends AbstractDao<DBCategory, Long> {

    public static final String TABLENAME = "DBCATEGORY";

    /**
     * Properties of entity DBCategory.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property CategoryId = new Property(0, Long.class, "CategoryId", true, "CATEGORY_ID");
        public final static Property Name = new Property(1, String.class, "Name", false, "NAME");
        public final static Property IsDeleted = new Property(2, Boolean.class, "IsDeleted", false, "IS_DELETED");
    }


    public DBCategoryDao(DaoConfig config) {
        super(config);
    }
    
    public DBCategoryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DBCATEGORY\" (" + //
                "\"CATEGORY_ID\" INTEGER PRIMARY KEY ," + // 0: CategoryId
                "\"NAME\" TEXT NOT NULL ," + // 1: Name
                "\"IS_DELETED\" INTEGER);"); // 2: IsDeleted
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DBCATEGORY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DBCategory entity) {
        stmt.clearBindings();
 
        Long CategoryId = entity.getCategoryId();
        if (CategoryId != null) {
            stmt.bindLong(1, CategoryId);
        }
        stmt.bindString(2, entity.getName());
 
        Boolean IsDeleted = entity.getIsDeleted();
        if (IsDeleted != null) {
            stmt.bindLong(3, IsDeleted ? 1L: 0L);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DBCategory entity) {
        stmt.clearBindings();
 
        Long CategoryId = entity.getCategoryId();
        if (CategoryId != null) {
            stmt.bindLong(1, CategoryId);
        }
        stmt.bindString(2, entity.getName());
 
        Boolean IsDeleted = entity.getIsDeleted();
        if (IsDeleted != null) {
            stmt.bindLong(3, IsDeleted ? 1L: 0L);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DBCategory readEntity(Cursor cursor, int offset) {
        DBCategory entity = new DBCategory( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // CategoryId
            cursor.getString(offset + 1), // Name
            cursor.isNull(offset + 2) ? null : cursor.getShort(offset + 2) != 0 // IsDeleted
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DBCategory entity, int offset) {
        entity.setCategoryId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setIsDeleted(cursor.isNull(offset + 2) ? null : cursor.getShort(offset + 2) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DBCategory entity, long rowId) {
        entity.setCategoryId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DBCategory entity) {
        if(entity != null) {
            return entity.getCategoryId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DBCategory entity) {
        return entity.getCategoryId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
