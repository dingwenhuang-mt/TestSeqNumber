package com.example.springbootmall0726.service.impl;

import com.example.springbootmall0726.dao.SeqNumberDao;
import com.example.springbootmall0726.service.ProductService;
import com.example.springbootmall0726.service.SeqNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SeqNumberServiceImpl implements SeqNumberService {

    @Autowired
    SeqNumberDao seqNumberDao;

    private static final Logger log = LoggerFactory.getLogger(SeqNumberService.class);

    private final Map<Integer, Object> lockersByCompany= new ConcurrentHashMap<>();

    private Object getLockerByCompany(int companyKey){
        return lockersByCompany.computeIfAbsent(companyKey, k -> new Object());
    }
    @Override
    @Transactional
    public void doTestSeqNumber(int companyKey) {
        synchronized (getLockerByCompany(companyKey)) {
            int nextSerialNumber = seqNumberDao.getNextSerialNumberByCompanyKey(companyKey);
            log.info("取得的下一個流水號: " + nextSerialNumber);

            String docNumber = "Seq number: " + nextSerialNumber;

            seqNumberDao.createDoc(companyKey, docNumber);

            int newNextSerialNumber = nextSerialNumber + 1;

            seqNumberDao.updateSerialNumberByCompanyKey(newNextSerialNumber, companyKey);
            log.info("儲存後更新的下一個流水號: " + newNextSerialNumber);
        }
    }
}
