package com.xiaomi.ai.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;

/* loaded from: classes3.dex */
public class Agent {

    /* loaded from: classes3.dex */
    public enum TargetDef {
        UNKNOWN(-1),
        TV(0),
        REMOTE_CONTROLLER(1);
        
        private int id;

        TargetDef(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "Answer", namespace = AIApiConstants.Agent.NAME)
    /* loaded from: classes3.dex */
    public static class Answer implements InstructionPayload {
        @Required
        private ObjectNode answer;

        public Answer() {
        }

        public Answer(ObjectNode objectNode) {
            this.answer = objectNode;
        }

        @Required
        public Answer setAnswer(ObjectNode objectNode) {
            this.answer = objectNode;
            return this;
        }

        @Required
        public ObjectNode getAnswer() {
            return this.answer;
        }
    }

    @NamespaceName(name = "Forward", namespace = AIApiConstants.Agent.NAME)
    /* loaded from: classes3.dex */
    public static class Forward implements InstructionPayload {
        @Required
        private Instruction<InstructionPayload> forward;
        @Required
        private TargetDef target;

        public Forward() {
        }

        public Forward(Instruction<InstructionPayload> instruction, TargetDef targetDef) {
            this.forward = instruction;
            this.target = targetDef;
        }

        @Required
        public Forward setForward(Instruction<InstructionPayload> instruction) {
            this.forward = instruction;
            return this;
        }

        @Required
        public Instruction<InstructionPayload> getForward() {
            return this.forward;
        }

        @Required
        public Forward setTarget(TargetDef targetDef) {
            this.target = targetDef;
            return this;
        }

        @Required
        public TargetDef getTarget() {
            return this.target;
        }
    }

    @NamespaceName(name = "Query", namespace = AIApiConstants.Agent.NAME)
    /* loaded from: classes3.dex */
    public static class Query implements InstructionPayload {
        @Required
        private String query;

        public Query() {
        }

        public Query(String str) {
            this.query = str;
        }

        @Required
        public Query setQuery(String str) {
            this.query = str;
            return this;
        }

        @Required
        public String getQuery() {
            return this.query;
        }
    }
}
