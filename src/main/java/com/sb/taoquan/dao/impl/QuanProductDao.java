package com.sb.taoquan.dao.impl;

import com.sb.taoquan.dao.IQuanProductDao;
import com.sb.taoquan.dao.base.BaseDao;
import com.sb.taoquan.entity.QuanProduct;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 小平 on 2017/1/29.
 */
@Repository("quanProductDao")
public class QuanProductDao extends BaseDao implements IQuanProductDao{
    @Override
    public void save(QuanProduct quanProduct) {
        this.getSqlSession().insert("quanProduct.save", quanProduct);
    }

    @Override
    public List<QuanProduct> getTop10WeightToTransfer() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(new Date());
        return this.getSqlSession().selectList("quanProduct.getTop10WeightToTransfer", dateStr + " 00:00:00");
    }

    @Override
    public void updateDXTransfer(QuanProduct quanProduct) {
        this.getSqlSession().update("quanProduct.updateDXTransfer", quanProduct);
    }

    @Override
    public void updateQqhdTransfer(QuanProduct quanProduct) {
        this.getSqlSession().update("quanProduct.updateQqhdTransfer", quanProduct);
    }

    @Override
    public void updateNoDxTransfer(QuanProduct quanProduct) {
        this.getSqlSession().update("quanProduct.updateNoDxTransfer", quanProduct);
    }

    @Override
    public void deleteInvalid(QuanProduct quanProduct) {
        this.getSqlSession().delete("quanProduct.deleteInvalid", quanProduct);
    }

    @Override
    public List<QuanProduct> getTop100ToCheck(QuanProduct quanProduct) {
        return this.getSqlSession().selectList("quanProduct.getTop100ToCheck", quanProduct);
    }

    @Override
    public void insertDownloadRecord() {
        this.getSqlSession().insert("quanProduct.insertDownloadRecord");
    }

    @Override
    public void deleteDownloadRecord() {
        this.getSqlSession().delete("quanProduct.deleteDownloadRecord");
    }

    @Override
    public Date getXlsToDbDay() {
        return this.getSqlSession().selectOne("quanProduct.getXlsToDbDay");
    }
}
