package id.azset.studio.kajisystemdev1.activity.player;

import android.content.Context;
import android.os.Handler;

import id.azset.studio.domain.business.IMainBusiness;
import id.azset.studio.domain.business.IMainOnlineBusiness;
import id.azset.studio.domain.business.impl.MainBusiness;

/**
 * Created by id_admchaset on 11/15/2017.
 */

public class FSPlayerInteractor implements IFSPlayerInteractor {
    private IMainBusiness mainBusiness;
    private IMainOnlineBusiness mainOnlineBusiness;
    private Context context;
    public FSPlayerInteractor(final Context context){this.context = context;}


    @Override
    public void GetDataKajian(final OnFinishedListener listener, final long id) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mainBusiness = new MainBusiness(context) {
                };
                listener.OnFinishedLoadMainData(mainBusiness.GetKajianById(id));
            }
        },1200);
    }
}
