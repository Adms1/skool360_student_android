package com.anandniketanbhadaj.skool360student.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeesModel {

        @SerializedName("Success")
        @Expose
        private String success;
        @SerializedName("Term1Btn")
        @Expose
        private Boolean term1Btn;
        @SerializedName("Term2Btn")
        @Expose
        private Boolean term2Btn;
        @SerializedName("Term1URL")
        @Expose
        private String term1URL;
        @SerializedName("Term2URL")
        @Expose
        private String term2URL;
        @SerializedName("Term1Msg")
        @Expose
        private String term1Msg;
        @SerializedName("Term2Msg")
        @Expose
        private String term2Msg;
        @SerializedName("TermTotal")
        @Expose
        private String termTotal;
        @SerializedName("TermPaid")
        @Expose
        private String termPaid;
        @SerializedName("TermDuePay")
        @Expose
        private String termDuePay;
        @SerializedName("TermDiscount")
        @Expose
        private String termDiscount;
        @SerializedName("FinalArray")
        @Expose
        private List<FinalArray> finalArray = null;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public Boolean getTerm1Btn() {
            return term1Btn;
        }

        public void setTerm1Btn(Boolean term1Btn) {
            this.term1Btn = term1Btn;
        }

        public Boolean getTerm2Btn() {
            return term2Btn;
        }

        public void setTerm2Btn(Boolean term2Btn) {
            this.term2Btn = term2Btn;
        }

        public String getTerm1URL() {
            return term1URL;
        }

        public void setTerm1URL(String term1URL) {
            this.term1URL = term1URL;
        }

        public String getTerm2URL() {
            return term2URL;
        }

        public void setTerm2URL(String term2URL) {
            this.term2URL = term2URL;
        }

        public String getTerm1Msg() {
            return term1Msg;
        }

        public void setTerm1Msg(String term1Msg) {
            this.term1Msg = term1Msg;
        }

        public String getTerm2Msg() {
            return term2Msg;
        }

        public void setTerm2Msg(String term2Msg) {
            this.term2Msg = term2Msg;
        }

        public String getTermTotal() {
            return termTotal;
        }

        public void setTermTotal(String termTotal) {
            this.termTotal = termTotal;
        }

        public String getTermPaid() {
            return termPaid;
        }

        public void setTermPaid(String termPaid) {
            this.termPaid = termPaid;
        }

        public String getTermDuePay() {
            return termDuePay;
        }

        public void setTermDuePay(String termDuePay) {
            this.termDuePay = termDuePay;
        }

        public String getTermDiscount() {
            return termDiscount;
        }

        public void setTermDiscount(String termDiscount) {
            this.termDiscount = termDiscount;
        }

        public List<FinalArray> getFinalArray() {
            return finalArray;
        }

        public void setFinalArray(List<FinalArray> finalArray) {
            this.finalArray = finalArray;
        }



    public class FinalArray {

        @SerializedName("LedgerName")
        @Expose
        private String ledgerName;
        @SerializedName("Term1Amt")
        @Expose
        private String term1Amt;
        @SerializedName("Term2Amt")
        @Expose
        private String term2Amt;

        public String getLedgerName() {
            return ledgerName;
        }

        public void setLedgerName(String ledgerName) {
            this.ledgerName = ledgerName;
        }

        public String getTerm1Amt() {
            return term1Amt;
        }

        public void setTerm1Amt(String term1Amt) {
            this.term1Amt = term1Amt;
        }

        public String getTerm2Amt() {
            return term2Amt;
        }

        public void setTerm2Amt(String term2Amt) {
            this.term2Amt = term2Amt;
        }

    }

}
