package com.anandniketanbhadaj.skool360student.Models.Suggestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InboxFinalArray {
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Suggestion")
    @Expose
    private String suggestion;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Response")
    @Expose
    private String response;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("ReplyDate")
    @Expose
    private String replyDate;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    //===============fees receipt===========================
    @SerializedName("Payment Type")
    @Expose
    private String paymentType;
    @SerializedName("Term")
    @Expose
    private String term;
    @SerializedName("Amount")
    @Expose
    private Double amount;
    @SerializedName("URL")
    @Expose
    private String uRL;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }


}
