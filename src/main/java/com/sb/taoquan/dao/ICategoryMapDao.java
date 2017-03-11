package com.sb.taoquan.dao;

import com.sb.taoquan.entity.CategoryMap;

import java.util.List;

/**
 * Created by 小平 on 2017/1/30.
 */
public interface ICategoryMapDao {
    public CategoryMap getCategoryNameCollectionByCategoryName(CategoryMap categoryMap);

    public void updateCategoryMap(CategoryMap categoryMap);

    public List<CategoryMap> getAll();
}
