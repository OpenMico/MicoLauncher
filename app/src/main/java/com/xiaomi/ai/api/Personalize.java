package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class Personalize {

    @NamespaceName(name = "Execute", namespace = AIApiConstants.Personalize.NAME)
    /* loaded from: classes3.dex */
    public static class Execute implements InstructionPayload {
        @Required
        private List<PersonalCmd> cmds;

        public Execute() {
        }

        public Execute(List<PersonalCmd> list) {
            this.cmds = list;
        }

        @Required
        public Execute setCmds(List<PersonalCmd> list) {
            this.cmds = list;
            return this;
        }

        @Required
        public List<PersonalCmd> getCmds() {
            return this.cmds;
        }
    }

    /* loaded from: classes3.dex */
    public static class PersonalCmd {
        @Required
        private String cmd;
        private Optional<Long> duration = Optional.empty();
        @Required
        private String type;

        public PersonalCmd() {
        }

        public PersonalCmd(String str, String str2) {
            this.cmd = str;
            this.type = str2;
        }

        @Required
        public PersonalCmd setCmd(String str) {
            this.cmd = str;
            return this;
        }

        @Required
        public String getCmd() {
            return this.cmd;
        }

        @Required
        public PersonalCmd setType(String str) {
            this.type = str;
            return this;
        }

        @Required
        public String getType() {
            return this.type;
        }

        public PersonalCmd setDuration(long j) {
            this.duration = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getDuration() {
            return this.duration;
        }
    }
}
