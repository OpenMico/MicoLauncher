package com.xiaomi.micolauncher.api.model;

import java.io.Serializable;
import java.util.Map;

/* loaded from: classes3.dex */
public class Admin {

    /* loaded from: classes3.dex */
    public static class Mico implements Serializable {
        private static final String OFFLINE = "offline";
        private static final String ONLINE = "online";
        private static final long serialVersionUID = 4498689688567310252L;
        private Map<String, Integer> capabilities;
        public boolean current;
        public String miotDID;
        public String name;
        public String presence;
        public String deviceID = "";
        public String serialNumber = "";
        public String hardware = "";
        public String romVersion = "";

        public String toString() {
            return "Mico{deviceID='" + this.deviceID + "', serialNumber='" + this.serialNumber + "', name='" + this.name + "', presence='" + this.presence + "', miotDID='" + this.miotDID + "', current=" + this.current + ", hardware='" + this.hardware + "', romVersion='" + this.romVersion + "', capabilities=" + this.capabilities + '}';
        }
    }
}
