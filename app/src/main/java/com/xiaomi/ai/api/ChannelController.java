package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class ChannelController {

    /* loaded from: classes3.dex */
    public enum ChangeTVChannelOp {
        UNKNOWN(-1),
        PREV(0),
        NEXT(1),
        RETURN(2);
        
        private int id;

        ChangeTVChannelOp(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TVChannelMode {
        STB(0),
        ATV(1);
        
        private int id;

        TVChannelMode(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TVChannelStatus {
        PLAYING(0);
        
        private int id;

        TVChannelStatus(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "ChangeRadioChannel", namespace = AIApiConstants.ChannelController.NAME)
    /* loaded from: classes3.dex */
    public static class ChangeRadioChannel implements InstructionPayload {
        @Required
        private List<RadioItem> candidates;
        private Optional<String> keyword = Optional.empty();

        public ChangeRadioChannel() {
        }

        public ChangeRadioChannel(List<RadioItem> list) {
            this.candidates = list;
        }

        @Required
        public ChangeRadioChannel setCandidates(List<RadioItem> list) {
            this.candidates = list;
            return this;
        }

        @Required
        public List<RadioItem> getCandidates() {
            return this.candidates;
        }

        public ChangeRadioChannel setKeyword(String str) {
            this.keyword = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getKeyword() {
            return this.keyword;
        }
    }

    @NamespaceName(name = "ChangeTVChannel", namespace = AIApiConstants.ChannelController.NAME)
    /* loaded from: classes3.dex */
    public static class ChangeTVChannel implements InstructionPayload {
        private Optional<Integer> num = Optional.empty();
        private Optional<List<String>> candidates = Optional.empty();
        private Optional<ChangeTVChannelOp> operation = Optional.empty();
        private Optional<String> standard_channel = Optional.empty();
        private Optional<Long> atv_id = Optional.empty();

        public ChangeTVChannel setNum(int i) {
            this.num = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getNum() {
            return this.num;
        }

        public ChangeTVChannel setCandidates(List<String> list) {
            this.candidates = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getCandidates() {
            return this.candidates;
        }

        public ChangeTVChannel setOperation(ChangeTVChannelOp changeTVChannelOp) {
            this.operation = Optional.ofNullable(changeTVChannelOp);
            return this;
        }

        public Optional<ChangeTVChannelOp> getOperation() {
            return this.operation;
        }

        public ChangeTVChannel setStandardChannel(String str) {
            this.standard_channel = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getStandardChannel() {
            return this.standard_channel;
        }

        public ChangeTVChannel setAtvId(long j) {
            this.atv_id = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getAtvId() {
            return this.atv_id;
        }
    }

    /* loaded from: classes3.dex */
    public static class RadioItem {
        @Required
        private String frequency;
        @Required
        private String name;
        private Optional<AudioPlayer.Stream> stream = Optional.empty();

        public RadioItem() {
        }

        public RadioItem(String str, String str2) {
            this.name = str;
            this.frequency = str2;
        }

        @Required
        public RadioItem setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public RadioItem setFrequency(String str) {
            this.frequency = str;
            return this;
        }

        @Required
        public String getFrequency() {
            return this.frequency;
        }

        public RadioItem setStream(AudioPlayer.Stream stream) {
            this.stream = Optional.ofNullable(stream);
            return this;
        }

        public Optional<AudioPlayer.Stream> getStream() {
            return this.stream;
        }
    }

    @NamespaceName(name = "TVChannelState", namespace = AIApiConstants.ChannelController.NAME)
    /* loaded from: classes3.dex */
    public static class TVChannelState implements ContextPayload {
        @Required
        private TVChannelMode mode;
        private Optional<TVChannelStatus> status = Optional.empty();

        public TVChannelState() {
        }

        public TVChannelState(TVChannelMode tVChannelMode) {
            this.mode = tVChannelMode;
        }

        @Required
        public TVChannelState setMode(TVChannelMode tVChannelMode) {
            this.mode = tVChannelMode;
            return this;
        }

        @Required
        public TVChannelMode getMode() {
            return this.mode;
        }

        public TVChannelState setStatus(TVChannelStatus tVChannelStatus) {
            this.status = Optional.ofNullable(tVChannelStatus);
            return this;
        }

        public Optional<TVChannelStatus> getStatus() {
            return this.status;
        }
    }
}
