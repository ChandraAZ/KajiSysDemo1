package id.azset.studio.domain.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import id.azset.studio.data.dao.DBManageKaji;
import id.azset.studio.domain.model.ManageKajiModel;

/**
 * Created by id_admchaset on 10/19/2017.
 */

public class ManageKajiModelDataMapper {
    public ManageKajiModelDataMapper() {}

    public ManageKajiModel transform(DBManageKaji dbManageKaji) {
        if (dbManageKaji == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final ManageKajiModel ManageKajiModel = new ManageKajiModel(dbManageKaji.getId());

        ManageKajiModel.setIsFavorite(dbManageKaji.getIsFavorite());
        ManageKajiModel.setIsDownload(dbManageKaji.getIsDownload());
        ManageKajiModel.setIsNew(dbManageKaji.getIsNew());
        ManageKajiModel.setLocalPath1(dbManageKaji.getLocalPath1());
        ManageKajiModel.setLocalPath2(dbManageKaji.getLocalPath2());
        ManageKajiModel.setRating(dbManageKaji.getRating());
        ManageKajiModel.setIsDeleted(dbManageKaji.getIsDeleted());
        ManageKajiModel.setKajianID(dbManageKaji.getId());

        return ManageKajiModel;
    }

    public Collection<ManageKajiModel> transform(Collection<DBManageKaji> manageKajiansCollection) {
        Collection<ManageKajiModel> ManageKajiModelsCollection;
        if (manageKajiansCollection != null && !manageKajiansCollection.isEmpty()) {
            ManageKajiModelsCollection = new ArrayList<>();
            for (DBManageKaji ManageKaji : manageKajiansCollection) {
                ManageKajiModelsCollection.add(transform(ManageKaji));
            }
        } else {
            ManageKajiModelsCollection = Collections.emptyList();
        }
        return ManageKajiModelsCollection;
    }
}
