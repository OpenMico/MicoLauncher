package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.common.Optional;

/* loaded from: classes3.dex */
public class TVController {

    /* loaded from: classes3.dex */
    public enum BLEActionType {
        BOOT(0);
        
        private int id;

        BLEActionType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TVControllerErrorType {
        UNKNOWN(-1),
        OPEN_TV_SOCKET_FAIL(0),
        OPEN_TV_MESH_FAIL(1),
        OPEN_TV_BLE_FAIL(2);
        
        private int id;

        TVControllerErrorType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "Operate", namespace = AIApiConstants.TVController.NAME)
    /* loaded from: classes3.dex */
    public static class Operate implements InstructionPayload {
        private Optional<String> action = Optional.empty();
        private Optional<String> query = Optional.empty();
        private Optional<BLEActionType> ble_action = Optional.empty();
        private Optional<String> bluetooth_mac = Optional.empty();

        public Operate setAction(String str) {
            this.action = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAction() {
            return this.action;
        }

        public Operate setQuery(String str) {
            this.query = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQuery() {
            return this.query;
        }

        public Operate setBleAction(BLEActionType bLEActionType) {
            this.ble_action = Optional.ofNullable(bLEActionType);
            return this;
        }

        public Optional<BLEActionType> getBleAction() {
            return this.ble_action;
        }

        public Operate setBluetoothMac(String str) {
            this.bluetooth_mac = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBluetoothMac() {
            return this.bluetooth_mac;
        }
    }

    @NamespaceName(name = "State", namespace = AIApiConstants.TVController.NAME)
    /* loaded from: classes3.dex */
    public static class State implements ContextPayload {
        private Optional<Boolean> tv_binded = Optional.empty();
        private Optional<String> name = Optional.empty();
        private Optional<String> bluetooth_mac = Optional.empty();
        private Optional<Integer> tv_num = Optional.empty();

        public State setTvBinded(boolean z) {
            this.tv_binded = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isTvBinded() {
            return this.tv_binded;
        }

        public State setName(String str) {
            this.name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getName() {
            return this.name;
        }

        public State setBluetoothMac(String str) {
            this.bluetooth_mac = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBluetoothMac() {
            return this.bluetooth_mac;
        }

        public State setTvNum(int i) {
            this.tv_num = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getTvNum() {
            return this.tv_num;
        }
    }

    @NamespaceName(name = "StateReport", namespace = AIApiConstants.TVController.NAME)
    /* loaded from: classes3.dex */
    public static class StateReport implements EventPayload {
        private Optional<TVControllerErrorType> control_error = Optional.empty();
        private Optional<Boolean> is_success = Optional.empty();

        public StateReport setControlError(TVControllerErrorType tVControllerErrorType) {
            this.control_error = Optional.ofNullable(tVControllerErrorType);
            return this;
        }

        public Optional<TVControllerErrorType> getControlError() {
            return this.control_error;
        }

        public StateReport setIsSuccess(boolean z) {
            this.is_success = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isSuccess() {
            return this.is_success;
        }
    }
}
