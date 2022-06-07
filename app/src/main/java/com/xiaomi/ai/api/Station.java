package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;

/* loaded from: classes3.dex */
public class Station {

    @NamespaceName(name = "DisplayDetails", namespace = AIApiConstants.Station.NAME)
    /* loaded from: classes3.dex */
    public static class DisplayDetails implements EventPayload {
        @Required
        private String id;
        private Optional<Integer> offset = Optional.empty();
        private Optional<Integer> version = Optional.empty();
        private Optional<String> ref = Optional.empty();

        public DisplayDetails() {
        }

        public DisplayDetails(String str) {
            this.id = str;
        }

        @Required
        public DisplayDetails setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        public DisplayDetails setOffset(int i) {
            this.offset = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getOffset() {
            return this.offset;
        }

        public DisplayDetails setVersion(int i) {
            this.version = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getVersion() {
            return this.version;
        }

        public DisplayDetails setRef(String str) {
            this.ref = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRef() {
            return this.ref;
        }
    }
}
