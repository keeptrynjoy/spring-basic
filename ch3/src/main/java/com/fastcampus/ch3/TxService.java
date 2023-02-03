package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TxService {
    @Autowired A1dao a1dao;
    @Autowired TxService2 txService2;

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertA1WithTx() throws Exception{
        a1dao.insert(1,100);//성공
        txService2.insertB1WithTx();
        a1dao.insert(2,200);//성공
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertA1WithTx2() throws Exception{
        a1dao.insert(1,300);//성공
        a1dao.insert(2,400);//성공
        txService2.insertB1WithTx2();
    }

    public void insertA1WithoutTx() throws Exception{
        a1dao.insert(1,100);
        a1dao.insert(1,200);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertA1WithTxFail() throws Exception{
        a1dao.insert(1,100);
        a1dao.insert(1,200);
    }

    @Transactional
    public void insertA1WithTxSuccess() throws Exception{
        a1dao.insert(1,100);
        a1dao.insert(2,200);
    }
}

