package com.sb.taoquan.alimama.simulation;

import com.sb.taoquan.dao.ICategoryMapDao;
import com.sb.taoquan.dao.IQuanProductDao;
import com.sb.taoquan.entity.CategoryMap;
import com.sb.taoquan.entity.QuanProduct;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 小平 on 2017/1/29.
 */
@Component
public class XlsQuanToDb implements InitializingBean {
    @Autowired
    private ICategoryMapDao categoryMapDao;

    @Autowired
    private IQuanProductDao quanProductDao;
    private static String qqhd2V1Url = "https://uland.taobao.com/coupon/edetail?activityId={activityId}&pid=" + Constants.webPid + "&itemId={itemId}&src=njrt_qytk&dx=0";
    public  void xlsQuanToDb() throws IOException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String xlsPath = "C:\\quan\\" +  df.format(new Date()) + ".xls";
        InputStream is = null;
        HSSFWorkbook hssfWorkbook = null;
        try {
            is = new FileInputStream(xlsPath);
            hssfWorkbook = new HSSFWorkbook(is);
            // 获取每一个工作薄
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 获取当前工作薄的每一行
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        try {
                            String productId = getValue(hssfRow.getCell(0));
                            String productName =  getValue(hssfRow.getCell(1));
                            String imageUrl = getValue(hssfRow.getCell(2));
                            String itemUrl = getValue(hssfRow.getCell(3));
                            String category = getValue(hssfRow.getCell(4));
                            double price = Double.parseDouble(getValue(hssfRow.getCell(6)));
                            String soldNum = getValue(hssfRow.getCell(7));
                            double commissionRebate = Double.parseDouble(getValue(hssfRow.getCell(8)));
                            String commission = getValue(hssfRow.getCell(9));
                            String sellId = getValue(hssfRow.getCell(11));
                            String shopName = getValue(hssfRow.getCell(12));
                            String platName = getValue(hssfRow.getCell(13));
                            String quanId = getValue(hssfRow.getCell(14));
                            String quanTotalNum = getValue(hssfRow.getCell(15));
                            String quanInfo = getValue(hssfRow.getCell(17));
                            String quanBeginDate = getValue(hssfRow.getCell(18));
                            String quanEndDate = getValue(hssfRow.getCell(19));
                            String quanUrl = "http://shop.m.taobao.com/shop/coupon.htm?seller_id="
                                + sellId + "&activity_id=" + quanId;
                            QuanProduct quanProduct = new QuanProduct();
                            quanProduct.setProductId(productId);
                            quanProduct.setProductName(productName);
                            quanProduct.setImageUrl(imageUrl);
                            quanProduct.setItemUrl(itemUrl);
                            quanProduct.setCategory(convertOrgCat(category));
                            quanProduct.setPrice(price);
                            try {
                                quanProduct.setSoldNum(Integer.parseInt(soldNum));
                            } catch (Exception e) {
                            }
                            quanProduct.setCommissionRebate(commissionRebate);
                            quanProduct.setCommission(commission);
                            quanProduct.setSellId(sellId);
                            quanProduct.setShopName(shopName);
                            quanProduct.setPlatName(platName);
                            quanProduct.setQuanId(quanId);
                            try {
                                quanProduct.setQuanTotalNum(Integer.parseInt(quanTotalNum));
                            } catch (Exception e) {
                            }
                            quanProduct.setQuanInfo(quanInfo);
                            quanProduct.setQuanBeginDate(df.parse(quanBeginDate));
                            quanProduct.setQuanEndDate(df.parse(quanEndDate));
                            quanProduct.setQuanUrl(quanUrl);
                            quanProduct.setQuanWeight(calculatePriceWeight(quanInfo, price));
                            quanProduct.setTransfer(2);
                            //默认全部为未申请定向的高佣2合1链接
                            String composeUrl = qqhd2V1Url.replace("{activityId}", quanId).replace("{itemId}", productId);
                            quanProduct.setComposeUrl(composeUrl);
                            quanProductDao.save(quanProduct);
                            AlimamaCookie.xlsToDbProcessSuccNum++;
                        } catch (Exception e) {
                            e.printStackTrace();
                            AlimamaCookie.xlsToDbProcessErrorNum++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            hssfWorkbook.close();
            is.close();
        }
        if (AlimamaCookie.xlsToDbProcessSuccNum  > 1000) {
            quanProductDao.deleteDownloadRecord();
            quanProductDao.insertDownloadRecord();
            AlimamaCookie.lastDownloadSussTime = new Date();
        }
    }

    // 转换数据格式
    public static String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    private static long calculatePriceWeight(String quanInfo, double price) {
        if (quanInfo.contains("无条件")) { //5元无条件券
            Pattern pattern = Pattern.compile("([0-9]*)");
            Matcher matcher = pattern.matcher(quanInfo);
            int quanPrice = 0;
            while(matcher.find()) {
                quanPrice = Integer.parseInt(matcher.group(1));//得到第0组——整个匹配
                break;
            }
            return Math.round(quanPrice / price * 100);
        } else if (quanInfo.startsWith("满")) { //满15元减5元
            int manPrice = 0;
            int jianPrice = 0;
            Pattern pattern = Pattern.compile("满([0-9]*)");
            Matcher matcher = pattern.matcher(quanInfo);
            while(matcher.find()) {
                manPrice = Integer.parseInt(matcher.group(1));//得到第0组——整个匹配
                break;
            }
            pattern = Pattern.compile("减([0-9]*)");
            matcher = pattern.matcher(quanInfo);
            while(matcher.find()) {
                jianPrice = Integer.parseInt(matcher.group(1));//得到第0组——整个匹配
                break;
            }
            if (price >= manPrice) {
                return Math.round(jianPrice / price * 100);
            }
        }
        return 0;
    }

    private static Map<String, List<String>> categoryAllMap = new HashMap<String, List<String>>();
    private static long lastFreshTime = (new Date()).getTime();
    private static long freshInterval = 1000 * 60 * 1;
    private  String convertOrgCat(String category) {
        long now = (new Date()).getTime();
        if (now - lastFreshTime > freshInterval) {
            lastFreshTime = now;
            List<CategoryMap> categoryMapList = categoryMapDao.getAll();
            for (CategoryMap categoryMap: categoryMapList) {
                categoryAllMap.put(categoryMap.getCategory(), Arrays.asList(categoryMap.getCategoryNameCollection().split(",")));
            }
        }
        Iterator<Map.Entry<String, List<String>>> it = categoryAllMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            List<String> value = entry.getValue();
            String key = entry.getKey();
            for (String cat : value) {
                if (category.contains(cat)) {
                    category = key;
                    break;
                }
            }
        }
        return category;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<CategoryMap> categoryMapList = categoryMapDao.getAll();
        for (CategoryMap categoryMap: categoryMapList) {
            categoryAllMap.put(categoryMap.getCategory(), Arrays.asList(categoryMap.getCategoryNameCollection().split(",")));
        }
    }
}
