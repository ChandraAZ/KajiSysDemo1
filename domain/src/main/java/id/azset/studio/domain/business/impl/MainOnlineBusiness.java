package id.azset.studio.domain.business.impl;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import id.azset.studio.data.dao.DBKaji;
import id.azset.studio.data.helper.KajiDataHelper;
import id.azset.studio.data.repository.ICategoryRepository;
import id.azset.studio.data.repository.IKajiRepository;
import id.azset.studio.data.repository.IUstadzRepository;
import id.azset.studio.data.repository.impl.KajiRepository;
import id.azset.studio.domain.business.IMainOnlineBusiness;
import id.azset.studio.domain.mapper.KajianModelDataMapper;
import id.azset.studio.domain.model.KajiModel;
import id.azset.studio.domain.serviceAPI.ManageVersion;

/**
 * Created by id_admchaset on 10/25/2017.
 */

public class MainOnlineBusiness implements IMainOnlineBusiness {

    Context context;
    private ICategoryRepository categoryRepo ;
    private IUstadzRepository ustadzRepo;
    private IKajiRepository kajiRepo;
    private KajiDataHelper kajiHelper;
    private ManageVersion mngVersion;
    KajianModelDataMapper mapperKajian;
    public MainOnlineBusiness(Context context) {
        this.context = context;
    }
    @Override
    public ArrayList<KajiModel> GetKajiansByUstadzId(long ustadzID) {
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
}
