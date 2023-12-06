package com.example.springbootmall0726.rowmapper;

import com.example.springbootmall0726.model.SeqNumber;
import com.example.springbootmall0726.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeqNumberRowMapper implements RowMapper<SeqNumber> {
    @Override
    public SeqNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
        SeqNumber seqNumber = new SeqNumber();

        seqNumber.setId(rs.getInt("id"));
        seqNumber.setCompany_key(rs.getInt("company_key"));
        seqNumber.setNext_serial_number(rs.getInt("next_serial_number"));
        seqNumber.setDoc_number(rs.getString("doc_number"));

        return seqNumber;
    }
}