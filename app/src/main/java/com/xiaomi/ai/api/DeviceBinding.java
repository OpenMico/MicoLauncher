package com.xiaomi.ai.api;

import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class DeviceBinding {

    /* loaded from: classes3.dex */
    public enum DeviceDistance {
        UNKNOWN(-1),
        IMMEDIATE(0),
        NEAR(1),
        FAR(2);
        
        private int id;

        DeviceDistance(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public static class DeviceResult {
        @Required
        private String friendly_name;
        private Optional<String> uuid = Optional.empty();
        private Optional<String> mac = Optional.empty();
        private Optional<Integer> beacon_count = Optional.empty();
        private Optional<DeviceDistance> distance = Optional.empty();
        private Optional<String> bluetooth_mac = Optional.empty();

        public DeviceResult() {
        }

        public DeviceResult(String str) {
            this.friendly_name = str;
        }

        @Required
        public DeviceResult setFriendlyName(String str) {
            this.friendly_name = str;
            return this;
        }

        @Required
        public String getFriendlyName() {
            return this.friendly_name;
        }

        public DeviceResult setUuid(String str) {
            this.uuid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUuid() {
            return this.uuid;
        }

        public DeviceResult setMac(String str) {
            this.mac = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMac() {
            return this.mac;
        }

        public DeviceResult setBeaconCount(int i) {
            this.beacon_count = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getBeaconCount() {
            return this.beacon_count;
        }

        public DeviceResult setDistance(DeviceDistance deviceDistance) {
            this.distance = Optional.ofNullable(deviceDistance);
            return this;
        }

        public Optional<DeviceDistance> getDistance() {
            return this.distance;
        }

        public DeviceResult setBluetoothMac(String str) {
            this.bluetooth_mac = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBluetoothMac() {
            return this.bluetooth_mac;
        }
    }

    @NamespaceName(name = "PairBluetooth", namespace = "DeviceBinding")
    /* loaded from: classes3.dex */
    public static class PairBluetooth implements ContextPayload {
        @Required
        private List<DeviceResult> bound;

        public PairBluetooth() {
        }

        public PairBluetooth(List<DeviceResult> list) {
            this.bound = list;
        }

        @Required
        public PairBluetooth setBound(List<DeviceResult> list) {
            this.bound = list;
            return this;
        }

        @Required
        public List<DeviceResult> getBound() {
            return this.bound;
        }
    }

    @NamespaceName(name = "PairDevices", namespace = "DeviceBinding")
    /* loaded from: classes3.dex */
    public static class PairDevices implements InstructionPayload {
        @Required
        private boolean exec_bind;

        public PairDevices() {
        }

        public PairDevices(boolean z) {
            this.exec_bind = z;
        }

        @Required
        public PairDevices setExecBind(boolean z) {
            this.exec_bind = z;
            return this;
        }

        @Required
        public boolean isExecBind() {
            return this.exec_bind;
        }
    }

    @NamespaceName(name = "PairDevicesResult", namespace = "DeviceBinding")
    /* loaded from: classes3.dex */
    public static class PairDevicesResult implements EventPayload {
        @Required
        private List<DeviceResult> failed;
        @Required
        private List<DeviceResult> succeeded;

        public PairDevicesResult() {
        }

        public PairDevicesResult(List<DeviceResult> list, List<DeviceResult> list2) {
            this.succeeded = list;
            this.failed = list2;
        }

        @Required
        public PairDevicesResult setSucceeded(List<DeviceResult> list) {
            this.succeeded = list;
            return this;
        }

        @Required
        public List<DeviceResult> getSucceeded() {
            return this.succeeded;
        }

        @Required
        public PairDevicesResult setFailed(List<DeviceResult> list) {
            this.failed = list;
            return this;
        }

        @Required
        public List<DeviceResult> getFailed() {
            return this.failed;
        }
    }

    @NamespaceName(name = "ScanDeviceState", namespace = "DeviceBinding")
    /* loaded from: classes3.dex */
    public static class ScanDeviceState implements ContextPayload {
        @Required
        private List<DeviceResult> devices;

        public ScanDeviceState() {
        }

        public ScanDeviceState(List<DeviceResult> list) {
            this.devices = list;
        }

        @Required
        public ScanDeviceState setDevices(List<DeviceResult> list) {
            this.devices = list;
            return this;
        }

        @Required
        public List<DeviceResult> getDevices() {
            return this.devices;
        }
    }

    @NamespaceName(name = "ScanDevices", namespace = "DeviceBinding")
    /* loaded from: classes3.dex */
    public static class ScanDevices implements InstructionPayload {
        private Optional<String> type = Optional.empty();

        public ScanDevices setType(String str) {
            this.type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getType() {
            return this.type;
        }
    }

    @NamespaceName(name = "ScanDevicesResult", namespace = "DeviceBinding")
    /* loaded from: classes3.dex */
    public static class ScanDevicesResult implements EventPayload {
        @Required
        private List<DeviceResult> devices;
        private Optional<String> dialog_id = Optional.empty();
        @Required
        private boolean succeeded;

        public ScanDevicesResult() {
        }

        public ScanDevicesResult(boolean z, List<DeviceResult> list) {
            this.succeeded = z;
            this.devices = list;
        }

        @Required
        public ScanDevicesResult setSucceeded(boolean z) {
            this.succeeded = z;
            return this;
        }

        @Required
        public boolean isSucceeded() {
            return this.succeeded;
        }

        @Required
        public ScanDevicesResult setDevices(List<DeviceResult> list) {
            this.devices = list;
            return this;
        }

        @Required
        public List<DeviceResult> getDevices() {
            return this.devices;
        }

        public ScanDevicesResult setDialogId(String str) {
            this.dialog_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDialogId() {
            return this.dialog_id;
        }
    }
}
