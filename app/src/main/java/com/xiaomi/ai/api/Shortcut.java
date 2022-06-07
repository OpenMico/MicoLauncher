package com.xiaomi.ai.api;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import com.xiaomi.micolauncher.api.model.MainScreen;

/* loaded from: classes3.dex */
public class Shortcut {

    @NamespaceName(name = "GetCoverInfo", namespace = AIApiConstants.Shortcut.NAME)
    /* loaded from: classes3.dex */
    public static class GetCoverInfo implements EventPayload {
        @Required
        private String id;
        private Optional<ObjectNode> param = Optional.empty();

        public GetCoverInfo() {
        }

        public GetCoverInfo(String str) {
            this.id = str;
        }

        @Required
        public GetCoverInfo setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        public GetCoverInfo setParam(ObjectNode objectNode) {
            this.param = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getParam() {
            return this.param;
        }
    }

    @NamespaceName(name = MainScreen.RecommendPage.CATEGORY_SKILL, namespace = AIApiConstants.Shortcut.NAME)
    /* loaded from: classes3.dex */
    public static class Skill implements ContextPayload {
        @Required
        private String id;
        private Optional<ArrayNode> params = Optional.empty();

        public Skill() {
        }

        public Skill(String str) {
            this.id = str;
        }

        @Required
        public Skill setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        public Skill setParams(ArrayNode arrayNode) {
            this.params = Optional.ofNullable(arrayNode);
            return this;
        }

        public Optional<ArrayNode> getParams() {
            return this.params;
        }
    }
}
