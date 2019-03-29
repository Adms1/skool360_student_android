package com.anandniketanbhadaj.skool360student.Models;

import java.util.ArrayList;

/**
 * Created by admsandroid on 9/6/2017.
 */

public class PaymentLedgerModel {
    private String PayDate;
    private String Paid;
    private String PaymentType;
    private String Date;
    private String Term;
    private String Amount;
    private String URL;

    private ArrayList<Data> dataArrayList;

    public PaymentLedgerModel() {
    }

    public String getPayDate() {
        return PayDate;
    }

    public void setPayDate(String payDate) {
        PayDate = payDate;
    }

    public String getPaid() {
        return Paid;
    }

    public void setPaid(String paid) {
        Paid = paid;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTerm() {
        return Term;
    }

    public void setTerm(String term) {
        Term = term;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public ArrayList<Data> getDataArrayList() {
        return dataArrayList;
    }

    public void setDataArrayList(ArrayList<Data> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }

    public class Data {

        private String Term;
        private String TermDetail;
        private String GRNO;
        private String PayMode;
        private String PaidFee;
        private String ReceiptNo;
        private String AdmissionFee;
        private String CautionFee;
        private String PreviousFees;
        private String TuitionFee;
        private String Transport;
        private String ImprestFee;
        private String LatesFee;
        private String DiscountFee;
        private String PayPaidFees;
        private String CurrentOutstandingFees;
        private String BankName;
        private String ChequeNumber;

        public Data() {
        }

        public String getTerm() {
            return Term;
        }

        public void setTerm(String term) {
            Term = term;
        }

        public String getTermDetail() {
            return TermDetail;
        }

        public void setTermDetail(String termDetail) {
            TermDetail = termDetail;
        }

        public String getGRNO() {
            return GRNO;
        }

        public void setGRNO(String GRNO) {
            this.GRNO = GRNO;
        }

        public String getPayMode() {
            return PayMode;
        }

        public void setPayMode(String payMode) {
            PayMode = payMode;
        }

        public String getPaidFee() {
            return PaidFee;
        }

        public void setPaidFee(String paidFee) {
            PaidFee = paidFee;
        }

        public String getReceiptNo() {
            return ReceiptNo;
        }

        public void setReceiptNo(String receiptNo) {
            ReceiptNo = receiptNo;
        }

        public String getAdmissionFee() {
            return AdmissionFee;
        }

        public void setAdmissionFee(String admissionFee) {
            AdmissionFee = admissionFee;
        }

        public String getCautionFee() {
            return CautionFee;
        }

        public void setCautionFee(String cautionFee) {
            CautionFee = cautionFee;
        }

        public String getPreviousFees() {
            return PreviousFees;
        }

        public void setPreviousFees(String previousFees) {
            PreviousFees = previousFees;
        }

        public String getTuitionFee() {
            return TuitionFee;
        }

        public void setTuitionFee(String tuitionFee) {
            TuitionFee = tuitionFee;
        }

        public String getTransport() {
            return Transport;
        }

        public void setTransport(String transport) {
            Transport = transport;
        }

        public String getImprestFee() {
            return ImprestFee;
        }

        public void setImprestFee(String imprestFee) {
            ImprestFee = imprestFee;
        }

        public String getLatesFee() {
            return LatesFee;
        }

        public void setLatesFee(String latesFee) {
            LatesFee = latesFee;
        }

        public String getDiscountFee() {
            return DiscountFee;
        }

        public void setDiscountFee(String discountFee) {
            DiscountFee = discountFee;
        }

        public String getPayPaidFees() {
            return PayPaidFees;
        }

        public void setPayPaidFees(String payPaidFees) {
            PayPaidFees = payPaidFees;
        }

        public String getCurrentOutstandingFees() {
            return CurrentOutstandingFees;
        }

        public void setCurrentOutstandingFees(String currentOutstandingFees) {
            CurrentOutstandingFees = currentOutstandingFees;
        }

        public String getBankName() {
            return BankName;
        }

        public void setBankName(String bankName) {
            BankName = bankName;
        }

        public String getChequeNumber() {
            return ChequeNumber;
        }

        public void setChequeNumber(String chequeNumber) {
            ChequeNumber = chequeNumber;
        }
    }

}
