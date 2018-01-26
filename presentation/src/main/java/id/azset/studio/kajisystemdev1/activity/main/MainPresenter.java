package id.azset.studio.kajisystemdev1.activity.main;

import java.util.ArrayList;
import java.util.List;

import id.azset.studio.data.dao.DBKaji;
import id.azset.studio.domain.model.KajiModel;

/**
 * Created by id_admchaset on 10/17/2017.
 */

public class MainPresenter implements IMainPresenter, MainInteractor.OnFinishedListener {
    private IMainView mainView;
    private MainInteractor mainInteractor;

    public MainPresenter(MainActivity mainView, MainInteractor mainInteractor) {
        this.mainView = mainView;
        this.mainInteractor = mainInteractor;
    }
    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onLoadMain() {
        if(mainView != null){
            mainView.showProgress();
        }
        mainInteractor.LoadMainData(this);
    }

    @Override
    public void OnFinishedLoadMainData(boolean bool) {
        mainView.hideProgress();
    }

    @Override
    public void OnFinishedGetLists(ArrayList<KajiModel> dbKajis) {
        if(mainView != null){
            mainView.setListKajian(dbKajis);
            mainView.hideProgress();
        }
    }


    @Override
    public void onDeletes(){
        if(mainView != null){
            mainView.showProgress();
        }
        mainInteractor.onDeletes(this);
    }

    @Override
    public void getDataKajians(){
        if(mainView != null){
            mainView.showProgress();
        }
        mainInteractor.getDataKajians(this);
    }
}
