package id.azset.studio.data.repository;

import java.util.ArrayList;
import java.util.Set;

import id.azset.studio.data.dao.DBManageKaji;

/**
 * Created by id_admchaset on 10/18/2017.
 */

public interface IManageKajiRepository {
    void closeDbConnections();
    DBManageKaji getManageKajiById(Long manageKajiId);
    DBManageKaji insertManageKajian(DBManageKaji manageKajian);
    void updateManageKajian(DBManageKaji manageKajian);
    boolean deleteManageKajianById(Long manageKajianId);
    void deleteManageKajians();
    ArrayList<DBManageKaji> listManageKajians();
    boolean bulkInsertManageKajians(Set<DBManageKaji> manageKajian);
}
