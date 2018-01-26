package id.azset.studio.kajisystemdev1.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by id_admchaset on 10/20/2017.
 */

public class KajianUtility {
    private Context context ;
    public KajianUtility(final  Context context) {
        this.context = context;
    }

    @Nullable public Date GetDateFormat(String value) throws ParseException {
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

    public void SaveIntPreferences(String key, int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("KAJI_SHARED_PREF",MODE_PRIVATE); //getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void SaveStringPreferences(String key, int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("KAJI_SHARED_PREF",MODE_PRIVATE); //getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int GetIntPreferences(String key){
        int result = 1;
        SharedPreferences sharedPreferences = context.getSharedPreferences("KAJI_SHARED_PREF",MODE_PRIVATE); //getPreferences(MODE_PRIVATE);
        result = sharedPreferences.getInt(key, 1);
        return result;
    }

    public String GetStringPreferences(String key){
        String result = "";
        SharedPreferences sharedPreferences = context.getSharedPreferences("KAJI_SHARED_PREF",MODE_PRIVATE); //getPreferences(MODE_PRIVATE);
        result = sharedPreferences.getString(key, "");
        return result;
    }
}
