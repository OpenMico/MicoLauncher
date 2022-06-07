package com.xiaomi.ai.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;

/* loaded from: classes3.dex */
public class CustomDirective {

    @NamespaceName(name = "ExecuteDeviceSkill", namespace = AIApiConstants.CustomDirective.NAME)
    /* loaded from: classes3.dex */
    public static class ExecuteDeviceSkill implements InstructionPayload {
        @Required
        private ObjectNode directive;

        public ExecuteDeviceSkill() {
        }

        public ExecuteDeviceSkill(ObjectNode objectNode) {
            this.directive = objectNode;
        }

        @Required
        public ExecuteDeviceSkill setDirective(ObjectNode objectNode) {
            this.directive = objectNode;
            return this;
        }

        @Required
        public ObjectNode getDirective() {
            return this.directive;
        }
    }
}
