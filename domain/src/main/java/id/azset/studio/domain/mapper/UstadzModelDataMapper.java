package id.azset.studio.domain.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import id.azset.studio.data.dao.DBUstadz;
import id.azset.studio.domain.model.UstadzModel;

/**
 * Created by id_admchaset on 10/19/2017.
 */

public class UstadzModelDataMapper {
    public UstadzModelDataMapper() {}

    public UstadzModel transform(DBUstadz dbUstadz) {
        if (dbUstadz == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final UstadzModel UstadzModel = new UstadzModel(dbUstadz.getUstadzId());
        UstadzModel.setName(dbUstadz.getName());
        UstadzModel.setDescription(dbUstadz.getDescription());
        UstadzModel.setStudy((dbUstadz.getStudy()));
        return UstadzModel;
    }

    public Collection<UstadzModel> transform(Collection<DBUstadz> ustadzsCollection) {
        Collection<UstadzModel> UstadzModelsCollection;
        if (ustadzsCollection != null && !ustadzsCollection.isEmpty()) {
            UstadzModelsCollection = new ArrayList<>();
            for (DBUstadz Ustadz : ustadzsCollection) {
                UstadzModelsCollection.add(transform(Ustadz));
            }
        } else {
            UstadzModelsCollection = Collections.emptyList();
        }
        return UstadzModelsCollection;
    }
}
