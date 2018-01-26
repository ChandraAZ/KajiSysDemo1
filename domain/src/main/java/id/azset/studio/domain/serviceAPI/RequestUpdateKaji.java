package id.azset.studio.domain.serviceAPI;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import id.azset.studio.data.helper.KajiDataConfig;
import id.azset.studio.data.helper.KajiDataHelper;
import id.azset.studio.domain.business.IMainBusiness;
import id.azset.studio.domain.business.impl.MainBusiness;

/**
 * Created by id_admchaset on 10/26/2017.
 */

public class RequestUpdateKaji implements Response.Listener,Response.ErrorListener {
    Context context;
    private IMainBusiness mainBusiness;
    private static final String TAG = RequestUpdateKaji.class.getCanonicalName();
    private RequestQueue mQueue;
    private KajiDataHelper kajiHelper;
    public RequestUpdateKaji(Context context){
        this.context = context;
    }
    public void UpdateData(){
        try{
            // Instantiate the RequestQueue.
            mQueue = CustomVolleyRequestQueue.getInstance(context.getApplicationContext()).getRequestQueue();
            String url = KajiDataConfig.URL;
            try {
                final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url, new JSONObject(), this, this);
                jsonRequest.setTag(TAG);
                mQueue.add(jsonRequest);
            } catch (Exception ex) {
                Toast.makeText(context, "ERR : " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(context, "ERR : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Toast.makeText(context.getApplicationContext(), "Communication Error!", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(context.getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(context.getApplicationContext(), "Server Side Error!", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NetworkError) {
            Toast.makeText(context.getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(context.getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        mainBusiness = new MainBusiness(context);
        try {
            mainBusiness.ResponUpdateData(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}