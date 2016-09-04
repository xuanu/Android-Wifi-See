# Android-Wifi-See
WIFI密码查看器  

#实现原理  
  从/data/misc/wifi/wpa_supplicant.conf文件中读取WIFI密码，需要root权限  
    
#使用方法  
#####1    
  添加仓库 ```maven { url "https://jitpack.io" }```  
  添加依赖 ```compile 'com.github.xuanu:Android-Wifi-See:0.0.1'```  
#####提供方法    

```
ShellUtils.checkRootPermission();//检查有无root权限
WifiSeeUtils.getWifiList()//获取WIFI列表，返回List<WifiBean>
```
  
截图：  
  ![image](https://github.com/xuanu/Android-Wifi-See/raw/master/screenshots/Screenshot_20160904-160729.png)  
APK:  
   https://github.com/xuanu/Android-Wifi-See/raw/master/screenshots/WIFI密码查看器.apk
