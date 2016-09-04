package cn.zeffect.apk.android_wifi_see.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import cn.zeffect.apk.android_wifi_see.R;
import cn.zeffect.plugins.wifisee.bean.WifiBean;

/**
 * Created by xuan on 2016/9/4.
 */
public class WifiAdapter extends BaseAdapter {
    private List<WifiBean> mWifis;
    private Context mContext;

    public WifiAdapter(Context pContext, List<WifiBean> pWifis) {
        if (pContext == null) {
            throw new NullPointerException("comtext not  null");
        }
        mContext = pContext;
        mWifis = (pWifis == null ? new LinkedList<WifiBean>() : pWifis);
    }

    @Override
    public int getCount() {
        return mWifis.size();
    }

    @Override
    public Object getItem(int i) {
        return mWifis.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodler hodler;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_wifi_list, null);
            hodler = new ViewHodler();
            hodler.ssid = (TextView) view.findViewById(R.id.iwl_ssid);
            hodler.pw = (TextView) view.findViewById(R.id.iwl_pw);
            view.setTag(hodler);
        } else {
            hodler = (ViewHodler) view.getTag();
        }
        WifiBean tBean = mWifis.get(i);
        hodler.ssid.setText(tBean.getSsid());
        hodler.pw.setText(TextUtils.isEmpty(tBean.getPassword()) ? "无密码" : tBean.getPassword());
        return view;
    }

    static class ViewHodler {
        TextView ssid, pw;
    }

}
