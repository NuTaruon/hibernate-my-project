package org.example.domain;

public enum Rating {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");

    private String value;


    Rating(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Rating getRatingByValue(String value) {
        if (value == null) return null;
        for (Rating r : Rating.values()) {
            if (r.value.equals(value)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Unknown rating value: " + value);
    }
}
