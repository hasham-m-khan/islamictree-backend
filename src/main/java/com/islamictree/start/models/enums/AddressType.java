package com.islamictree.start.models.enums;

public enum AddressType {
    RESIDENTIAL, BUSINESS, PO_BOX, OTHER;

    @Override
    public String toString() {
        return this.name();
    }

    public static AddressType fromString(String value) {
        for (AddressType type : AddressType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown AddressType: " + value);
    }
}
