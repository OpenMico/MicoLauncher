package com.xiaomi.ai.api;

import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;

/* loaded from: classes3.dex */
public class Scene {

    /* loaded from: classes3.dex */
    public enum SceneType {
        VIDEO(0),
        MUSIC(1),
        STATION(2),
        MIOT(3),
        VOICE(4),
        TRANSLATION(5),
        CALCULATION(6),
        OPEN_PLATFORM(7),
        MULTI_DOMAIN(8);
        
        private int id;

        SceneType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "Enter", namespace = "Scene")
    /* loaded from: classes3.dex */
    public static class Enter implements InstructionPayload {
        private Optional<Integer> mic_duration = Optional.empty();
        @Required
        private SceneType type;

        public Enter() {
        }

        public Enter(SceneType sceneType) {
            this.type = sceneType;
        }

        @Required
        public Enter setType(SceneType sceneType) {
            this.type = sceneType;
            return this;
        }

        @Required
        public SceneType getType() {
            return this.type;
        }

        public Enter setMicDuration(int i) {
            this.mic_duration = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getMicDuration() {
            return this.mic_duration;
        }
    }
}
