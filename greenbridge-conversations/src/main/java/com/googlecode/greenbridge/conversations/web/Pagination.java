/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.web;

/**
 *
 * @author ryan
 */
public class Pagination {

    private int lastPage;
    private int totalResults;
    private int page;
    private int resultsPerPage;

    public Pagination(int page, int resultsPerPage, int totalResults) {
        this.totalResults = totalResults;
        this.resultsPerPage = resultsPerPage;
        this.lastPage = (int)Math.ceil(totalResults/resultsPerPage);
        if (page > lastPage) {
            page = lastPage;
        }
        if (page < 1) {
            page = 1;
        }
        this.page = page;
    }

    public int getResultsPageStartRange() {
        if (totalResults < resultsPerPage) {
            return 1;
        }
        else return (page * resultsPerPage) + 1;
    }

    public int getResultsPageEndRange() {
     
        int endRange =  (int) (page * resultsPerPage) + resultsPerPage;
        if (endRange > totalResults) {
            int total = page * resultsPerPage;
            if (total > totalResults) total = 0;
            endRange = totalResults - total;
        }
        return endRange;
    }



    public int getPreviousPage() {
        if (page == 1) {
            return page;
        }
        else return page -1;
    }

    public int getNextPage() {
        if (page == lastPage) {
            return lastPage;
        }
        else return page + 1;
    }


    /**
     * @return the lastPage
     */
    public int getLastPage() {
        return lastPage;
    }

    /**
     * @param lastPage the lastPage to set
     */
    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    /**
     * @return the totalResults
     */
    public int getTotalResults() {
        return totalResults;
    }

    /**
     * @param totalResults the totalResults to set
     */
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return the resultsPerPage
     */
    public int getResultsPerPage() {
        return resultsPerPage;
    }

    /**
     * @param resultsPerPage the resultsPerPage to set
     */
    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }
}
