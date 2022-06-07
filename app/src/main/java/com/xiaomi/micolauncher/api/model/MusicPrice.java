package com.xiaomi.micolauncher.api.model;

import android.text.TextUtils;

/* loaded from: classes3.dex */
public class MusicPrice {
    private String contractDesc;
    private int contractFirstSalePrice;
    private String contractResourceId;
    private boolean isRenew;
    private String origin;
    private int originalSalePrice;
    private String paymentUrl;
    private String productId;
    private String productName;
    private int purchasePrice;
    private int salePrice;
    private boolean selected;
    private int sellSubType;
    private int sellType;
    private String shortName;
    private String subtitle;
    private boolean supportContract;
    private boolean timeLimitSale;
    private int validPeriod;

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String str) {
        this.productId = str;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String str) {
        this.productName = str;
    }

    public float getSalePrice() {
        return this.salePrice;
    }

    public void setSalePrice(int i) {
        this.salePrice = i;
    }

    public int getPurchasePrice() {
        return this.purchasePrice;
    }

    public void setPurchasePrice(int i) {
        this.purchasePrice = i;
    }

    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String str) {
        this.origin = str;
    }

    public String getPaymentUrl() {
        return this.paymentUrl;
    }

    public void setPaymentUrl(String str) {
        this.paymentUrl = str;
    }

    public boolean isSupportContract() {
        return this.supportContract;
    }

    public void setSupportContract(boolean z) {
        this.supportContract = z;
    }

    public String getContractDesc() {
        return this.contractDesc;
    }

    public void setContractDesc(String str) {
        this.contractDesc = str;
    }

    public int getContractFirstSalePrice() {
        return this.contractFirstSalePrice;
    }

    public void setContractFirstSalePrice(int i) {
        this.contractFirstSalePrice = i;
    }

    public int getOriginalSalePrice() {
        return this.originalSalePrice;
    }

    public void setOriginalSalePrice(int i) {
        this.originalSalePrice = i;
    }

    public int getValidPeriod() {
        return this.validPeriod;
    }

    public void setValidPeriod(int i) {
        this.validPeriod = i;
    }

    public int getSellType() {
        return this.sellType;
    }

    public void setSellType(int i) {
        this.sellType = i;
    }

    public int getSellSubType() {
        return this.sellSubType;
    }

    public void setSellSubType(int i) {
        this.sellSubType = i;
    }

    public String getContractResourceId() {
        return this.contractResourceId;
    }

    public void setContractResourceId(String str) {
        this.contractResourceId = str;
    }

    public boolean isTimeLimitSale() {
        return this.timeLimitSale;
    }

    public void setTimeLimitSale(boolean z) {
        this.timeLimitSale = z;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean z) {
        this.selected = z;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String str) {
        this.shortName = str;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(String str) {
        this.subtitle = str;
    }

    public boolean isRenew() {
        return !TextUtils.isEmpty(getContractResourceId());
    }

    /* loaded from: classes3.dex */
    public static class Order {
        public static final int ALL_ALBUM = 2;
        public static final String CONTRACT_NOT_SIGN = "CONTRACT_NOT_SIGN";
        public static final int SINGLE_ALBUM = 1;
        public static final String TRADE_DELIVERED = "TRADE_DELIVERED";
        public static final String TRADE_FINISHED = "TRADE_FINISHED";
        public static final String TRADE_FINISHING = "TRADE_FINISHING";
        public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
        public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        private int contractFee;
        private String contractId;
        private String mitvContractProductId;
        private String orderId;
        private String productName;
        private int totalFee;
        private String wechatPlanId;

        public boolean isRenew() {
            return !TextUtils.isEmpty(getContractId());
        }

        public String getOrderId() {
            return this.orderId;
        }

        public void setOrderId(String str) {
            this.orderId = str;
        }

        public String getContractId() {
            return this.contractId;
        }

        public void setContractId(String str) {
            this.contractId = str;
        }

        public float getTotalFee() {
            return this.totalFee;
        }

        public void setTotalFee(int i) {
            this.totalFee = i;
        }

        public String getMitvContractProductId() {
            return this.mitvContractProductId;
        }

        public void setMitvContractProductId(String str) {
            this.mitvContractProductId = str;
        }

        public String getWechatPlanId() {
            return this.wechatPlanId;
        }

        public void setWechatPlanId(String str) {
            this.wechatPlanId = str;
        }

        public String getProductName() {
            return this.productName;
        }

        public void setProductName(String str) {
            this.productName = str;
        }

        public int getContractFee() {
            return this.contractFee;
        }

        public void setContractFee(int i) {
            this.contractFee = i;
        }
    }
}
