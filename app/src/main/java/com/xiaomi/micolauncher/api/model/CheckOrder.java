package com.xiaomi.micolauncher.api.model;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes3.dex */
public class CheckOrder {

    /* loaded from: classes3.dex */
    public static class CheckRenewOrder {
        private DataBean data;
        private int status;
        private int timeout;

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int i) {
            this.status = i;
        }

        public DataBean getData() {
            return this.data;
        }

        public void setData(DataBean dataBean) {
            this.data = dataBean;
        }

        public int getTimeout() {
            return this.timeout;
        }

        public void setTimeout(int i) {
            this.timeout = i;
        }

        /* loaded from: classes3.dex */
        public static class DataBean {
            private int order_result;
            private int sign_result;

            public int getSign_result() {
                return this.sign_result;
            }

            public void setSign_result(int i) {
                this.sign_result = i;
            }

            public int getOrder_result() {
                return this.order_result;
            }

            public void setOrder_result(int i) {
                this.order_result = i;
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class CheckNotRenewOrder {
        @SerializedName("data")
        private DataBean dataBean;
        private int status;
        private String status_msg;

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int i) {
            this.status = i;
        }

        public String getStatus_msg() {
            return this.status_msg;
        }

        public void setStatus_msg(String str) {
            this.status_msg = str;
        }

        public DataBean getDataBean() {
            return this.dataBean;
        }

        public void setDataBean(DataBean dataBean) {
            this.dataBean = dataBean;
        }

        /* loaded from: classes3.dex */
        public static class DataBean {
            private int result;

            public int getResult() {
                return this.result;
            }

            public void setResult(int i) {
                this.result = i;
            }
        }
    }
}
