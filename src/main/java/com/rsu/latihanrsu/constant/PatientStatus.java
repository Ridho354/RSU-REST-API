package com.rsu.latihanrsu.constant;

public enum PatientStatus {
    MANDIRI("Mandiri"),
    BPJS("BPJS");
    
    private final String value;
    
    PatientStatus(String value) {
        this.value = value;
    }

    public static PatientStatus fromValue(String value) {
        for (PatientStatus patientStatus : values()) {
            if (patientStatus.value.equalsIgnoreCase(value)) {
                return patientStatus;
            }
        }
        return null;
    }
    
}
