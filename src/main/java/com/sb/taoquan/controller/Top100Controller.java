package com.sb.taoquan.controller;

import com.sb.taoquan.dao.ICategoryMapDao;
import com.sb.taoquan.dao.ITop100Dao;
import com.sb.taoquan.entity.CategoryMap;
import com.sb.taoquan.entity.Top100;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
@Controller
@RequestMapping("/top100")
public class Top100Controller {
    @Autowired
    private ITop100Dao top100Dao;
    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Map upload(@RequestParam("picUrl") String picUrl, @RequestParam("title") String title, @RequestParam("quanAmount") int quanAmount
            , @RequestParam("price") double price, @RequestParam("soldNum") int soldNum,  @RequestParam("quanUrl") String quanUrl, @RequestParam("tkl") String tkl) {
        Map result = new HashMap();
        result.put("status", 200);
        Top100 top100 = new Top100();
        top100.setPicUrl(picUrl);
        top100.setTitle(title);
        top100.setQuanAmount(quanAmount);
        top100.setPrice(price);
        top100.setSoldNum(soldNum);
        top100.setTkl(tkl);
        top100.setQuanUrl(quanUrl);
        top100Dao.save(top100);
        return result;
    }

    @RequestMapping(value = "/preSave",method = RequestMethod.GET)
    public String manager(Model model){
        return "pc/top100/save";
    }


}
