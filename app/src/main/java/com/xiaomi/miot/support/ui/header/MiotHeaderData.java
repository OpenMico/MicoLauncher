package com.xiaomi.miot.support.ui.header;

/* loaded from: classes3.dex */
public class MiotHeaderData {
    public Item mHumidity;
    public Item mPM25;
    public Item mTemperature;

    /* loaded from: classes3.dex */
    public static class Item {
        public String did;
        public String roomId;
        public String roomInfo;
        public int value;

        public boolean equals(Object obj) {
            if (obj instanceof Item) {
                Item item = (Item) obj;
                if (item.did.equals(this.did) && item.roomId.equals(this.roomId) && item.value == this.value) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean equals(Object obj) {
        Item item;
        Item item2;
        Item item3;
        return (obj instanceof MiotHeaderData) && ((this.mTemperature == null && ((MiotHeaderData) obj).mTemperature == null) || ((item3 = this.mTemperature) != null && item3.equals(((MiotHeaderData) obj).mTemperature))) && (((this.mHumidity == null && ((MiotHeaderData) obj).mHumidity == null) || ((item2 = this.mHumidity) != null && item2.equals(((MiotHeaderData) obj).mHumidity))) && ((this.mPM25 == null && ((MiotHeaderData) obj).mPM25 == null) || ((item = this.mPM25) != null && item.equals(((MiotHeaderData) obj).mPM25))));
    }
}
