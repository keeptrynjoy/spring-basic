package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class A1daoTest {
    @Autowired
    A1dao a1dao;
    
    @Autowired
    DataSource ds;

    @Autowired
    DataSourceTransactionManager tm;
    
    @Test
    public void insertTest() {
        //TxManager를 생성
//        PlatformTransactionManager tm = new DataSourceTransactionManager(ds);
        TransactionStatus status = tm.getTransaction(new DefaultTransactionDefinition());
        // Tx 시작
        try {
            a1dao.deleteAll();
            a1dao.insert(1,100);
            a1dao.insert(2,200);
            tm.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            tm.rollback(status);
        } finally {

        }
    }
}