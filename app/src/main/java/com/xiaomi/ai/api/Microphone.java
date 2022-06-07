package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.common.Optional;

/* loaded from: classes3.dex */
public class Microphone {

    @NamespaceName(name = "TurnOn", namespace = AIApiConstants.Microphone.NAME)
    /* loaded from: classes3.dex */
    public static class TurnOn implements InstructionPayload {
    }

    @NamespaceName(name = "TurnOff", namespace = AIApiConstants.Microphone.NAME)
    /* loaded from: classes3.dex */
    public static class TurnOff implements InstructionPayload {
        private Optional<Integer> duration = Optional.empty();

        public TurnOff setDuration(int i) {
            this.duration = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getDuration() {
            return this.duration;
        }
    }
}
