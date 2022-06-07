package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Nlp;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class Alerts {

    @NamespaceName(name = "ChimeHourly", namespace = AIApiConstants.Alerts.NAME)
    /* loaded from: classes3.dex */
    public static class ChimeHourly implements EventPayload {
    }

    @NamespaceName(name = "StopAlert", namespace = AIApiConstants.Alerts.NAME)
    /* loaded from: classes3.dex */
    public static class StopAlert implements InstructionPayload {
    }

    /* loaded from: classes3.dex */
    public enum AlertCircleType {
        UNKNOWN(-1),
        ONCE(0),
        EVERYDAY(1),
        WORKDAY(2),
        MONDAY_TO_FRIDAY(3),
        HOLIDAY(4),
        WEEKEND(5),
        WEEKLY(6),
        MONTHLY(7),
        YEARLY(8);
        
        private int id;

        AlertCircleType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum AlertFilterType {
        UNKNOWN(-1),
        RECENTLY_SET(0),
        RECENTLY_CALL(1),
        ON_STATUS(2),
        OFF_STATUS(3);
        
        private int id;

        AlertFilterType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum AlertOperation {
        UNKNOWN(-1),
        OPEN(0),
        CLOSE(1),
        PAUSE(2),
        PROCEED(3),
        QUERY(4),
        DELETE(5),
        AMEND(6);
        
        private int id;

        AlertOperation(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum AlertStatus {
        UNKNOWN(-1),
        OFF(0),
        ON(1),
        SUSPEND(2);
        
        private int id;

        AlertStatus(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum AlertTimeType {
        UNKNOWN(-1),
        DATETIME(0),
        DURATION(1),
        OFFSET(2),
        INDETER_DATETIME(3);
        
        private int id;

        AlertTimeType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum AlertType {
        UNKNOWN(-1),
        ALARM(0),
        REMINDER(1),
        TIMER(2),
        BIRTHDAY(3),
        ANNIVERSARY(4);
        
        private int id;

        AlertType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum DateType {
        GREGORIAN(0),
        LUNAR(1);
        
        private int id;

        DateType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ExamItemType {
        UNKNOWN(-1),
        SIGN_UP(0),
        PRINT_ADMISSION_CARD(1),
        EXAM(2),
        QUERY_SCORE(3);
        
        private int id;

        ExamItemType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum RemindType {
        UNKNOWN(-1),
        PUSH(0),
        ALARM(1);
        
        private int id;

        RemindType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ResourceType {
        UNKNOWN(0),
        MUSIC(1),
        NEWS(2);
        
        private int id;

        ResourceType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public static class AlertDateTime {
        @Required
        private long timestamp;
        @Required
        private String value;

        public AlertDateTime() {
        }

        public AlertDateTime(String str, long j) {
            this.value = str;
            this.timestamp = j;
        }

        @Required
        public AlertDateTime setValue(String str) {
            this.value = str;
            return this;
        }

        @Required
        public String getValue() {
            return this.value;
        }

        @Required
        public AlertDateTime setTimestamp(long j) {
            this.timestamp = j;
            return this;
        }

        @Required
        public long getTimestamp() {
            return this.timestamp;
        }
    }

    /* loaded from: classes3.dex */
    public static class AlertItem {
        @Required
        private AlertCircleType circle;
        @Required
        private String datetime;
        @Required
        private String id;
        @Required
        private AlertStatus status;
        @Required
        private AlertType type;
        private Optional<String> circle_extra = Optional.empty();
        private Optional<String> offset = Optional.empty();
        private Optional<String> event = Optional.empty();
        private Optional<String> reminder = Optional.empty();
        private Optional<String> reminder_url = Optional.empty();
        private Optional<Integer> volume = Optional.empty();
        private Optional<String> ringtone = Optional.empty();
        private Optional<String> update_datetime = Optional.empty();
        private Optional<String> disable_datetime = Optional.empty();
        private Optional<String> ringtone_uri = Optional.empty();
        private Optional<String> ringtone_type = Optional.empty();
        private Optional<String> ringtone_query = Optional.empty();
        private Optional<Integer> advance_reminder = Optional.empty();
        private Optional<Integer> repeat_ringing = Optional.empty();
        private Optional<String> time_reminder = Optional.empty();
        private Optional<Long> offset_remained = Optional.empty();
        private Optional<ReminderParam> reminder_param = Optional.empty();
        private Optional<DateType> date_type = Optional.empty();
        private Optional<Boolean> use_year = Optional.empty();
        private Optional<String> birth_name = Optional.empty();
        @Deprecated
        private Optional<Template.Image> icon = Optional.empty();

        public AlertItem() {
        }

        public AlertItem(String str, AlertType alertType, AlertStatus alertStatus, String str2, AlertCircleType alertCircleType) {
            this.id = str;
            this.type = alertType;
            this.status = alertStatus;
            this.datetime = str2;
            this.circle = alertCircleType;
        }

        @Required
        public AlertItem setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public AlertItem setType(AlertType alertType) {
            this.type = alertType;
            return this;
        }

        @Required
        public AlertType getType() {
            return this.type;
        }

        @Required
        public AlertItem setStatus(AlertStatus alertStatus) {
            this.status = alertStatus;
            return this;
        }

        @Required
        public AlertStatus getStatus() {
            return this.status;
        }

        @Required
        public AlertItem setDatetime(String str) {
            this.datetime = str;
            return this;
        }

        @Required
        public String getDatetime() {
            return this.datetime;
        }

        @Required
        public AlertItem setCircle(AlertCircleType alertCircleType) {
            this.circle = alertCircleType;
            return this;
        }

        @Required
        public AlertCircleType getCircle() {
            return this.circle;
        }

        public AlertItem setCircleExtra(String str) {
            this.circle_extra = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCircleExtra() {
            return this.circle_extra;
        }

        public AlertItem setOffset(String str) {
            this.offset = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOffset() {
            return this.offset;
        }

        public AlertItem setEvent(String str) {
            this.event = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEvent() {
            return this.event;
        }

        public AlertItem setReminder(String str) {
            this.reminder = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getReminder() {
            return this.reminder;
        }

        public AlertItem setReminderUrl(String str) {
            this.reminder_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getReminderUrl() {
            return this.reminder_url;
        }

        public AlertItem setVolume(int i) {
            this.volume = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getVolume() {
            return this.volume;
        }

        public AlertItem setRingtone(String str) {
            this.ringtone = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRingtone() {
            return this.ringtone;
        }

        public AlertItem setUpdateDatetime(String str) {
            this.update_datetime = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUpdateDatetime() {
            return this.update_datetime;
        }

        public AlertItem setDisableDatetime(String str) {
            this.disable_datetime = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDisableDatetime() {
            return this.disable_datetime;
        }

        public AlertItem setRingtoneUri(String str) {
            this.ringtone_uri = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRingtoneUri() {
            return this.ringtone_uri;
        }

        public AlertItem setRingtoneType(String str) {
            this.ringtone_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRingtoneType() {
            return this.ringtone_type;
        }

        public AlertItem setRingtoneQuery(String str) {
            this.ringtone_query = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRingtoneQuery() {
            return this.ringtone_query;
        }

        public AlertItem setAdvanceReminder(int i) {
            this.advance_reminder = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getAdvanceReminder() {
            return this.advance_reminder;
        }

        public AlertItem setRepeatRinging(int i) {
            this.repeat_ringing = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getRepeatRinging() {
            return this.repeat_ringing;
        }

        public AlertItem setTimeReminder(String str) {
            this.time_reminder = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTimeReminder() {
            return this.time_reminder;
        }

        public AlertItem setOffsetRemained(long j) {
            this.offset_remained = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getOffsetRemained() {
            return this.offset_remained;
        }

        public AlertItem setReminderParam(ReminderParam reminderParam) {
            this.reminder_param = Optional.ofNullable(reminderParam);
            return this;
        }

        public Optional<ReminderParam> getReminderParam() {
            return this.reminder_param;
        }

        public AlertItem setDateType(DateType dateType) {
            this.date_type = Optional.ofNullable(dateType);
            return this;
        }

        public Optional<DateType> getDateType() {
            return this.date_type;
        }

        public AlertItem setUseYear(boolean z) {
            this.use_year = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isUseYear() {
            return this.use_year;
        }

        public AlertItem setBirthName(String str) {
            this.birth_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBirthName() {
            return this.birth_name;
        }

        @Deprecated
        public AlertItem setIcon(Template.Image image) {
            this.icon = Optional.ofNullable(image);
            return this;
        }

        @Deprecated
        public Optional<Template.Image> getIcon() {
            return this.icon;
        }
    }

    /* loaded from: classes3.dex */
    public static class AlertOpItem {
        @Required
        private String id;
        @Required
        private AlertType type;
        private Optional<String> disable_datetime = Optional.empty();
        private Optional<String> datetime = Optional.empty();
        private Optional<String> event = Optional.empty();

        public AlertOpItem() {
        }

        public AlertOpItem(String str, AlertType alertType) {
            this.id = str;
            this.type = alertType;
        }

        @Required
        public AlertOpItem setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public AlertOpItem setType(AlertType alertType) {
            this.type = alertType;
            return this;
        }

        @Required
        public AlertType getType() {
            return this.type;
        }

        public AlertOpItem setDisableDatetime(String str) {
            this.disable_datetime = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDisableDatetime() {
            return this.disable_datetime;
        }

        public AlertOpItem setDatetime(String str) {
            this.datetime = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDatetime() {
            return this.datetime;
        }

        public AlertOpItem setEvent(String str) {
            this.event = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEvent() {
            return this.event;
        }
    }

    /* loaded from: classes3.dex */
    public static class AlertTimeInfo {
        @Required
        private String token;
        @Required
        private AlertTimeType type;
        private Optional<AlertDateTime> datetime = Optional.empty();
        private Optional<AlertDateTime> start_datetime = Optional.empty();
        private Optional<AlertDateTime> end_datetime = Optional.empty();

        public AlertTimeInfo() {
        }

        public AlertTimeInfo(AlertTimeType alertTimeType, String str) {
            this.type = alertTimeType;
            this.token = str;
        }

        @Required
        public AlertTimeInfo setType(AlertTimeType alertTimeType) {
            this.type = alertTimeType;
            return this;
        }

        @Required
        public AlertTimeType getType() {
            return this.type;
        }

        @Required
        public AlertTimeInfo setToken(String str) {
            this.token = str;
            return this;
        }

        @Required
        public String getToken() {
            return this.token;
        }

        public AlertTimeInfo setDatetime(AlertDateTime alertDateTime) {
            this.datetime = Optional.ofNullable(alertDateTime);
            return this;
        }

        public Optional<AlertDateTime> getDatetime() {
            return this.datetime;
        }

        public AlertTimeInfo setStartDatetime(AlertDateTime alertDateTime) {
            this.start_datetime = Optional.ofNullable(alertDateTime);
            return this;
        }

        public Optional<AlertDateTime> getStartDatetime() {
            return this.start_datetime;
        }

        public AlertTimeInfo setEndDatetime(AlertDateTime alertDateTime) {
            this.end_datetime = Optional.ofNullable(alertDateTime);
            return this;
        }

        public Optional<AlertDateTime> getEndDatetime() {
            return this.end_datetime;
        }
    }

    @NamespaceName(name = "AlertsResult", namespace = AIApiConstants.Alerts.NAME)
    /* loaded from: classes3.dex */
    public static class AlertsResult implements EventPayload {
        @Required
        private List<AlertItem> alerts;

        public AlertsResult() {
        }

        public AlertsResult(List<AlertItem> list) {
            this.alerts = list;
        }

        @Required
        public AlertsResult setAlerts(List<AlertItem> list) {
            this.alerts = list;
            return this;
        }

        @Required
        public List<AlertItem> getAlerts() {
            return this.alerts;
        }
    }

    @NamespaceName(name = "DeleteAlert", namespace = AIApiConstants.Alerts.NAME)
    @Deprecated
    /* loaded from: classes3.dex */
    public static class DeleteAlert implements InstructionPayload {
        @Required
        private String id;
        @Required
        private AlertType type;

        public DeleteAlert() {
        }

        public DeleteAlert(String str, AlertType alertType) {
            this.id = str;
            this.type = alertType;
        }

        @Required
        public DeleteAlert setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public DeleteAlert setType(AlertType alertType) {
            this.type = alertType;
            return this;
        }

        @Required
        public AlertType getType() {
            return this.type;
        }
    }

    @NamespaceName(name = "DeleteAlerts", namespace = AIApiConstants.Alerts.NAME)
    /* loaded from: classes3.dex */
    public static class DeleteAlerts implements InstructionPayload {
        @Required
        private List<AlertOpItem> alerts;

        public DeleteAlerts() {
        }

        public DeleteAlerts(List<AlertOpItem> list) {
            this.alerts = list;
        }

        @Required
        public DeleteAlerts setAlerts(List<AlertOpItem> list) {
            this.alerts = list;
            return this;
        }

        @Required
        public List<AlertOpItem> getAlerts() {
            return this.alerts;
        }
    }

    @NamespaceName(name = "DeliverAlertIntention", namespace = AIApiConstants.Alerts.NAME)
    /* loaded from: classes3.dex */
    public static class DeliverAlertIntention implements InstructionPayload {
        @Required
        private boolean operate_all;
        @Required
        private AlertOperation operation;
        @Required
        private AlertType type;
        private Optional<AlertCircleType> circle = Optional.empty();
        private Optional<String> circle_extra = Optional.empty();
        private Optional<String> event = Optional.empty();
        private Optional<AlertFilterType> filter_type = Optional.empty();
        private Optional<AlertTimeInfo> time = Optional.empty();

        public DeliverAlertIntention() {
        }

        public DeliverAlertIntention(AlertType alertType, AlertOperation alertOperation, boolean z) {
            this.type = alertType;
            this.operation = alertOperation;
            this.operate_all = z;
        }

        @Required
        public DeliverAlertIntention setType(AlertType alertType) {
            this.type = alertType;
            return this;
        }

        @Required
        public AlertType getType() {
            return this.type;
        }

        @Required
        public DeliverAlertIntention setOperation(AlertOperation alertOperation) {
            this.operation = alertOperation;
            return this;
        }

        @Required
        public AlertOperation getOperation() {
            return this.operation;
        }

        public DeliverAlertIntention setCircle(AlertCircleType alertCircleType) {
            this.circle = Optional.ofNullable(alertCircleType);
            return this;
        }

        public Optional<AlertCircleType> getCircle() {
            return this.circle;
        }

        public DeliverAlertIntention setCircleExtra(String str) {
            this.circle_extra = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCircleExtra() {
            return this.circle_extra;
        }

        public DeliverAlertIntention setEvent(String str) {
            this.event = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEvent() {
            return this.event;
        }

        public DeliverAlertIntention setFilterType(AlertFilterType alertFilterType) {
            this.filter_type = Optional.ofNullable(alertFilterType);
            return this;
        }

        public Optional<AlertFilterType> getFilterType() {
            return this.filter_type;
        }

        @Required
        public DeliverAlertIntention setOperateAll(boolean z) {
            this.operate_all = z;
            return this;
        }

        @Required
        public boolean isOperateAll() {
            return this.operate_all;
        }

        public DeliverAlertIntention setTime(AlertTimeInfo alertTimeInfo) {
            this.time = Optional.ofNullable(alertTimeInfo);
            return this;
        }

        public Optional<AlertTimeInfo> getTime() {
            return this.time;
        }
    }

    /* loaded from: classes3.dex */
    public static class ReminderParam {
        private Optional<String> end_datetime = Optional.empty();
        private Optional<Integer> cycle_count = Optional.empty();
        private Optional<Boolean> all_day = Optional.empty();
        private Optional<String> cycle_end_datetime = Optional.empty();
        private Optional<String> event_location = Optional.empty();
        private Optional<String> circle_rule = Optional.empty();

        public ReminderParam setEndDatetime(String str) {
            this.end_datetime = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEndDatetime() {
            return this.end_datetime;
        }

        public ReminderParam setCycleCount(int i) {
            this.cycle_count = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getCycleCount() {
            return this.cycle_count;
        }

        public ReminderParam setAllDay(boolean z) {
            this.all_day = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAllDay() {
            return this.all_day;
        }

        public ReminderParam setCycleEndDatetime(String str) {
            this.cycle_end_datetime = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCycleEndDatetime() {
            return this.cycle_end_datetime;
        }

        public ReminderParam setEventLocation(String str) {
            this.event_location = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEventLocation() {
            return this.event_location;
        }

        public ReminderParam setCircleRule(String str) {
            this.circle_rule = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCircleRule() {
            return this.circle_rule;
        }
    }

    /* loaded from: classes3.dex */
    public static class Ringtone {
        private Optional<String> uri = Optional.empty();
        private Optional<String> type = Optional.empty();
        private Optional<Nlp.InvokeNlpRequest> request = Optional.empty();

        public Ringtone setUri(String str) {
            this.uri = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUri() {
            return this.uri;
        }

        public Ringtone setType(String str) {
            this.type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getType() {
            return this.type;
        }

        public Ringtone setRequest(Nlp.InvokeNlpRequest invokeNlpRequest) {
            this.request = Optional.ofNullable(invokeNlpRequest);
            return this;
        }

        public Optional<Nlp.InvokeNlpRequest> getRequest() {
            return this.request;
        }
    }

    @NamespaceName(name = "SetAlert", namespace = AIApiConstants.Alerts.NAME)
    /* loaded from: classes3.dex */
    public static class SetAlert implements InstructionPayload {
        @Required
        private String datetime;
        @Required
        private String id;
        @Required
        private AlertType type;
        private Optional<AlertCircleType> circle = Optional.empty();
        private Optional<String> circle_extra = Optional.empty();
        private Optional<String> event = Optional.empty();
        private Optional<String> reminder = Optional.empty();
        private Optional<Integer> repeat_ringing = Optional.empty();
        private Optional<String> offset = Optional.empty();
        @Deprecated
        private Optional<Nlp.InvokeNlpRequest> invoke_nlp_request = Optional.empty();
        private Optional<Integer> advance_reminder = Optional.empty();
        private Optional<Ringtone> ringtone = Optional.empty();
        private Optional<String> time_reminder = Optional.empty();
        private Optional<String> end_datetime = Optional.empty();
        private Optional<RemindType> remind_type = Optional.empty();
        private Optional<DateType> date_type = Optional.empty();
        private Optional<Boolean> use_year = Optional.empty();
        private Optional<String> birth_name = Optional.empty();
        private Optional<ExamItemType> exam_item_type = Optional.empty();

        public SetAlert() {
        }

        public SetAlert(String str, AlertType alertType, String str2) {
            this.id = str;
            this.type = alertType;
            this.datetime = str2;
        }

        @Required
        public SetAlert setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public SetAlert setType(AlertType alertType) {
            this.type = alertType;
            return this;
        }

        @Required
        public AlertType getType() {
            return this.type;
        }

        @Required
        public SetAlert setDatetime(String str) {
            this.datetime = str;
            return this;
        }

        @Required
        public String getDatetime() {
            return this.datetime;
        }

        public SetAlert setCircle(AlertCircleType alertCircleType) {
            this.circle = Optional.ofNullable(alertCircleType);
            return this;
        }

        public Optional<AlertCircleType> getCircle() {
            return this.circle;
        }

        public SetAlert setCircleExtra(String str) {
            this.circle_extra = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCircleExtra() {
            return this.circle_extra;
        }

        public SetAlert setEvent(String str) {
            this.event = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEvent() {
            return this.event;
        }

        public SetAlert setReminder(String str) {
            this.reminder = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getReminder() {
            return this.reminder;
        }

        public SetAlert setRepeatRinging(int i) {
            this.repeat_ringing = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getRepeatRinging() {
            return this.repeat_ringing;
        }

        public SetAlert setOffset(String str) {
            this.offset = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOffset() {
            return this.offset;
        }

        @Deprecated
        public SetAlert setInvokeNlpRequest(Nlp.InvokeNlpRequest invokeNlpRequest) {
            this.invoke_nlp_request = Optional.ofNullable(invokeNlpRequest);
            return this;
        }

        @Deprecated
        public Optional<Nlp.InvokeNlpRequest> getInvokeNlpRequest() {
            return this.invoke_nlp_request;
        }

        public SetAlert setAdvanceReminder(int i) {
            this.advance_reminder = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getAdvanceReminder() {
            return this.advance_reminder;
        }

        public SetAlert setRingtone(Ringtone ringtone) {
            this.ringtone = Optional.ofNullable(ringtone);
            return this;
        }

        public Optional<Ringtone> getRingtone() {
            return this.ringtone;
        }

        public SetAlert setTimeReminder(String str) {
            this.time_reminder = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTimeReminder() {
            return this.time_reminder;
        }

        public SetAlert setEndDatetime(String str) {
            this.end_datetime = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEndDatetime() {
            return this.end_datetime;
        }

        public SetAlert setRemindType(RemindType remindType) {
            this.remind_type = Optional.ofNullable(remindType);
            return this;
        }

        public Optional<RemindType> getRemindType() {
            return this.remind_type;
        }

        public SetAlert setDateType(DateType dateType) {
            this.date_type = Optional.ofNullable(dateType);
            return this;
        }

        public Optional<DateType> getDateType() {
            return this.date_type;
        }

        public SetAlert setUseYear(boolean z) {
            this.use_year = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isUseYear() {
            return this.use_year;
        }

        public SetAlert setBirthName(String str) {
            this.birth_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBirthName() {
            return this.birth_name;
        }

        public SetAlert setExamItemType(ExamItemType examItemType) {
            this.exam_item_type = Optional.ofNullable(examItemType);
            return this;
        }

        public Optional<ExamItemType> getExamItemType() {
            return this.exam_item_type;
        }
    }

    @NamespaceName(name = "SetAlerts", namespace = AIApiConstants.Alerts.NAME)
    /* loaded from: classes3.dex */
    public static class SetAlerts implements InstructionPayload {
        @Required
        private List<SetAlert> alerts;
        private Optional<Long> event_time = Optional.empty();

        public SetAlerts() {
        }

        public SetAlerts(List<SetAlert> list) {
            this.alerts = list;
        }

        @Required
        public SetAlerts setAlerts(List<SetAlert> list) {
            this.alerts = list;
            return this;
        }

        @Required
        public List<SetAlert> getAlerts() {
            return this.alerts;
        }

        public SetAlerts setEventTime(long j) {
            this.event_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getEventTime() {
            return this.event_time;
        }
    }

    @NamespaceName(name = "SetTODO", namespace = AIApiConstants.Alerts.NAME)
    /* loaded from: classes3.dex */
    public static class SetTODO implements InstructionPayload {
        private Optional<String> datetime = Optional.empty();
        @Required
        private String todo;

        public SetTODO() {
        }

        public SetTODO(String str) {
            this.todo = str;
        }

        @Required
        public SetTODO setTodo(String str) {
            this.todo = str;
            return this;
        }

        @Required
        public String getTodo() {
            return this.todo;
        }

        public SetTODO setDatetime(String str) {
            this.datetime = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDatetime() {
            return this.datetime;
        }
    }

    @NamespaceName(name = "SmartAlarm", namespace = AIApiConstants.Alerts.NAME)
    /* loaded from: classes3.dex */
    public static class SmartAlarm implements InstructionPayload {
        @Required
        private String bgm_url;
        @Required
        private String tts_url;
        private Optional<String> resource_url = Optional.empty();
        private Optional<ResourceType> resource_type = Optional.empty();
        private Optional<String> tts_text = Optional.empty();

        public SmartAlarm() {
        }

        public SmartAlarm(String str, String str2) {
            this.tts_url = str;
            this.bgm_url = str2;
        }

        @Required
        public SmartAlarm setTtsUrl(String str) {
            this.tts_url = str;
            return this;
        }

        @Required
        public String getTtsUrl() {
            return this.tts_url;
        }

        @Required
        public SmartAlarm setBgmUrl(String str) {
            this.bgm_url = str;
            return this;
        }

        @Required
        public String getBgmUrl() {
            return this.bgm_url;
        }

        public SmartAlarm setResourceUrl(String str) {
            this.resource_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResourceUrl() {
            return this.resource_url;
        }

        public SmartAlarm setResourceType(ResourceType resourceType) {
            this.resource_type = Optional.ofNullable(resourceType);
            return this;
        }

        public Optional<ResourceType> getResourceType() {
            return this.resource_type;
        }

        public SmartAlarm setTtsText(String str) {
            this.tts_text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTtsText() {
            return this.tts_text;
        }
    }

    @NamespaceName(name = "StoreAlerts", namespace = AIApiConstants.Alerts.NAME)
    /* loaded from: classes3.dex */
    public static class StoreAlerts implements EventPayload {
        @Required
        private List<AlertItem> alert_items;

        public StoreAlerts() {
        }

        public StoreAlerts(List<AlertItem> list) {
            this.alert_items = list;
        }

        @Required
        public StoreAlerts setAlertItems(List<AlertItem> list) {
            this.alert_items = list;
            return this;
        }

        @Required
        public List<AlertItem> getAlertItems() {
            return this.alert_items;
        }
    }

    @NamespaceName(name = "UpdateAlertStatus", namespace = AIApiConstants.Alerts.NAME)
    @Deprecated
    /* loaded from: classes3.dex */
    public static class UpdateAlertStatus implements InstructionPayload {
        private Optional<String> disable_datetime = Optional.empty();
        @Required
        private String id;
        @Required
        private AlertOperation operation;
        @Required
        private AlertType type;

        public UpdateAlertStatus() {
        }

        public UpdateAlertStatus(String str, AlertType alertType, AlertOperation alertOperation) {
            this.id = str;
            this.type = alertType;
            this.operation = alertOperation;
        }

        @Required
        public UpdateAlertStatus setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public UpdateAlertStatus setType(AlertType alertType) {
            this.type = alertType;
            return this;
        }

        @Required
        public AlertType getType() {
            return this.type;
        }

        @Required
        public UpdateAlertStatus setOperation(AlertOperation alertOperation) {
            this.operation = alertOperation;
            return this;
        }

        @Required
        public AlertOperation getOperation() {
            return this.operation;
        }

        public UpdateAlertStatus setDisableDatetime(String str) {
            this.disable_datetime = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDisableDatetime() {
            return this.disable_datetime;
        }
    }

    @NamespaceName(name = "UpdateAlerts", namespace = AIApiConstants.Alerts.NAME)
    /* loaded from: classes3.dex */
    public static class UpdateAlerts implements InstructionPayload {
        @Required
        private List<AlertOpItem> alerts;
        @Required
        private AlertOperation operation;

        public UpdateAlerts() {
        }

        public UpdateAlerts(AlertOperation alertOperation, List<AlertOpItem> list) {
            this.operation = alertOperation;
            this.alerts = list;
        }

        @Required
        public UpdateAlerts setOperation(AlertOperation alertOperation) {
            this.operation = alertOperation;
            return this;
        }

        @Required
        public AlertOperation getOperation() {
            return this.operation;
        }

        @Required
        public UpdateAlerts setAlerts(List<AlertOpItem> list) {
            this.alerts = list;
            return this;
        }

        @Required
        public List<AlertOpItem> getAlerts() {
            return this.alerts;
        }
    }

    @NamespaceName(name = "UploadAlerts", namespace = AIApiConstants.Alerts.NAME)
    /* loaded from: classes3.dex */
    public static class UploadAlerts implements InstructionPayload {
        @Required
        private List<AlertType> type;

        public UploadAlerts() {
        }

        public UploadAlerts(List<AlertType> list) {
            this.type = list;
        }

        @Required
        public UploadAlerts setType(List<AlertType> list) {
            this.type = list;
            return this;
        }

        @Required
        public List<AlertType> getType() {
            return this.type;
        }
    }

    @NamespaceName(name = "VoiceAssistantSmartAlerts", namespace = AIApiConstants.Alerts.NAME)
    /* loaded from: classes3.dex */
    public static class VoiceAssistantSmartAlerts implements EventPayload {
        private Optional<Long> duration = Optional.empty();
        private Optional<Long> alert_timestamp = Optional.empty();

        public VoiceAssistantSmartAlerts setDuration(long j) {
            this.duration = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getDuration() {
            return this.duration;
        }

        public VoiceAssistantSmartAlerts setAlertTimestamp(long j) {
            this.alert_timestamp = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getAlertTimestamp() {
            return this.alert_timestamp;
        }
    }
}
