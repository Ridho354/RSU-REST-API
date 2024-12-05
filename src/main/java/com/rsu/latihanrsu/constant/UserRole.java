package com.rsu.latihanrsu.constant;

public enum UserRole {
    ROLE_PATIENT("PASIEN"),
    ROLE_STAFF("KARYAWAN"),
    ROLE_SUPER_ADMIN("SUPERADMIN");


    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public static UserRole fromValue(String value) {
        for (UserRole userRole : values()) {
            if (userRole.value.equalsIgnoreCase(value)) {
                return userRole;
            }
        }
        return null;
    }
}
