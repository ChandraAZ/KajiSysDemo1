package id.azset.studio.domain.business.impl;

import android.content.Context;

import id.azset.studio.data.helper.KajiDataHelper;
import id.azset.studio.data.repository.ICategoryRepository;
import id.azset.studio.data.repository.IKajiRepository;
import id.azset.studio.data.repository.IUstadzRepository;
import id.azset.studio.domain.business.IMainOfflineBusiness;
import id.azset.studio.domain.serviceAPI.ManageVersion;

/**
 * Created by id_admchaset on 10/25/2017.
 */

public class MainOfflineBusiness implements IMainOfflineBusiness {

    private Context context;
    private ICategoryRepository categoryRepo ;
    private IUstadzRepository ustadzRepo;
    private IKajiRepository kajiRepo;
    private KajiDataHelper kajiHelper;
    private ManageVersion mngVersion;

    public MainOfflineBusiness(Context context) {
        this.context = context;
    }


}
