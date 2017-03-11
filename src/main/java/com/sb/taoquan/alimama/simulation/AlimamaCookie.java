package com.sb.taoquan.alimama.simulation;

import java.util.Date;

/**
 * Created by tytx02 on 2017/1/25.
 */
public class AlimamaCookie {
    public static String _tb_token_ = "";
    public static String cookie = "";
    public static Date lastMachineTryTime = new Date(); //上一次机器尝试的时间
    public static Date lastManualTryTime = null; //上一次人工尝试的时间
    public static boolean isMachineLogin = true;
    public static boolean isManualLogin = false;
    public static boolean isLoginSucc = false;
    public static int tryLoginNum = 0;
    public static String loginErrorLog = "";
    public static Date lastDownloadSussTime = null;
    public static String downloadInfo = "未正在下载";
//    public static Date getLastDownloadTime
    public static int xlsToDbProcessSuccNum  = 0;
    public static int xlsToDbProcessErrorNum  = 0;
    public static int applyDxSuccNum = 0;
    public static int applyHdSuccNum = 0;
    public static int transferErrorNum = 0;

}
