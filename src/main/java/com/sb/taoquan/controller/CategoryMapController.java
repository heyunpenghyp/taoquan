package com.sb.taoquan.controller;

import com.sb.taoquan.dao.ICategoryMapDao;
import com.sb.taoquan.entity.CategoryMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 小平 on 2017/1/30.
 */
@Controller
@RequestMapping("/categoryMap")
public class CategoryMapController {
    @Autowired
    private ICategoryMapDao categoryMapDao;
    @RequestMapping(value = "/manager",method = RequestMethod.GET)
    public String manager(Model model){
        return "pc/categoryMap/categoryMap";
    }

    @ResponseBody
    @RequestMapping(value = "/generateCategoryNameCollection",method = RequestMethod.POST)
    public CategoryMap generateCategoryNameCollection(@RequestParam("category") String category) {
        CategoryMap categoryMapParam = new CategoryMap();
        categoryMapParam.setCategory(category);
        CategoryMap categoryMap = categoryMapDao.getCategoryNameCollectionByCategoryName(categoryMapParam);
        return categoryMap;
    }

    @ResponseBody
    @RequestMapping(value = "/updateCategoryMap",method = RequestMethod.POST)
    public CategoryMap updateCategoryMap(@RequestParam("category") String category, @RequestParam("categoryNameCollection") String categoryNameCollection) {
        CategoryMap categoryMapParam = new CategoryMap();
        categoryMapParam.setCategory(category);
        categoryMapParam.setCategoryNameCollection(categoryNameCollection);
        categoryMapDao.updateCategoryMap(categoryMapParam);
        return categoryMapParam;
    }

}
