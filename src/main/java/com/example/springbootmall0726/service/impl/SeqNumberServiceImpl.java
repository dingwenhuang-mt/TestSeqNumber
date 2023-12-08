package com.example.springbootmall0726.service.impl;

import com.example.springbootmall0726.dao.SeqNumberDao;
import com.example.springbootmall0726.service.ProductService;
import com.example.springbootmall0726.service.SeqNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class SeqNumberServiceImpl implements SeqNumberService {

    @Autowired
    SeqNumberDao seqNumberDao;

    private static final Logger log = LoggerFactory.getLogger(SeqNumberService.class);

    private final Map<Integer, ReentrantLock> lockersByCompany = new ConcurrentHashMap<>();

    private ReentrantLock getLockerByCompany(int companyKey) {
        return lockersByCompany.computeIfAbsent(companyKey, k -> new ReentrantLock());
    }
    @Override
    @Transactional
    public void doTestSeqNumber(int companyKey) {

        ReentrantLock locker = getLockerByCompany(companyKey);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            public void afterCommit() {
                if(locker.getHoldCount() > 0)
                    locker.unlock();
            }

        });

        locker.lock();

        try {
            int nextSerialNumber = seqNumberDao.getNextSerialNumberByCompanyKey(companyKey);
            log.info("公司 {} ，取得的下一個流水號: {}",companyKey, nextSerialNumber);

            String docNumber = "Seq number: " + nextSerialNumber;

            double randomNumber = Math.random();
            if (randomNumber < 0.3) {
                // unlock()可以在這裡
//                locker.unlock();
                throw new RuntimeException();
            }

            seqNumberDao.createDoc(companyKey, docNumber);

            int newNextSerialNumber = nextSerialNumber + 1;

            seqNumberDao.updateSerialNumberByCompanyKey(newNextSerialNumber, companyKey);
            log.info("公司 {} ，儲存後更新的下一個流水號: {}",companyKey, newNextSerialNumber);
        } catch (Exception e) {
            // unlock()也可以在這裡
//            locker.unlock();
            throw e;
        }
    }
}
