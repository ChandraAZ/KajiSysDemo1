package id.azset.studio.kajisystemdev1.activity.main;

import android.content.Context;
import android.os.Handler;

import id.azset.studio.domain.business.IMainBusiness;
import id.azset.studio.domain.business.IMainOnlineBusiness;
import id.azset.studio.domain.business.impl.MainBusiness;
import id.azset.studio.domain.business.impl.MainOnlineBusiness;

/**
 * Created by id_admchaset on 10/17/2017.
 */

public class MainInteractor implements IMainInteractor {
    private IMainBusiness mainBusiness;
    private IMainOnlineBusiness mainOnlineBusiness;
    private Context context ;
    public MainInteractor(final Context context){
        this.context = context;
    }


    @Override
    public void LoadMainData(final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mainBusiness = new MainBusiness(context) {
                };
                listener.OnFinishedLoadMainData(mainBusiness.LoadMainData());
            }
        },1200);
    }

    @Override
    public void onDeletes(final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mainBusiness = new MainBusiness(context) {
                };
                listener.OnFinishedLoadMainData(mainBusiness.DeleteAll());
            }
        },1200);
    }

    @Override
    public void getDataKajians(final OnFinishedListener listener){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mainOnlineBusiness = new MainOnlineBusiness(context) {
                };
                listener.OnFinishedGetLists(mainOnlineBusiness.GetKajiansByUstadzId(1));
            }
        },1200);
    }

}
