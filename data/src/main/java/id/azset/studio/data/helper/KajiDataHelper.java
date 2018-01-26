package id.azset.studio.data.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by id_admchaset on 10/24/2017.
 */

public class KajiDataHelper {
    Context context ;
    public KajiDataHelper(Context context){
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

    public Boolean InternetIsConnected() {
        ConnectivityManager cn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();
        if (nf != null && nf.isConnected() == true) {
            return true;
        } else {
            Toast.makeText(context, "No internet connection.!", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public static boolean IsWifiConnected(Context context){
        NetworkInfo localNetworkInfo = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (localNetworkInfo == null) {
            return false;
        }
        else{
            //Check if only wifi is selected or wifi==1  Constant Value: 1 (0x00000001)
            if(((localNetworkInfo.getType() == 1)) || (localNetworkInfo.isConnected()) || (localNetworkInfo.isAvailable()))
                return true;
        }
        return false;
    }
    public int GetIntPreferences(String key){
        int result = 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences("KAJI_SHARED_PREF",MODE_PRIVATE); //getPreferences(MODE_PRIVATE);
        result = sharedPreferences.getInt(key, 0);
        return result;
    }

    public void SetIntPreferences(String key,int value ){
        SharedPreferences sharedPreferences = context.getSharedPreferences("KAJI_SHARED_PREF",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    @Nullable
    public Date GetDateFormat(String value) throws ParseException {
        String format = "yyyy-MM-dd";
        if(value.equals("") || value.equals(null)) {return null;}
        Date dt = new Date();
        if(!tryParsetoDate(value,format)){return null;}
        DateFormat dtFormat = new SimpleDateFormat(format);
        dt = dtFormat.parse(value);
        return dt;
    }

    private boolean tryParsetoDate(String value, String format){
        boolean result = false;
        try{
            DateFormat dtFormat = new SimpleDateFormat(format);
            dtFormat.parse(value);
            result = true;
        }
        catch (Exception e){
            return result;
        }
        return result;
    }
}
