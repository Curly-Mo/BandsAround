/**
 * $Revision$
 * $Date$
 * $Author$
 * 
 * $Log$
 *
 *
 * (c) 2008 Future Platforms
 * 
 */ 
package com.curlymo.bandsaround.songkick.api.helper;

import com.curlymo.bandsaround.songkick.api.objects.ClientLocation;

public class EventResultsPageContents extends ResultsPageContents {

	private EventResults results;
	private int totalEntries;
	private int perPage;
	private int page;
	private ClientLocation clientLocation;
	
	public Results getResults() {
		return results;
	}
	public void setResults(EventResults results) {
		this.results = results;
	}
	public int getTotalEntries() {
		return totalEntries;
	}
	public void setTotalEntries(int totalEntries) {
		this.totalEntries = totalEntries;
	}
	public int getPerPage() {
		return perPage;
	}
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
        public ClientLocation getClientLocation() {
            return clientLocation;
        }
        public void setClientLocation(ClientLocation clientLocation) {
            this.clientLocation = clientLocation;
        }	
	
	
}
