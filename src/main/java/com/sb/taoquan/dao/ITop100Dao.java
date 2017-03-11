package com.sb.taoquan.dao;

import com.sb.taoquan.entity.Top100;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
public interface ITop100Dao {
    public void save(Top100 top100);

    public List<Top100> query();

    public void del(int id);
}
