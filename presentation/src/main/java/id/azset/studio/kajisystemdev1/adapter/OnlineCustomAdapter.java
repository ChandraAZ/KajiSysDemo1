package id.azset.studio.kajisystemdev1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import id.azset.studio.domain.model.KajiModel;
import id.azset.studio.kajisystemdev1.R;


/**
 * Created by id_admchaset on 10/27/2017.
 */

public class OnlineCustomAdapter extends ArrayAdapter<KajiModel> implements View.OnClickListener {

    private ArrayList<KajiModel> dataSet;
    Context mContext;
    KajiModel km = new KajiModel(mContext);
    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle;
        TextView txtPemateri;
        TextView txtLocation;
        TextView txtDuration;
    }

    public OnlineCustomAdapter(ArrayList<KajiModel> data, Context context) {
        super(context, R.layout.list_row, data);
        this.dataSet = data;
        this.mContext = context;

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataSet.size();
    }

    @Override
    public KajiModel getItem(int position) {
        // TODO Auto-generated method stub
        km = dataSet.get(position);
        return km;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        km =  dataSet.get(position);
        return km.getID();
    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        KajiModel dataModel = (KajiModel) object;

        switch (v.getId()) {
            case R.id.title1:
                //Snackbar.make(v, "Release date " + dataModel.getFeature(), Snackbar.LENGTH_LONG).setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        KajiModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row, null);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.title1);
            viewHolder.txtPemateri = (TextView) convertView.findViewById(R.id.pemateri);
            //viewHolder.txtLocation = (TextView) convertView.findViewById(R.id.);
            viewHolder.txtDuration = (TextView) convertView.findViewById(R.id.duration);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtTitle.setText(dataModel.getTitle());
        viewHolder.txtPemateri.setText(dataModel.getpemateri());
        viewHolder.txtDuration .setText(dataModel.getDuration());
       // viewHolder.txtTypeFile.setOnClickListener(this);
        //viewHolder..setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}