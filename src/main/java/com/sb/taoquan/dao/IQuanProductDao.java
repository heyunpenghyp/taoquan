package com.sb.taoquan.dao;

import com.sb.taoquan.entity.QuanProduct;

import java.util.Date;
import java.util.List;

/**
 * Created by 小平 on 2017/1/29.
 */
public interface IQuanProductDao {
    public void save(QuanProduct quanProduct);

    public List<QuanProduct> getTop10WeightToTransfer();

    public void updateDXTransfer(QuanProduct quanProduct);

    public void updateQqhdTransfer(QuanProduct quanProduct);

    public void updateNoDxTransfer(QuanProduct quanProduct);

    public void deleteInvalid(QuanProduct quanProduct);

    public List<QuanProduct> getTop100ToCheck(QuanProduct quanProduct);

    public void insertDownloadRecord();

    public void deleteDownloadRecord();

    public Date getXlsToDbDay();


}
