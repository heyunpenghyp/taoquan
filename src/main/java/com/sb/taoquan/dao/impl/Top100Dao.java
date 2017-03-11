package com.sb.taoquan.dao.impl;

import com.sb.taoquan.dao.ITop100Dao;
import com.sb.taoquan.dao.base.BaseDao;
import com.sb.taoquan.entity.Top100;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
@Repository("top100Dao")
public class Top100Dao extends BaseDao implements ITop100Dao {
    @Override
    public void save(Top100 top100) {
        this.getSqlSession().insert("top100.save", top100);
    }

    @Override
    public List<Top100> query() {
        return this.getSqlSession().selectList("top100.query");
    }

    @Override
    public void del(int id) {
        this.getSqlSession().delete("top100.del", id);
    }


}
