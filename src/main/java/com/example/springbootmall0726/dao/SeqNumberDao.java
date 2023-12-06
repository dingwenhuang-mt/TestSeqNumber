package com.example.springbootmall0726.dao;

public interface SeqNumberDao {

    int getNextSerialNumberByCompanyKey(int companyKey);

    void updateSerialNumberByCompanyKey(int nextSerialNumber, int companyKey);

    void createDoc(int companyKey, String docNumber);
}
