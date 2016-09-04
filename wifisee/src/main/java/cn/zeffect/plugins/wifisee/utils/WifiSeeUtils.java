package cn.zeffect.plugins.wifisee.utils;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.zeffect.plugins.wifisee.bean.WifiBean;

/**
 * 查看WIFI密码相关工具
 * Created by xuan on 2016/9/4.
 */
public class WifiSeeUtils {
    /**
     * 获取WIFI列表
     *
     * @return 返回WIFI列表的JAVABEAN
     */
    public static List<WifiBean> getWifiList() {
        return anyWifiContent(getWifiConf());
    }

    /**
     * 得到WIFI文件内容
     *
     * @return
     */
    private static String getWifiConf() {
        Process process = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        StringBuffer wifiConf = new StringBuffer();
        try {
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataInputStream = new DataInputStream(process.getInputStream());
            dataOutputStream.writeBytes("cat /data/misc/wifi/wpa_supplicant.conf\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                wifiConf.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            process.waitFor();
        } catch (Exception e) {
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return wifiConf.toString();
    }

    /**
     * 解析WIFI文件内容
     *
     * @param pWifiContext 内容
     * @return
     */
    private static List<WifiBean> anyWifiContent(String pWifiContext) {
        List<WifiBean> tList = new LinkedList<>();
        Pattern network = Pattern.compile("network=\\{([^\\}]+)\\}", Pattern.DOTALL);
        Matcher networkMatcher = network.matcher(pWifiContext);
        while (networkMatcher.find()) {
            String networkBlock = networkMatcher.group();
            Pattern ssid = Pattern.compile("ssid=\"([^\"]+)\"");
            Matcher ssidMatcher = ssid.matcher(networkBlock);
            if (ssidMatcher.find()) {
                WifiBean tWifiBean = new WifiBean();
                tWifiBean.setSsid(ssidMatcher.group(1));
                Pattern psk = Pattern.compile("psk=\"([^\"]+)\"");
                Matcher pskMatcher = psk.matcher(networkBlock);
                if (pskMatcher.find()) {
                    tWifiBean.setPassword(pskMatcher.group(1));
                } else {
                    tWifiBean.setPassword("");
                }
                tList.add(tWifiBean);
            }
        }
        return tList;
    }

}
