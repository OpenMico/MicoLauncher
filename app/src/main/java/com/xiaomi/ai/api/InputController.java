package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;

/* loaded from: classes3.dex */
public class InputController {

    /* loaded from: classes3.dex */
    public enum InputType {
        HDMI(0),
        AV(1),
        DTMB(2),
        TV(3);
        
        private int id;

        InputType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "SelectInput", namespace = AIApiConstants.InputController.NAME)
    /* loaded from: classes3.dex */
    public static class SelectInput implements InstructionPayload {
        private Optional<Integer> index = Optional.empty();
        @Required
        private InputType input;

        public SelectInput() {
        }

        public SelectInput(InputType inputType) {
            this.input = inputType;
        }

        @Required
        public SelectInput setInput(InputType inputType) {
            this.input = inputType;
            return this;
        }

        @Required
        public InputType getInput() {
            return this.input;
        }

        public SelectInput setIndex(int i) {
            this.index = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getIndex() {
            return this.index;
        }
    }
}
