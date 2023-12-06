package com.example.springbootmall0726.model;

public class SeqNumber {

    private Integer id;

    private Integer company_key;

    private Integer next_serial_number;

    private String doc_number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompany_key() {
        return company_key;
    }

    public void setCompany_key(Integer company_key) {
        this.company_key = company_key;
    }

    public Integer getNext_serial_number() {
        return next_serial_number;
    }

    public void setNext_serial_number(Integer next_serial_number) {
        this.next_serial_number = next_serial_number;
    }

    public String getDoc_number() {
        return doc_number;
    }

    public void setDoc_number(String doc_number) {
        this.doc_number = doc_number;
    }
}
