package com.rsu.latihanrsu.constant;

public enum MedicinePayment {
    DRAFT("DRAFT"),
    PENDING("PROSES"),
    PAID("DIBAYAR"),
    BPJS("BPJS"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED");
    
    
    private final String value;
    
    MedicinePayment(String value) {
        this.value = value;
    }

    public static MedicinePayment fromValue(String value) {
        for (MedicinePayment MedicinePayment : values()) {
            if (MedicinePayment.value.equalsIgnoreCase(value)) {
                return MedicinePayment;
            }
        }
        return null;
    }
    
}
