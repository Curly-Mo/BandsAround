package com.curlymo.bandsaround.songkick;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ResultsPage implements Serializable{
    
    String status;
    long perPage;
    long page;
    long totalEntries;
    ClientLocation clientLocation;
    Results results;
    
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public long getPerPage() {
        return perPage;
    }
    public void setPerPage(long perPage) {
        this.perPage = perPage;
    }
    public long getPage() {
        return page;
    }
    public void setPage(long page) {
        this.page = page;
    }
    public long getTotalEntries() {
        return totalEntries;
    }
    public void setTotalEntries(long totalEntries) {
        this.totalEntries = totalEntries;
    }
    public ClientLocation getClientLocation() {
        return clientLocation;
    }
    public void setClientLocation(ClientLocation clientLocation) {
        this.clientLocation = clientLocation;
    }
    public Results getResults() {
        return results;
    }
    public void setResults(Results results) {
        this.results = results;
    }
    
    
}
