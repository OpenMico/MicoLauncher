package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;

/* loaded from: classes3.dex */
public class Bluetooth {

    @NamespaceName(name = "Connect", namespace = AIApiConstants.Bluetooth.NAME)
    /* loaded from: classes3.dex */
    public static class Connect implements InstructionPayload {
    }

    @NamespaceName(name = "Disconnect", namespace = AIApiConstants.Bluetooth.NAME)
    /* loaded from: classes3.dex */
    public static class Disconnect implements InstructionPayload {
    }

    @NamespaceName(name = "Switch", namespace = AIApiConstants.Bluetooth.NAME)
    /* loaded from: classes3.dex */
    public static class Switch implements InstructionPayload {
    }

    @NamespaceName(name = "TurnOff", namespace = AIApiConstants.Bluetooth.NAME)
    /* loaded from: classes3.dex */
    public static class TurnOff implements InstructionPayload {
    }

    @NamespaceName(name = "TurnOn", namespace = AIApiConstants.Bluetooth.NAME)
    /* loaded from: classes3.dex */
    public static class TurnOn implements InstructionPayload {
    }
}
