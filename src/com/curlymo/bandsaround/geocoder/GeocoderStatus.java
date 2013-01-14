package com.curlymo.bandsaround.geocoder;

public enum GeocoderStatus {

    ERROR,
    INVALID_REQUEST,
    OK,
    OVER_QUERY_LIMIT,
    REQUEST_DENIED,
    UNKNOWN_ERROR,
    ZERO_RESULTS;

    public String value() {
        return name();
    }

    public static GeocoderStatus fromValue(String v) {
        return valueOf(v);
    }

}