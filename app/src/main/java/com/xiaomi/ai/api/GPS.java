package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;

/* loaded from: classes3.dex */
public class GPS {

    @NamespaceName(name = "Switch", namespace = AIApiConstants.GPS.NAME)
    /* loaded from: classes3.dex */
    public static class Switch implements InstructionPayload {
    }

    @NamespaceName(name = "TurnOff", namespace = AIApiConstants.GPS.NAME)
    /* loaded from: classes3.dex */
    public static class TurnOff implements InstructionPayload {
    }

    @NamespaceName(name = "TurnOn", namespace = AIApiConstants.GPS.NAME)
    /* loaded from: classes3.dex */
    public static class TurnOn implements InstructionPayload {
    }
}
