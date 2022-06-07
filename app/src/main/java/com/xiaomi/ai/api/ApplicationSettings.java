package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class ApplicationSettings {

    /* loaded from: classes3.dex */
    public enum MenstrualPeriodTimeType {
        RECORD(0),
        PREDICTION(1);
        
        private int id;

        MenstrualPeriodTimeType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum MenstruationOperationType {
        SETTING(0),
        RECORD_QUERY(1),
        PERIOD_PREDICTION(2),
        RECORD_PREDICTION_QUERY(3);
        
        private int id;

        MenstruationOperationType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "ManageMenstrual", namespace = AIApiConstants.ApplicationSettings.NAME)
    /* loaded from: classes3.dex */
    public static class ManageMenstrual implements InstructionPayload {
        @Required
        private MenstrualPeriod item;
        @Required
        private MenstruationOperationType operation_type;

        public ManageMenstrual() {
        }

        public ManageMenstrual(MenstruationOperationType menstruationOperationType, MenstrualPeriod menstrualPeriod) {
            this.operation_type = menstruationOperationType;
            this.item = menstrualPeriod;
        }

        @Required
        public ManageMenstrual setOperationType(MenstruationOperationType menstruationOperationType) {
            this.operation_type = menstruationOperationType;
            return this;
        }

        @Required
        public MenstruationOperationType getOperationType() {
            return this.operation_type;
        }

        @Required
        public ManageMenstrual setItem(MenstrualPeriod menstrualPeriod) {
            this.item = menstrualPeriod;
            return this;
        }

        @Required
        public MenstrualPeriod getItem() {
            return this.item;
        }
    }

    /* loaded from: classes3.dex */
    public static class MenstrualPeriod {
        private Optional<MenstrualPeriodInfo> start = Optional.empty();
        private Optional<MenstrualPeriodInfo> end = Optional.empty();

        public MenstrualPeriod setStart(MenstrualPeriodInfo menstrualPeriodInfo) {
            this.start = Optional.ofNullable(menstrualPeriodInfo);
            return this;
        }

        public Optional<MenstrualPeriodInfo> getStart() {
            return this.start;
        }

        public MenstrualPeriod setEnd(MenstrualPeriodInfo menstrualPeriodInfo) {
            this.end = Optional.ofNullable(menstrualPeriodInfo);
            return this;
        }

        public Optional<MenstrualPeriodInfo> getEnd() {
            return this.end;
        }
    }

    /* loaded from: classes3.dex */
    public static class MenstrualPeriodInfo {
        private Optional<Long> timestamp = Optional.empty();
        private Optional<MenstrualPeriodTimeType> type = Optional.empty();

        public MenstrualPeriodInfo setTimestamp(long j) {
            this.timestamp = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getTimestamp() {
            return this.timestamp;
        }

        public MenstrualPeriodInfo setType(MenstrualPeriodTimeType menstrualPeriodTimeType) {
            this.type = Optional.ofNullable(menstrualPeriodTimeType);
            return this;
        }

        public Optional<MenstrualPeriodTimeType> getType() {
            return this.type;
        }
    }

    @NamespaceName(name = "MenstruationState", namespace = AIApiConstants.ApplicationSettings.NAME)
    /* loaded from: classes3.dex */
    public static class MenstruationState implements EventPayload {
        @Required
        private MenstruationOperationType operation_type;
        @Required
        private int status_code;
        private Optional<List<MenstrualPeriod>> menstrual_period_records = Optional.empty();
        private Optional<List<MenstrualPeriod>> menstrual_period_predict = Optional.empty();

        public MenstruationState() {
        }

        public MenstruationState(int i, MenstruationOperationType menstruationOperationType) {
            this.status_code = i;
            this.operation_type = menstruationOperationType;
        }

        @Required
        public MenstruationState setStatusCode(int i) {
            this.status_code = i;
            return this;
        }

        @Required
        public int getStatusCode() {
            return this.status_code;
        }

        @Required
        public MenstruationState setOperationType(MenstruationOperationType menstruationOperationType) {
            this.operation_type = menstruationOperationType;
            return this;
        }

        @Required
        public MenstruationOperationType getOperationType() {
            return this.operation_type;
        }

        public MenstruationState setMenstrualPeriodRecords(List<MenstrualPeriod> list) {
            this.menstrual_period_records = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<MenstrualPeriod>> getMenstrualPeriodRecords() {
            return this.menstrual_period_records;
        }

        public MenstruationState setMenstrualPeriodPredict(List<MenstrualPeriod> list) {
            this.menstrual_period_predict = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<MenstrualPeriod>> getMenstrualPeriodPredict() {
            return this.menstrual_period_predict;
        }
    }
}
