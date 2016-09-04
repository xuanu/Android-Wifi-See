package cn.zeffect.apk.android_wifi_see;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import cn.zeffect.apk.android_wifi_see.utils.WifiAdapter;
import cn.zeffect.plugins.wifisee.bean.WifiBean;
import cn.zeffect.plugins.wifisee.utils.ShellUtils;
import cn.zeffect.plugins.wifisee.utils.WifiSeeUtils;


public class MainActivity extends Activity {
    ListView mListView;
    private List<WifiBean> mWifis = new LinkedList<>();
    private WifiAdapter mAdapter;
    ImageView mRefreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mRefreshView = (ImageView) findViewById(R.id.am_refresh_img);
        mRefreshView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startAnim(view);
                refreshList();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stopAnim(view);
                    }
                }, 1000);
            }
        });
        mListView = (ListView) findViewById(R.id.am_wifi_list);
        mAdapter = new WifiAdapter(this, mWifis);
        mListView.setAdapter(mAdapter);
    }

    private void startAnim(View pView) {
        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        pView.startAnimation(operatingAnim);
    }

    private void stopAnim(View pView) {
        pView.clearAnimation();
    }

    private void refreshList() {
        if (ShellUtils.checkRootPermission()) {
            mWifis.clear();
            mWifis.addAll(WifiSeeUtils.getWifiList());
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(MainActivity.this, "没有root权限", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        refreshList();
        super.onResume();
    }
}
