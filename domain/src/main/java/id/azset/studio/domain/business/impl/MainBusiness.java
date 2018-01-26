package id.azset.studio.domain.business.impl;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import id.azset.studio.data.dao.DBCategory;
import id.azset.studio.data.dao.DBKaji;
import id.azset.studio.data.dao.DBUstadz;
import id.azset.studio.data.helper.KajiDataConfig;
import id.azset.studio.data.helper.KajiDataHelper;
import id.azset.studio.data.repository.ICategoryRepository;
import id.azset.studio.data.repository.IKajiRepository;
import id.azset.studio.data.repository.IUstadzRepository;
import id.azset.studio.data.repository.impl.CategoryRepository;
import id.azset.studio.data.repository.impl.KajiRepository;
import id.azset.studio.data.repository.impl.UstadzRepository;
import id.azset.studio.domain.business.IMainBusiness;
import id.azset.studio.domain.mapper.CategoryModelDataMapper;
import id.azset.studio.domain.mapper.KajianModelDataMapper;
import id.azset.studio.domain.mapper.UstadzModelDataMapper;
import id.azset.studio.domain.model.CategoryModel;
import id.azset.studio.domain.model.KajiModel;
import id.azset.studio.domain.model.UstadzModel;
import id.azset.studio.domain.serviceAPI.ManageVersion;
import id.azset.studio.domain.serviceAPI.RequestUpdateKaji;

/**
 * Created by id_admchaset on 10/26/2017.
 */

public class MainBusiness implements IMainBusiness {
    private Context context;
    private ICategoryRepository categoryRepo ;
    private IUstadzRepository ustadzRepo;
    private IKajiRepository kajiRepo;
    private KajiDataHelper kajiHelper;
    private ManageVersion mngVersion;
    private RequestUpdateKaji requestUpdate;
    private KajianModelDataMapper mapperKajian;
    private UstadzModelDataMapper mapperUstadz;
    private CategoryModelDataMapper mapperCategory;
    public MainBusiness(Context context) {
        this.context = context;
    }

