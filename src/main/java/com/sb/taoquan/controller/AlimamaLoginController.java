package com.sb.taoquan.controller;

import com.sb.taoquan.alimama.simulation.AlimamaCookie;
import com.sb.taoquan.alimama.simulation.DownloadQuanXls;
import com.sb.taoquan.alimama.simulation.LoginSimulation;
import com.sb.taoquan.alimama.simulation.XlsQuanToDb;
import com.sb.taoquan.dao.IQuanProductDao;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 小平 on 2017/1/28.
 */
@Controller
@RequestMapping("/loginHelp")
public class AlimamaLoginController {
    @Autowired
    private IQuanProductDao quanProductDao;
    @Autowired
    private XlsQuanToDb xlsQuanToDb;
    @Autowired
    private DownloadQuanXls downloadQuanXls;
    @RequestMapping(value = "/toLogin",method = RequestMethod.GET)
    public String toLogin(Model model){
        String loginRole;
        String lastLoginTime;
        String loginSuccFlag;
        String loginErrorMsg;
        String cookie;
        String _tb_token_;
        String lastDownloadSussTime;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (AlimamaCookie.isMachineLogin) {
            loginRole = "机器";
            lastLoginTime = df.format(AlimamaCookie.lastMachineTryTime);
        }  else {
            loginRole = "人工";
            lastLoginTime = df.format(AlimamaCookie.lastManualTryTime);
        }
        loginSuccFlag = AlimamaCookie.isLoginSucc ? "成功" : "失败";
        loginErrorMsg = AlimamaCookie.loginErrorLog;
        cookie = AlimamaCookie.cookie;
        _tb_token_ = AlimamaCookie._tb_token_;
        if (AlimamaCookie.lastDownloadSussTime == null) {
            lastDownloadSussTime = "还未进行过一次下载";
        } else {
            lastDownloadSussTime = df.format(AlimamaCookie.lastDownloadSussTime);
        }

        model.addAttribute("loginRole", loginRole);
        model.addAttribute("lastLoginTime", lastLoginTime);
        model.addAttribute("loginSuccFlag", loginSuccFlag);
        model.addAttribute("loginErrorMsg", loginErrorMsg);
        model.addAttribute("cookie", cookie);
        model.addAttribute("_tb_token_", _tb_token_);
        model.addAttribute("xlsToDbProcessSuccNum", AlimamaCookie.xlsToDbProcessSuccNum);
        model.addAttribute("xlsToDbProcessErrorNum", AlimamaCookie.xlsToDbProcessErrorNum);
        model.addAttribute("lastDownloadSussTime", lastDownloadSussTime);
        model.addAttribute("applyDxSuccNum", AlimamaCookie.applyDxSuccNum);
        model.addAttribute("applyHdSuccNum", AlimamaCookie.applyHdSuccNum);
        model.addAttribute("downloadInfo", AlimamaCookie.downloadInfo);
        model.addAttribute("tryLoginNum", AlimamaCookie.tryLoginNum);
        return "pc/login/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> login(){
        Map<String, String> map = new HashMap<>();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                LoginSimulation.login(true);
            }
        };
        new Thread(runnable).start();
        map.put("msg", "人工登录已触发，请等待直到机器自动新开的浏览器自动关闭");
        return map;
    }

    @RequestMapping(value = "/download",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> download(){
        Map<String, String> map = new HashMap<>();
        map.put("msg", "人工触发下载护航完成,下载已开始，下载非常占用内存，导致页面会有卡的现象，请耐心关注页面的自动刷新");
        Runnable runnable =
                new Runnable() {
                    @Override
                    public void run() {
                        Date xlsToDbDay = quanProductDao.getXlsToDbDay();
                        AlimamaCookie.lastDownloadSussTime = xlsToDbDay;
                        AlimamaCookie.xlsToDbProcessSuccNum = 0;
                        AlimamaCookie.xlsToDbProcessErrorNum = 0;
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        if (AlimamaCookie.isLoginSucc) {
                            AlimamaCookie.downloadInfo = "["+df.format(new Date())+"]" + "下载启动,系统会吃掉大量内存，会比较卡，估计时间花费在20分钟";
                            if (! DateUtils.isSameDay(xlsToDbDay, new Date())) {
                                boolean downloadFlag = downloadQuanXls.download();
                                try {
                                    if (downloadFlag) {
                                        xlsQuanToDb.xlsQuanToDb();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    AlimamaCookie.downloadInfo = "["+df.format(new Date())+"]" + " 下载过程中失败了:" + e.getMessage();
                                }
                            }
                            AlimamaCookie.downloadInfo = "["+df.format(new Date())+"]" + " 下载成功";
                        } else {
                            AlimamaCookie.downloadInfo = "["+df.format(new Date())+"]" + " cookie失效，下载失败，请登录护航后，触发下载护航";
                        }
                    }
                };
        new Thread(runnable).start();
        return map;
    }

    @RequestMapping(value = "/refreshLoginInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> refreshLoginInfo(Model model){
        Map<String, Object> map = new HashMap<>();
        String loginRole;
        String lastLoginTime;
        String loginSuccFlag;
        String loginErrorMsg;
        String cookie;
        String _tb_token_;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (AlimamaCookie.isMachineLogin) {
            loginRole = "机器";
            lastLoginTime = df.format(AlimamaCookie.lastMachineTryTime);
        }  else {
            loginRole = "人工";
            lastLoginTime = df.format(AlimamaCookie.lastManualTryTime);
        }
        loginSuccFlag = AlimamaCookie.isLoginSucc ? "成功" : "失败";
        loginErrorMsg = AlimamaCookie.loginErrorLog;
        cookie = AlimamaCookie.cookie;
        _tb_token_ = AlimamaCookie._tb_token_;
        map.put("loginRole", loginRole);
        map.put("lastLoginTime", lastLoginTime);
        map.put("loginSuccFlag", loginSuccFlag);
        map.put("loginErrorMsg", loginErrorMsg);
        map.put("cookie", cookie);
        map.put("_tb_token_", _tb_token_);

        String lastDownloadSussTime;
        if (AlimamaCookie.lastDownloadSussTime == null) {
            lastDownloadSussTime = "还未进行过一次下载";
        } else {
            lastDownloadSussTime = df.format(AlimamaCookie.lastDownloadSussTime);
        }
        map.put("lastDownloadSussTime", lastDownloadSussTime);
        map.put("xlsToDbProcessSuccNum", AlimamaCookie.xlsToDbProcessSuccNum);
        map.put("xlsToDbProcessErrorNum", AlimamaCookie.xlsToDbProcessErrorNum);
        map.put("applyDxSuccNum", AlimamaCookie.applyDxSuccNum);
        map.put("applyHdSuccNum", AlimamaCookie.applyHdSuccNum);
        map.put("downloadInfo", AlimamaCookie.downloadInfo);
        map.put("tryLoginNum", AlimamaCookie.tryLoginNum);

        return map;
    }
}
