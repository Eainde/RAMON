package com.db.ramon.constant;

public enum FileHeaderEnum {
    ORDER_ID("Order"),
    LEVEL("Level"),
    CODE("Code"),
    PARENT("Parent"),
    DESCRIPTION("Description"),
    ITEM_INCLUDES("This item includes"),
    ITEM_ALSO_INCLUDES("This item also includes"),
    RULINGS("Rulings"),
    ITEM_EXCLUDES("This item excludes"),
    REF_TO_ISIC_REV_4("Reference to ISIC Rev. 4");

    private final String number;

    FileHeaderEnum(final String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number;
    }
}
