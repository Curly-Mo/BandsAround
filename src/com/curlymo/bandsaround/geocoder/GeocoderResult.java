package com.curlymo.bandsaround.geocoder;

import java.util.List;

public class GeocoderResult {
    protected List<String> types;
    protected String formattedAddress;
    protected List<GeocoderAddressComponent> addressComponents;
    protected GeocoderGeometry geometry;

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public List<GeocoderAddressComponent> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List<GeocoderAddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }

    public GeocoderGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(GeocoderGeometry geometry) {
        this.geometry = geometry;
    }
}