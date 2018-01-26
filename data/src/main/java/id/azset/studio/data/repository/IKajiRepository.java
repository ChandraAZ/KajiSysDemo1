package id.azset.studio.data.repository;

import java.util.List;
import java.util.Set;

import id.azset.studio.data.dao.DBKaji;

/**
 * Created by id_admchaset on 10/18/2017.
 */

public interface IKajiRepository {
    void closeDbConnections();
    DBKaji getKajianById(Long kajianId);
    boolean KajianIsEmpty();
    boolean bulkInsertKajians(Set<DBKaji> kajians);
    void deleteKajians();
    List<DBKaji> SelectAll();
    boolean deleteKajianById(Long kajianId);
    void updateKajian(DBKaji kajian);
    DBKaji insertKajian(DBKaji kajian);
    Set<DBKaji> InsertJsonKajian(String json);
    List<DBKaji> GetDetailKajians();
    List<DBKaji> GetKajiansByUstadzId(long ustadzId);
}
