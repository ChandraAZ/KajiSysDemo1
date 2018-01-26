package id.azset.studio.domain.mapper;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import id.azset.studio.data.dao.DBKaji;
import id.azset.studio.domain.model.KajiModel;

/**
 * Created by id_admchaset on 10/20/2017.
 */

public class KajianModelDataMapper {
    Context context;
    public KajianModelDataMapper() {}
    public KajianModelDataMapper(Context context) {this.context = context;}
    public KajiModel transform(DBKaji dbKaji) {
        if (dbKaji == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final KajiModel kajiModel = new KajiModel(dbKaji.getId());
        kajiModel.setTitle(dbKaji.getTitle());
        kajiModel.setpemateri(dbKaji.getPemateri());
        kajiModel.setDuration(dbKaji.getDuration());
        kajiModel.setSize(dbKaji.getSize());
        kajiModel.setDescription(dbKaji.getDescription());
        kajiModel.setUrl1(dbKaji.getUrl1());
        kajiModel.setUrl2(dbKaji.getUrl2());
        kajiModel.setFileName(dbKaji.getFileName());
        kajiModel.setTypeFile(dbKaji.getTypeFile());
        kajiModel.setPreview(dbKaji.getPreview());
        kajiModel.setQuality(dbKaji.getQuality());
        kajiModel.setLocation(dbKaji.getLocation());
        kajiModel.setDate(dbKaji.getDate());
        kajiModel.setCreatedDate(dbKaji.getCreatedDate());
        kajiModel.setCategoryID(dbKaji.getCategoryId(),this.context);
        kajiModel.setUstadzID(dbKaji.getUstadzId(),this.context);
        return kajiModel;
    }


    public Collection<KajiModel> transform(Collection<DBKaji> kajiansCollection) {
        Collection<KajiModel> KajiModelsCollection;

        if (kajiansCollection != null && !kajiansCollection.isEmpty()) {
            KajiModelsCollection = new ArrayList<>();
            for (DBKaji Kaji : kajiansCollection) {
                KajiModelsCollection.add(transform(Kaji));
            }
        } else {
            KajiModelsCollection = Collections.emptyList();
        }

        return KajiModelsCollection;
    }

}
