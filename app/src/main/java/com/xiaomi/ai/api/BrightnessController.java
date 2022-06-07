package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;

/* loaded from: classes3.dex */
public class BrightnessController {

    @NamespaceName(name = "AdjustBrightness", namespace = AIApiConstants.BrightnessController.NAME)
    /* loaded from: classes3.dex */
    public static class AdjustBrightness implements InstructionPayload {
        @Required
        private int brightness_delta;

        public AdjustBrightness() {
        }

        public AdjustBrightness(int i) {
            this.brightness_delta = i;
        }

        @Required
        public AdjustBrightness setBrightnessDelta(int i) {
            this.brightness_delta = i;
            return this;
        }

        @Required
        public int getBrightnessDelta() {
            return this.brightness_delta;
        }
    }

    @NamespaceName(name = "SetBrightness", namespace = AIApiConstants.BrightnessController.NAME)
    /* loaded from: classes3.dex */
    public static class SetBrightness implements InstructionPayload {
        @Required
        private int brightness;

        public SetBrightness() {
        }

        public SetBrightness(int i) {
            this.brightness = i;
        }

        @Required
        public SetBrightness setBrightness(int i) {
            this.brightness = i;
            return this;
        }

        @Required
        public int getBrightness() {
            return this.brightness;
        }
    }
}