    public boolean LoadMainData(){
        boolean result = false ;
        kajiHelper = new KajiDataHelper(context);
        try{
            int thisVersion = kajiHelper.GetIntPreferences(KajiDataConfig.REF_VERSION);
            if(thisVersion == 0) {
                if (InsertDataJson()) {
                    kajiHelper.SetIntPreferences(KajiDataConfig.REF_VERSION,1);
                }
            }
            result = CheckingUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private boolean CheckingUpdate() {
        boolean result = false ;
        mngVersion = new ManageVersion(context);
        kajiHelper = new KajiDataHelper(context);
        try{
            if(kajiHelper.InternetIsConnected()){
                mngVersion.VersionUpdate(); // request API
            }
        }catch (Exception e){
            Toast.makeText(context, "ERR : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    @Override
    public void ResponUpdateData(Object response) throws JSONException {
        kajiHelper = new KajiDataHelper(context);
        kajiRepo = new KajiRepository(context);
        String jsonText = ((JSONObject)response).getString("kajian");
        int newVersion = 0;
        if(jsonText.toString()!="") {
            kajiRepo.deleteKajians();
            Set<DBKaji> kajians = kajiRepo.InsertJsonKajian(jsonText.toString());
            boolean isSaved = kajiRepo.bulkInsertKajians(kajians);
            if (!isSaved) {
                return;
            }
        }

        newVersion = ((JSONObject)response).getInt("version");
        // update version kajian
        kajiHelper.SetIntPreferences(KajiDataConfig.REF_VERSION,newVersion);
        Toast.makeText(context, "Update version v-0" + newVersion + " Success!.", Toast.LENGTH_SHORT).show();
        //Snackbar.make(context, "Update version v-0" + newVersion + " Success!.", Snackbar.LENGTH_LONG).setAction("No action", null).show();
    }

    private boolean InsertDataJson(){
        boolean result = false;
        String jsonText;
        categoryRepo = new CategoryRepository(context);
        ustadzRepo = new UstadzRepository(context);
        kajiRepo = new KajiRepository(context);
        kajiHelper = new KajiDataHelper(context);
        try{
            categoryRepo.deleteCategories();
             jsonText = kajiHelper.GetAssstsFile(KajiDataConfig.CATEGORY_JSONFILE);
            if(jsonText.toString()!="") {
                Set<DBCategory> categories = categoryRepo.InsertJsonCategories(jsonText.toString());
                boolean isSaved = categoryRepo.bulkInsertCategories(categories);
                if (!isSaved) {
                    //return;
                }
            }

            ustadzRepo.deleteUstadzs();
             jsonText = kajiHelper.GetAssstsFile(KajiDataConfig.USTADZ_JSONFILE);
            if(jsonText.toString()!="") {
                Set<DBUstadz> ustadzs = ustadzRepo.InsertJsonUstadzs(jsonText.toString());
                boolean isSaved = ustadzRepo.bulkInsertUstadzs(ustadzs);
                if (!isSaved) {
                    //return;
                }
            }

            kajiRepo.deleteKajians();
            jsonText = kajiHelper.GetAssstsFile(KajiDataConfig.KAJI_JSONFILE);
            if(jsonText.toString()!="") {
                Set<DBKaji> kajians = kajiRepo.InsertJsonKajian(jsonText.toString());
                boolean isSaved = kajiRepo.bulkInsertKajians(kajians);
                if (!isSaved) {
                    //return;
                }
            }
            result = true ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    @Override
    public boolean DeleteAll() {
        boolean result = false;
        try {
            kajiHelper = new KajiDataHelper(context);
            categoryRepo = new CategoryRepository(context);
            ustadzRepo = new UstadzRepository(context);
            kajiRepo = new KajiRepository(context);
            categoryRepo.deleteCategories();
            ustadzRepo.deleteUstadzs();
            kajiRepo.deleteKajians();
            kajiHelper.SetIntPreferences(KajiDataConfig.REF_VERSION,0);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<KajiModel> LoadDataKajians() {
        List<DBKaji> kajians  ;
        mapperKajian = new KajianModelDataMapper(this.context);
        ArrayList<KajiModel> modelKajians = new ArrayList<KajiModel>();
        try {
            kajiRepo = new KajiRepository(context);
            kajians = kajiRepo.SelectAll();
            if(kajians !=null && kajians.size() >0){
                modelKajians = (ArrayList<KajiModel>) mapperKajian.transform(kajians);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelKajians;
    }

    @Override
    public UstadzModel GetUstadzById(long key_){
        mapperUstadz = new UstadzModelDataMapper();
        UstadzModel ustadzModel = null;
        try{
            UstadzRepository ustadzRepository = new UstadzRepository(context);
            DBUstadz dbUstadz = ustadzRepository.getUstadzById(key_);
            if(dbUstadz !=null) {
                ustadzModel = mapperUstadz.transform(dbUstadz);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ustadzModel;
    }

    @Override
    public CategoryModel GetCategoryById(long key_){
        mapperCategory = new CategoryModelDataMapper();
        CategoryModel categoryModel = null;
        try{
            CategoryRepository ustadzRepository = new CategoryRepository(context);
            DBCategory dbCategory = ustadzRepository.getCategoryById(key_);
            if(dbCategory !=null) {
                categoryModel = mapperCategory.transform(dbCategory);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return categoryModel;
    }

    @Override
    public KajiModel GetKajianById(long key_){
        DBKaji kajian  ;
        mapperKajian = new KajianModelDataMapper(this.context);
        KajiModel modelKajian = null;
        try {
            kajiRepo = new KajiRepository(context);
            kajian = kajiRepo.getKajianById(key_);
            if(kajian !=null){
                modelKajian = mapperKajian.transform(kajian);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelKajian;
    }

}
