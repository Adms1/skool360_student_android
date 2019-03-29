package com.anandniketanbhadaj.skool360student.Models.Suggestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnlineTransactionModel {
    @SerializedName("PaymentID")
    @Expose
    private String paymentID;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("TransactionStatus")
    @Expose
    private String transactionStatus;
    @SerializedName("SchoolStatus")
    @Expose
    private String schoolStatus;

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getSchoolStatus() {
        return schoolStatus;
    }

    public void setSchoolStatus(String schoolStatus) {
        this.schoolStatus = schoolStatus;
    }

}
