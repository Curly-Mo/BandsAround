package com.curlymo.bandsaround.geocoder;

/**
 * @author <a href="mailto:panchmp@gmail.com">Michael Panchenko</a>
 */
public enum GeocoderLocationType {
    APPROXIMATE,
    GEOMETRIC_CENTER,
    RANGE_INTERPOLATED,
    ROOFTOP;

    public String value() {
        return name();
    }

    public static GeocoderLocationType fromValue(String v) {
        return valueOf(v);
    }
}