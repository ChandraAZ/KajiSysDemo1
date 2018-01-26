package id.azset.studio.data.repository;

import java.util.ArrayList;
import java.util.Set;

import id.azset.studio.data.dao.DBUstadz;

/**
 * Created by id_admchaset on 10/18/2017.
 */

public interface IUstadzRepository {
    void closeDbConnections();
    DBUstadz getUstadzById(Long ustadzId);
    DBUstadz insertUstadz(DBUstadz ustadz);
    void updateUstadz(DBUstadz ustadz);
    boolean deleteUstadzById(Long ustadzById);
    void deleteUstadzs();
    ArrayList<DBUstadz> listUstadzs();
    boolean UstadzsIsEmpty();
    boolean bulkInsertUstadzs(Set<DBUstadz> ustadzs);
    Set<DBUstadz> InsertJsonUstadzs(String json);
}
