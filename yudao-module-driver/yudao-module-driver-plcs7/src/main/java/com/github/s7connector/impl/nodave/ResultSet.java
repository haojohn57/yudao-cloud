package com.github.s7connector.impl.nodave;

/**
 * @author Thomas Hergenhahn
 */
public final class ResultSet {
    private int errorState, numResults;
    public Result[] results;

    public int getErrorState() {
        return this.errorState;
    }

    public int getNumResults() {
        return this.numResults;
    }

    ;

    public void setErrorState(final int error) {
        this.errorState = error;
    }

    public void setNumResults(final int nr) {
        this.numResults = nr;
    }

    ;
}
