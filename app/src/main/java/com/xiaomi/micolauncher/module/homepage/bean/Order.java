package com.xiaomi.micolauncher.module.homepage.bean;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes3.dex */
public class Order {
    @SerializedName("list")
    public List<OrderInfo> list;
    @SerializedName("nextIndex")
    public long nextIndex;

    /* loaded from: classes3.dex */
    public static class OrderInfo {
        public String action;
        public String broadcaster;
        public String category;
        public long createTime;
        public long finishTime;
        public String orderId;
        public String origin;
        public String originName;
        public long payTime;
        public String pictureUrl;
        public String productId;
        public String productName;
        public String status;
        public String type;
        public String userId;

        public int getTypeId(String str) {
            if (str == null) {
                return 0;
            }
            char c = 65535;
            int hashCode = str.hashCode();
            if (hashCode != 92896879) {
                if (hashCode != 108270587) {
                    if (hashCode == 109627663 && str.equals("sound")) {
                        c = 2;
                    }
                } else if (str.equals("radio")) {
                    c = 1;
                }
            } else if (str.equals("album")) {
                c = 0;
            }
            switch (c) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                default:
                    return 0;
            }
        }

        public String toString() {
            return "OrderInfo{origin='" + this.origin + "', action='" + this.action + "', category='" + this.category + "'}";
        }
    }
}
