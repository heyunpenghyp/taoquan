package com.sb.taoquan.dao.impl;

import com.sb.taoquan.dao.ICategoryMapDao;
import com.sb.taoquan.dao.base.BaseDao;
import com.sb.taoquan.entity.CategoryMap;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 小平 on 2017/1/30.
 */
@Repository("categoryMapDao")
public class CategoryMapDao extends BaseDao implements ICategoryMapDao {
    @Override
    public CategoryMap getCategoryNameCollectionByCategoryName(CategoryMap categoryMap) {
        return this.getSqlSession().selectOne("categoryMap.getCategoryNameCollectionByCategoryName", categoryMap);
    }

    @Override
    public void updateCategoryMap(CategoryMap categoryMap) {
        this.getSqlSession().update("categoryMap.updateCategoryMap", categoryMap);
    }

    @Override
    public List<CategoryMap> getAll() {
        return this.getSqlSession().selectList("categoryMap.getAll");
    }
}
