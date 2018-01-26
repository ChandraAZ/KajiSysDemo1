package id.azset.studio.kajisystemdev1.activity.main;

import java.util.ArrayList;
import java.util.List;

import id.azset.studio.domain.model.KajiModel;

/**
 * Created by id_admchaset on 10/17/2017.
 */

public interface IMainView {
    void showProgress();
    void hideProgress();
    void setListKajian(ArrayList<KajiModel> kajiData);

}
