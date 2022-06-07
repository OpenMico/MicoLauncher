package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.common.Optional;

/* loaded from: classes3.dex */
public class Selector {

    /* loaded from: classes3.dex */
    public enum SelectTarget {
        UNKNOWN(-1),
        MAP_NAVIGATION_LIST(0);
        
        private int id;

        SelectTarget(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "Select", namespace = AIApiConstants.Selector.NAME)
    /* loaded from: classes3.dex */
    public static class Select implements InstructionPayload {
        private Optional<Integer> index = Optional.empty();
        @Deprecated
        private Optional<String> name = Optional.empty();
        private Optional<SelectTarget> target = Optional.empty();

        public Select setIndex(int i) {
            this.index = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getIndex() {
            return this.index;
        }

        @Deprecated
        public Select setName(String str) {
            this.name = Optional.ofNullable(str);
            return this;
        }

        @Deprecated
        public Optional<String> getName() {
            return this.name;
        }

        public Select setTarget(SelectTarget selectTarget) {
            this.target = Optional.ofNullable(selectTarget);
            return this;
        }

        public Optional<SelectTarget> getTarget() {
            return this.target;
        }
    }
}
