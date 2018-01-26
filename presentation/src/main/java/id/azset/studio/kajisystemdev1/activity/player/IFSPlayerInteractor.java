package id.azset.studio.kajisystemdev1.activity.player;

import java.util.ArrayList;

import id.azset.studio.domain.model.KajiModel;

/**
 * Created by id_admchaset on 11/15/2017.
 */

public interface IFSPlayerInteractor {
    interface OnFinishedListener {
        void OnFinishedLoadMainData(KajiModel dbKaji);
    }
    void GetDataKajian(IFSPlayerInteractor.OnFinishedListener listener,long id);
}
