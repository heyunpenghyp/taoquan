package com.sb.taoquan.dao.base;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tytx02 on 2016/4/26.
 */
public class BaseDao extends SqlSessionDaoSupport {
    @Resource(name = "sqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    private static int batchDealNum = 100;

    private SqlSession batchSession;

    @PostConstruct
    public void SqlSessionFactory() {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public int batchInsert(String statement, List<?> list) {
        batchSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int i = 0;
        for(int cnt = list.size(); i < cnt; i++) {
            batchSession.insert(statement, list.get(i));
            if((i + 1) % batchDealNum == 0) {//Constants.BATCH_DEAL_NUM为批量提交的条数
                batchSession.flushStatements();
            }
        }
        batchSession.flushStatements();
        batchSession.close();
        return i;
    }

    public int batchUpdate(String statement, List<?> list) {
        batchSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int i = 0;
        for(int cnt = list.size(); i < cnt; i++) {
            batchSession.update(statement, list.get(i));
            if((i + 1) % batchDealNum == 0) {
                batchSession.flushStatements();
            }
        }
        batchSession.flushStatements();
        batchSession.close();
        return i;
    }

    public int batchDelete(String statement, List<?> list) {
        batchSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int i = 0;
        for(int cnt = list.size(); i < cnt; i++) {
            batchSession.delete(statement, list.get(i));
            if((i + 1) % batchDealNum == 0) {
                batchSession.flushStatements();
            }
        }
        batchSession.flushStatements();
        batchSession.close();
        return i;
    }
}
