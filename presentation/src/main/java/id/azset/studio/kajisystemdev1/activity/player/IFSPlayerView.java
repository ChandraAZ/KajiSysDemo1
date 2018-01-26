package id.azset.studio.kajisystemdev1.activity.player;

import java.util.ArrayList;

import id.azset.studio.domain.model.KajiModel;

/**
 * Created by id_admchaset on 11/15/2017.
 */

public interface IFSPlayerView {

    void showProgress();
    void hideProgress();
    void setKajian(KajiModel kajiData);
    void setTimeBar(String a);
    void setNext();
}
