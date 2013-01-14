package com.curlymo.bandsaround.geocoder;

import java.util.List;

/**
 * @author <a href="mailto:panchmp@gmail.com">Michael Panchenko</a>
 */
public class GeocoderResponseType {
    protected GeocoderStatus status;
    protected List<GeocoderResult> results;

    public GeocoderResponseType() {
    }

    public GeocoderStatus getStatus() {
        return status;
    }

    public void setStatus(GeocoderStatus status) {
        this.status = status;
    }

    public List<GeocoderResult> getResults() {
        return results;
    }

    public void setResults(List<GeocoderResult> result) {
        this.results = result;
    }
}

