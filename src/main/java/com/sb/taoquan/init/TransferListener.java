package com.sb.taoquan.init;

import com.sb.taoquan.alimama.simulation.AlimamaCookie;
import com.sb.taoquan.alimama.simulation.DingxiangApplySimulation;
import com.sb.taoquan.alimama.simulation.LoginSimulation;
import com.sb.taoquan.alimama.simulation.QQHDApplySimulation;
import com.sb.taoquan.dao.IQuanProductDao;
import com.sb.taoquan.entity.QuanProduct;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 小平 on 2017/1/30.
 */
//@Component
//@DependsOn("autoLoginListener")
public class TransferListener  implements InitializingBean {
    @Autowired
    private IQuanProductDao quanProductDao;
    @Override
    public void afterPropertiesSet() throws Exception {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (! AlimamaCookie.isLoginSucc) {
                        System.out.println("-----cookie失效,暂停转链,需要人工护航-----");
                        try {
                            Thread.sleep(1000 * 60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    List<QuanProduct> quanProductList = quanProductDao.getTop10WeightToTransfer();
                    if (CollectionUtils.isEmpty(quanProductList)) {
                        System.out.println("----还没有需要转链的商品，等待一分钟继续-----");
                        try {
                            Thread.sleep(1000 * 60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        for (QuanProduct quanProduct : quanProductList) {
                            try {
                                boolean isHd = QQHDApplySimulation.isHd(quanProduct);
                                if (!isHd) {
                                    DingxiangApplySimulation.apply(quanProduct);
                                    AlimamaCookie.applyDxSuccNum++;
                                }  else {
                                    AlimamaCookie.applyHdSuccNum++;
                                }
                            } catch (Exception e) {
                                AlimamaCookie.transferErrorNum++;
                                System.out.println("-----转链失败" + quanProduct.getItemUrl() + "-------");
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
        new Thread(runnable).start();

    }
}
