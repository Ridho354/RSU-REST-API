package com.rsu.latihanrsu.constant;

public enum OutpatientStatus {
    REGISTER("Register"),
    PAID("Paid"),
    FINGERPRINT("Fingerprint"),
    DONE("Done"),
    CANCEL("Cancel");
    
    private final String value;
    
    OutpatientStatus(String value) {
        this.value = value;
    }

    public static OutpatientStatus fromValue(String value) {
        for (OutpatientStatus outpatientStatus : values()) {
            if (outpatientStatus.value.equalsIgnoreCase(value)) {
                return outpatientStatus;
            }
        }
        return null;
    }
    
}
