package com.rsu.latihanrsu.dto;

public class MedicineTransactionDetailDTO {
    private String id_Medicine_transaction_detail;
    private String Medicine_name;
    private Long Medicine_price;
    private Integer quantity;

    public MedicineTransactionDetailDTO(String id_Medicine_transaction_detail, String Medicine_name, Long Medicine_price, Integer quantity) {
        this.id_Medicine_transaction_detail = id_Medicine_transaction_detail;
        this.Medicine_name = Medicine_name;
        this.Medicine_price = Medicine_price;
        this.quantity = quantity;
    }

    // Getter dan Setter
    public String getId_Medicine_transaction_detail() {
        return id_Medicine_transaction_detail;
    }

    public void setId_Medicine_transaction_detail(String id_Medicine_transaction_detail) {
        this.id_Medicine_transaction_detail = id_Medicine_transaction_detail;
    }

    public String getMedicine_name() {
        return Medicine_name;
    }

    public void setMedicine_name(String Medicine_name) {
        this.Medicine_name = Medicine_name;
    }

    public void setMedicine_price(Long Medicine_price) {
        this.Medicine_price = Medicine_price;
    }

    public Long getMedicine_price() {
        return Medicine_price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

