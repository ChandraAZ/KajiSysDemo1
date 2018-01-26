package id.azset.studio.kajisystemdev1.activity.player;

import android.content.Context;

import id.azset.studio.domain.model.KajiModel;

/**
 * Created by id_admchaset on 11/15/2017.
 */

public class FSPlayerPresenter implements  IFSPlayerPresenter,FSPlayerInteractor.OnFinishedListener {
    private IFSPlayerView fSPlayerView;
    private FSPlayerInteractor fSPlayerInteractor;

    public FSPlayerPresenter(FullScreenPlayer fSPlayerView, FSPlayerInteractor fSPlayerInteractor){
        this.fSPlayerView = fSPlayerView;
        this.fSPlayerInteractor = fSPlayerInteractor;
    }

    @Override
    public void getKajianbyID(long id) {
        if(fSPlayerView != null){
            fSPlayerView.showProgress();
        }
        fSPlayerInteractor.GetDataKajian(this,id);
    }

    @Override
    public void OnFinishedLoadMainData(KajiModel kajiModel) {
        if(fSPlayerView != null){
            fSPlayerView.setKajian(kajiModel);
            fSPlayerView.hideProgress();
        }
    }
}
