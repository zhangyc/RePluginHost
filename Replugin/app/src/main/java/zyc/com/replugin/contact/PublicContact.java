package zyc.com.replugin.contact;

/**
 * Created by zhangyc on 2018/5/4.
 */

public class PublicContact {
    //热更新的IP
    public static final String SERVER_IP = "http://operation.mobile.taikang.com";
    public static final String TEST_SERVER_IP = "http://10.130.212.39";
    //获取版本那补丁列表
    public static final String DOWNLOADPKG="/client/h";
    //获取补丁列表
    public static final String VERSIONFIXLISTS="/client/hotfix";
    //下载ICON
    public static final String ICON="/client/icon";
    //下载安装包
    public static final String DOWNLOADAPK="/client/v";
    //获取最新版本信息
    public static final String VERSIONINFOLISTS="/client/version";
    public static final String APP_KEY_PLUGINUPDATE = "26d97e97bdf64b228df8e8bb0430bf70";//版本在自更新平台的key
    public static final String FRAEWORK_VERSION = "1";//插件平台版本 不要随意修改和app版本号不是一回事情 这个是app和插件链接的sdk版本
}
