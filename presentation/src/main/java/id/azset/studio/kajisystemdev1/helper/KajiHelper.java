package id.azset.studio.kajisystemdev1.helper;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by id_admchaset on 10/24/2017.
 */

public class KajiHelper {
    Context context ;
    public KajiHelper(Context context){
        this.context = context;
    }

    public String GetAssstsFile (String fileName) throws IOException {
        String result= "";
        StringBuilder buf = null;
        // get input stream for text
        InputStream is = context.getAssets().open(fileName);
        // check size
        int size = is.available();
        // create buffer for IO
        byte[] buffer = new byte[size];
        // get data to buffer
        is.read(buffer);
        // close stream
        is.close();
        // set result to TextView
        result = new String(buffer);
        return result;
    }

}
