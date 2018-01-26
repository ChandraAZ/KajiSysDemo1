package id.azset.studio.kajisystemdev1.activity.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import id.azset.studio.domain.model.KajiModel;
import id.azset.studio.kajisystemdev1.R;
import id.azset.studio.kajisystemdev1.activity.player.FullScreenPlayer;
import id.azset.studio.kajisystemdev1.adapter.OnlineCustomAdapter;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements IMainView{
    IMainPresenter presenter;
    ArrayList<KajiModel> dataModels;
    private static OnlineCustomAdapter adapter;
    @BindView(R.id.btnDelete) Button btnDelete;
    @BindView(R.id.btnLoadMain) Button btnLoadMain;
    @BindView(R.id.textView) TextView textView;
    @BindView(R.id.progressBar)ProgressBar progressBar;
    @BindView(R.id.kajiList) ListView kajiList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this, new MainInteractor(this.getApplicationContext()));
        presenter.getDataKajians();
    }
    @OnItemClick(R.id.kajiList) public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,FullScreenPlayer.class );
        intent.putExtra("KeyID", id);
        startActivity(intent);
    }

    @OnClick(R.id.btnLoadMain) void btnLoadMaonOnClick(View view){
        presenter.onLoadMain();
    }

    @OnClick(R.id.btnDelete) void btbDeleteOnClick(View view){
        presenter.onDeletes();
    }

    @Override
    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress(){
        progressBar.setVisibility(GONE);
    }

    @Override
    public void setListKajian(ArrayList<KajiModel> dataKajians){
        adapter= new OnlineCustomAdapter(dataKajians,this.getApplicationContext());
        kajiList.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
