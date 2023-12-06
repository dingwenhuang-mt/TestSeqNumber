package com.example.springbootmall0726.dao.impl;

import com.example.springbootmall0726.dao.SeqNumberDao;
import com.example.springbootmall0726.model.SeqNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class SeqNumberDaoImpl implements SeqNumberDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public int getNextSerialNumberByCompanyKey(int companyKey) {
        StringBuilder _sql = new StringBuilder();
        _sql.append("SELECT next_serial_number \n");
        _sql.append("FROM test_issue_number_rule \n");
        _sql.append("WHERE company_key = :companyKey \n");

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("companyKey", companyKey);
        return namedParameterJdbcTemplate.queryForObject(_sql.toString(),params, Integer.class);
    }

    @Override
    public void updateSerialNumberByCompanyKey(int nextSerialNumber, int companyKey) {
        StringBuilder _sql = new StringBuilder();
        _sql.append("UPDATE test_issue_number_rule \n");
        _sql.append("SET next_serial_number = :nextSerialNumber \n");
        _sql.append("WHERE company_key = :companyKey");

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nextSerialNumber", nextSerialNumber)
                .addValue("companyKey", companyKey);

        Objects.requireNonNull(namedParameterJdbcTemplate).update(_sql.toString(), params);
    }

    @Override
    public void createDoc(int companyKey, String docNumber) {
        StringBuilder _sql = new StringBuilder();
        _sql.append("INSERT INTO test_doc_delivery (company_key, doc_number) \n");
        _sql.append("VALUES (:companyKey, :docNumber)");
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("companyKey", companyKey)
                .addValue("docNumber", docNumber);
        Objects.requireNonNull(namedParameterJdbcTemplate).update(_sql.toString(), params);
    }
}
