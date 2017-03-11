package com.sb.taoquan.controller;

import com.sb.taoquan.dao.ITop100Dao;
import com.sb.taoquan.entity.Top100;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
@Controller
@RequestMapping("/m")
public class MobileController {
    @Autowired
    private ITop100Dao top100Dao;
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        List<Top100> list = top100Dao.query();
        model.addAttribute("productList", list);
        return "m/index";
    }
}
