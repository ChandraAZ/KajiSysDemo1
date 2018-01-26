package id.azset.studio.domain.business;

import java.util.ArrayList;

import id.azset.studio.domain.model.KajiModel;

/**
 * Created by id_admchaset on 10/25/2017.
 */

public interface IMainOnlineBusiness {
    ArrayList<KajiModel> GetKajiansByUstadzId(long ustadzID);
}
