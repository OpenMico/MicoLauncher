package com.xiaomi.ai.api;

import com.xiaomi.ai.api.Scene;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;

/* loaded from: classes3.dex */
public class Dialog {

    @NamespaceName(name = "EnterTemporaryContinuousDialog", namespace = "Dialog")
    /* loaded from: classes3.dex */
    public static class EnterTemporaryContinuousDialog implements InstructionPayload {
    }

    @NamespaceName(name = "ExitContinuousDialog", namespace = "Dialog")
    /* loaded from: classes3.dex */
    public static class ExitContinuousDialog implements InstructionPayload {
    }

    @NamespaceName(name = "ExitMultipleTurn", namespace = "Dialog")
    /* loaded from: classes3.dex */
    public static class ExitMultipleTurn implements InstructionPayload {
    }

    @NamespaceName(name = "Finish", namespace = "Dialog")
    /* loaded from: classes3.dex */
    public static class Finish implements InstructionPayload {
    }

    @NamespaceName(name = "TurnOffContinuousDialog", namespace = "Dialog")
    /* loaded from: classes3.dex */
    public static class TurnOffContinuousDialog implements InstructionPayload {
    }

    @NamespaceName(name = "TurnOnContinuousDialog", namespace = "Dialog")
    /* loaded from: classes3.dex */
    public static class TurnOnContinuousDialog implements InstructionPayload {
    }

    /* loaded from: classes3.dex */
    public enum JudgeFinishType {
        NEARBY_AWAKE(0);
        
        private int id;

        JudgeFinishType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "CacheInstructions", namespace = "Dialog")
    /* loaded from: classes3.dex */
    public static class CacheInstructions implements InstructionPayload {
        @Required
        private int duration_in_ms;

        public CacheInstructions() {
        }

        public CacheInstructions(int i) {
            this.duration_in_ms = i;
        }

        @Required
        public CacheInstructions setDurationInMs(int i) {
            this.duration_in_ms = i;
            return this;
        }

        @Required
        public int getDurationInMs() {
            return this.duration_in_ms;
        }
    }

    @NamespaceName(name = "DialogState", namespace = "Dialog")
    /* loaded from: classes3.dex */
    public static class DialogState implements ContextPayload {
        private Optional<Boolean> continuous_dialog = Optional.empty();
        private Optional<Boolean> is_interruptable = Optional.empty();
        private Optional<Scene.SceneType> scene = Optional.empty();

        public DialogState setContinuousDialog(boolean z) {
            this.continuous_dialog = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isContinuousDialog() {
            return this.continuous_dialog;
        }

        public DialogState setIsInterruptable(boolean z) {
            this.is_interruptable = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isInterruptable() {
            return this.is_interruptable;
        }

        public DialogState setScene(Scene.SceneType sceneType) {
            this.scene = Optional.ofNullable(sceneType);
            return this;
        }

        public Optional<Scene.SceneType> getScene() {
            return this.scene;
        }
    }

    @NamespaceName(name = "JudgeFinish", namespace = "Dialog")
    /* loaded from: classes3.dex */
    public static class JudgeFinish implements InstructionPayload {
        @Required
        private JudgeFinishType source;

        public JudgeFinish() {
        }

        public JudgeFinish(JudgeFinishType judgeFinishType) {
            this.source = judgeFinishType;
        }

        @Required
        public JudgeFinish setSource(JudgeFinishType judgeFinishType) {
            this.source = judgeFinishType;
            return this;
        }

        @Required
        public JudgeFinishType getSource() {
            return this.source;
        }
    }

    @NamespaceName(name = "MultipleTurnInProgress", namespace = "Dialog")
    /* loaded from: classes3.dex */
    public static class MultipleTurnInProgress implements InstructionPayload {
        @Required
        private String name;

        public MultipleTurnInProgress() {
        }

        public MultipleTurnInProgress(String str) {
            this.name = str;
        }

        @Required
        public MultipleTurnInProgress setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }
    }

    @NamespaceName(name = "Reject", namespace = "Dialog")
    /* loaded from: classes3.dex */
    public static class Reject implements InstructionPayload {
        @Required
        private String query;

        public Reject() {
        }

        public Reject(String str) {
            this.query = str;
        }

        @Required
        public Reject setQuery(String str) {
            this.query = str;
            return this;
        }

        @Required
        public String getQuery() {
            return this.query;
        }
    }
}
