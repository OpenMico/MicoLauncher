package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import java.util.List;

/* loaded from: classes3.dex */
public class Execution {

    /* loaded from: classes3.dex */
    public enum GroupCategory {
        CONCURRENT(0);
        
        private int id;

        GroupCategory(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum InstructionBehavior {
        CONCURRENT(0),
        WAIT_PREVIOUS_FINISH(1),
        REPLACE_ALL(2),
        INSERT_FRONT(3);
        
        private int id;

        InstructionBehavior(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ProcessFlag {
        UNKNOWN(-1),
        PROVIDER(0),
        RESULT(1);
        
        private int id;

        ProcessFlag(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum RequestControlType {
        UNKNOWN(-1),
        NLP(0),
        TTS(1);
        
        private int id;

        RequestControlType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "Group", namespace = AIApiConstants.Execution.NAME)
    /* loaded from: classes3.dex */
    public static class Group implements InstructionPayload {
        @Required
        private GroupCategory category;
        @Required
        private List<String> ids;

        public Group() {
        }

        public Group(List<String> list, GroupCategory groupCategory) {
            this.ids = list;
            this.category = groupCategory;
        }

        @Required
        public Group setIds(List<String> list) {
            this.ids = list;
            return this;
        }

        @Required
        public List<String> getIds() {
            return this.ids;
        }

        @Required
        public Group setCategory(GroupCategory groupCategory) {
            this.category = groupCategory;
            return this;
        }

        @Required
        public GroupCategory getCategory() {
            return this.category;
        }
    }

    @NamespaceName(name = "InstructionControl", namespace = AIApiConstants.Execution.NAME)
    /* loaded from: classes3.dex */
    public static class InstructionControl implements InstructionPayload {
        @Required
        private InstructionBehavior behavior;

        public InstructionControl() {
        }

        public InstructionControl(InstructionBehavior instructionBehavior) {
            this.behavior = instructionBehavior;
        }

        @Required
        public InstructionControl setBehavior(InstructionBehavior instructionBehavior) {
            this.behavior = instructionBehavior;
            return this;
        }

        @Required
        public InstructionBehavior getBehavior() {
            return this.behavior;
        }
    }

    @NamespaceName(name = "RequestControl", namespace = AIApiConstants.Execution.NAME)
    /* loaded from: classes3.dex */
    public static class RequestControl implements ContextPayload {
        @Required
        private List<RequestControlType> disabled;

        public RequestControl() {
        }

        public RequestControl(List<RequestControlType> list) {
            this.disabled = list;
        }

        @Required
        public RequestControl setDisabled(List<RequestControlType> list) {
            this.disabled = list;
            return this;
        }

        @Required
        public List<RequestControlType> getDisabled() {
            return this.disabled;
        }
    }
}
