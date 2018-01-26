package id.azset.studio.domain.business;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import id.azset.studio.domain.model.CategoryModel;
import id.azset.studio.domain.model.KajiModel;
import id.azset.studio.domain.model.UstadzModel;

/**
 * Created by id_admchaset on 10/26/2017.
 */

public interface IMainBusiness {
    boolean LoadMainData();
    void ResponUpdateData(Object response) throws JSONException;
    boolean DeleteAll();
    ArrayList<KajiModel> LoadDataKajians();
    UstadzModel GetUstadzById(long key_);
    CategoryModel GetCategoryById (long key_);
    KajiModel GetKajianById(long key_);
}
