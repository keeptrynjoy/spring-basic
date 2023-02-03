package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TxService2 {
    @Autowired
    B1dao b1dao;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insertB1WithTx() throws Exception {
        b1dao.insert(1, 100);//성공
        b1dao.insert(1, 200);//실패
    }

    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void insertB1WithTx2() throws Exception {
        b1dao.insert(1, 300);//성공
        b1dao.insert(1, 400);//실패
    }
}
