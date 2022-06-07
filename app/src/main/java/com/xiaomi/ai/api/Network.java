package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;

/* loaded from: classes3.dex */
public class Network {

    /* loaded from: classes3.dex */
    public enum NetworkType {
        UNKNOWN(-1),
        WIFI(0),
        DATA(1),
        HOTSPOT(2);
        
        private int id;

        NetworkType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "Switch", namespace = AIApiConstants.Network.NAME)
    /* loaded from: classes3.dex */
    public static class Switch implements InstructionPayload {
        @Required
        private NetworkType target;

        public Switch() {
        }

        public Switch(NetworkType networkType) {
            this.target = networkType;
        }

        @Required
        public Switch setTarget(NetworkType networkType) {
            this.target = networkType;
            return this;
        }

        @Required
        public NetworkType getTarget() {
            return this.target;
        }
    }

    @NamespaceName(name = "TurnOff", namespace = AIApiConstants.Network.NAME)
    /* loaded from: classes3.dex */
    public static class TurnOff implements InstructionPayload {
        @Required
        private NetworkType target;

        public TurnOff() {
        }

        public TurnOff(NetworkType networkType) {
            this.target = networkType;
        }

        @Required
        public TurnOff setTarget(NetworkType networkType) {
            this.target = networkType;
            return this;
        }

        @Required
        public NetworkType getTarget() {
            return this.target;
        }
    }

    @NamespaceName(name = "TurnOn", namespace = AIApiConstants.Network.NAME)
    /* loaded from: classes3.dex */
    public static class TurnOn implements InstructionPayload {
        @Required
        private NetworkType target;

        public TurnOn() {
        }

        public TurnOn(NetworkType networkType) {
            this.target = networkType;
        }

        @Required
        public TurnOn setTarget(NetworkType networkType) {
            this.target = networkType;
            return this;
        }

        @Required
        public NetworkType getTarget() {
            return this.target;
        }
    }
}
