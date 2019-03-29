package com.anandniketanbhadaj.skool360student.Models.FeesResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeesFinalResponse {
    @SerializedName("LedgerName")
    @Expose
    private String ledgerName;
    @SerializedName("Term1Amt")
    @Expose
    private Double term1Amt;
    @SerializedName("Term2Amt")
    @Expose
    private Double term2Amt;

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public Double getTerm1Amt() {
        return term1Amt;
    }

    public void setTerm1Amt(Double term1Amt) {
        this.term1Amt = term1Amt;
    }

    public Double getTerm2Amt() {
        return term2Amt;
    }

    public void setTerm2Amt(Double term2Amt) {
        this.term2Amt = term2Amt;
    }

}
