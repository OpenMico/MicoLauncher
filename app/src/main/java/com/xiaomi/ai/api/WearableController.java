package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class WearableController {

    /* loaded from: classes3.dex */
    public enum ActivityStatus {
        ONGOING(0),
        PAUSED(1),
        STOPPED(2);
        
        private int id;

        ActivityStatus(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum DirectiveType {
        MEASURE_HEART_RATE(0),
        OPEN_PRESSURE(1),
        OPEN_ENERGY(2),
        OPEN_STAND_FREQUENCY(3),
        OPEN_SLEEP(4),
        OPEN_MAXIMAL_OXYGEN_UPTAKE(5),
        OPEN_STEP(6),
        OPEN_CALORIES(7),
        OPEN_SPORTS(8),
        OPEN_BLOOD_OXYGEN_SATURATION(9),
        OPEN_TRAINING_EXERCISE(10),
        OPEN_WOMEN_HEALTH(11),
        OPEN_ACTIVITY_GOAL(12),
        OPEN_ACTIVITY_RECORD(13),
        OPEN_EXERCISE_RECORD(14),
        OPEN_SPORT_MODE(15);
        
        private int id;

        DirectiveType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum Mode {
        UNKNOWN(-1),
        RUNNING_INDOOR(0),
        RUNNING_OUTDOOR(1),
        WAKING_OUTDOOR(2),
        SWIMMING_INDOOR(3),
        SWIMMING_OUTDOOR(4),
        BIKING_INDOOR(5),
        BIKING_OUTDOOR(6),
        CROSS_COUNTRY(7),
        ACTIVITY(8),
        ELLIPTICAL_TRAINER(9),
        SKIING(10),
        TENNIS(11),
        FOOTBALL(12),
        JUMPING(13),
        CLIMBING(14),
        ZUMBA(15),
        FREE_SPARRING(16),
        DIRT_SKI(17),
        BADMINTON(18),
        YOGA(19),
        BICYCLE_MOTOCROSS(20),
        DANCE(21),
        KONGFU(22),
        GYMNASTICS(23),
        THAI_BOXING(24),
        TAI_CHI(25),
        TEAKWONDO(26),
        STEP_TRAINING(27),
        STEPPER(28),
        DOUBLE_BOARD_SKIING(29),
        WRESTLING(30),
        HANDBALL(31),
        INDOOR_FITNESS(32),
        INDOOR_SKATING(33),
        ARCHERY(34),
        MOUNTAIN_RIDE(35),
        SOFT_TRAINING(36),
        JUDO(37),
        BOXING(38),
        PILATES(39),
        TABLE_TENNIS(40),
        TREADMILL(41),
        ROCK_CLIMBING(42),
        VOLLEYBAL(43),
        CLIMBE(44),
        GATEBALL(45),
        HORSEMANSHIP(46),
        ROLLER_SKATING(47),
        WEIGHT_TRAINING(48),
        SOFTBALL(49),
        BASKETBALL(50),
        STRETCHING(51),
        KARATE(52),
        HIPHOP(53),
        SOCIAL_DANCING(54),
        SURFING(55),
        FENCING(56),
        MIXED_AEROBIC(57),
        ROWING_MACHINE(58),
        OUTDOOR_SKATING(59),
        CORE_TRAINING(60),
        SQUARE_DANCING(61),
        MOUNTAIN_SKI(62),
        HIIT(63),
        YACHTING(64),
        BELLY_DANCE(65),
        FISHING(66),
        SNOWBOARD_SKI(67),
        HUNT(68),
        CURLING(69),
        SQUASH_RACKETS(70),
        BOWLING(71),
        BASEBALL(72),
        CRICKET(73),
        BALLET(74);
        
        private int id;

        Mode(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum OperationType {
        START(0),
        END(1),
        PAUSE(2),
        RESUME(3);
        
        private int id;

        OperationType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum PropertyName {
        COUNTDOWN(0),
        SPEED(1),
        GRIPPING_HAND(2),
        SPORTS_REPORT(3),
        CALORIES(4),
        CALORIES_TARGET(5),
        CALORIES_BURNED(6),
        CALORIES_REMAINING(7),
        MILEAGE(8),
        MILEAGE_TARGET(9),
        MILEAGE_CONSUMED(10),
        MILEAGE_REMAINING(11),
        TIME(12),
        TIME_TARGET(13),
        TIME_CONSUMED(14),
        TIME_REMAINING(15),
        HEART_RATE(16),
        PRESSURE(17),
        ENERGY(18),
        STEPS(19),
        STANDING_TIME(20),
        STANDING_FREQUENCY(21),
        SLEEP(22),
        SLEEP_TIME(23),
        DEEP_SLEEP_TIME(24),
        SLEEP_SCORE(25),
        MAXIMAL_OXYGEN_UPTAKE(26),
        JUMPING_FREQUENCY(27),
        JUMPING_FREQUENCY_TARGET(28),
        JUMPING_FREQUENCY_REMAINING(29),
        JUMPING_FREQUENCY_CONSUMED(30),
        CALORIES_TARGET_TODAY(31),
        CALORIES_BURNED_TODAY(32),
        CALORIES_REMAINING_TODAY(33),
        ETE_SLEEP_FEEDBACK(34),
        STEPS_TARGET(35),
        STEPS_CONSUMED(36),
        STEPS_REMAINING(37),
        STANDING_FREQUENCY_TARGET(38),
        STANDING_FREQUENCY_CONSUMED(39),
        STANDING_FREQUENCY_REMAINING(40),
        LATEST_HEART_RATE_MEASURING_PASSED(41),
        HEART_RATE_MEASURE(42),
        SLEEP_SUGGESTION(43),
        SHALLOW_SLEEP_TIME(44),
        RAPID_EYE_MOVE_TIME(45),
        FALL_SLEEP_TIME(46),
        WAKE_TIME(47),
        BLOOD_OXYGEN_SATURATION(48),
        TRAINING_EXERCISE(49),
        WOMEN_HEALTH(50),
        ACTIVITY_GOAL(51),
        ACTIVITY_RECORD(52),
        EXERCISE_RECORD(53),
        SPORT_MODE(54);
        
        private int id;

        PropertyName(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum UnitType {
        METER(0),
        KILOMETER(1),
        HOUR(2),
        MINUTE(3),
        SECOND(4),
        KILOJOULE(5),
        KILOCALORIES(6),
        FREQUENCY(7),
        PERCENT(8);
        
        private int id;

        UnitType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ValueType {
        STRING(0),
        INT(1),
        DOUBLE(2);
        
        private int id;

        ValueType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "CheckStatus", namespace = AIApiConstants.WearableController.NAME)
    /* loaded from: classes3.dex */
    public static class CheckStatus implements InstructionPayload {
        private Optional<Mode> required_mode = Optional.empty();
        @Required
        private List<PropertyEntity> required_properties;

        public CheckStatus() {
        }

        public CheckStatus(List<PropertyEntity> list) {
            this.required_properties = list;
        }

        @Required
        public CheckStatus setRequiredProperties(List<PropertyEntity> list) {
            this.required_properties = list;
            return this;
        }

        @Required
        public List<PropertyEntity> getRequiredProperties() {
            return this.required_properties;
        }

        public CheckStatus setRequiredMode(Mode mode) {
            this.required_mode = Optional.ofNullable(mode);
            return this;
        }

        public Optional<Mode> getRequiredMode() {
            return this.required_mode;
        }
    }

    @NamespaceName(name = "Execute", namespace = AIApiConstants.WearableController.NAME)
    /* loaded from: classes3.dex */
    public static class Execute implements InstructionPayload {
        @Required
        private DirectiveType directive;

        public Execute() {
        }

        public Execute(DirectiveType directiveType) {
            this.directive = directiveType;
        }

        @Required
        public Execute setDirective(DirectiveType directiveType) {
            this.directive = directiveType;
            return this;
        }

        @Required
        public DirectiveType getDirective() {
            return this.directive;
        }
    }

    /* loaded from: classes3.dex */
    public static class PropertyEntity {
        @Required
        private boolean enabled;
        @Required
        private PropertyName name;
        private Optional<PropertyValue> value = Optional.empty();
        private Optional<Integer> name_id = Optional.empty();

        public PropertyEntity() {
        }

        public PropertyEntity(PropertyName propertyName, boolean z) {
            this.name = propertyName;
            this.enabled = z;
        }

        @Required
        public PropertyEntity setName(PropertyName propertyName) {
            this.name = propertyName;
            return this;
        }

        @Required
        public PropertyName getName() {
            return this.name;
        }

        @Required
        public PropertyEntity setEnabled(boolean z) {
            this.enabled = z;
            return this;
        }

        @Required
        public boolean isEnabled() {
            return this.enabled;
        }

        public PropertyEntity setValue(PropertyValue propertyValue) {
            this.value = Optional.ofNullable(propertyValue);
            return this;
        }

        public Optional<PropertyValue> getValue() {
            return this.value;
        }

        public PropertyEntity setNameId(int i) {
            this.name_id = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getNameId() {
            return this.name_id;
        }
    }

    @NamespaceName(name = "PropertyResults", namespace = AIApiConstants.WearableController.NAME)
    /* loaded from: classes3.dex */
    public static class PropertyResults implements EventPayload {
        @Required
        private List<PropertyEntity> results;
        private Optional<Mode> current_mode = Optional.empty();
        private Optional<ActivityStatus> activity_status = Optional.empty();

        public PropertyResults() {
        }

        public PropertyResults(List<PropertyEntity> list) {
            this.results = list;
        }

        @Required
        public PropertyResults setResults(List<PropertyEntity> list) {
            this.results = list;
            return this;
        }

        @Required
        public List<PropertyEntity> getResults() {
            return this.results;
        }

        public PropertyResults setCurrentMode(Mode mode) {
            this.current_mode = Optional.ofNullable(mode);
            return this;
        }

        public Optional<Mode> getCurrentMode() {
            return this.current_mode;
        }

        public PropertyResults setActivityStatus(ActivityStatus activityStatus) {
            this.activity_status = Optional.ofNullable(activityStatus);
            return this;
        }

        public Optional<ActivityStatus> getActivityStatus() {
            return this.activity_status;
        }
    }

    /* loaded from: classes3.dex */
    public static class PropertyValue {
        private Optional<UnitType> unitType = Optional.empty();
        @Required
        private String value;
        @Required
        private ValueType valueType;

        public PropertyValue() {
        }

        public PropertyValue(String str, ValueType valueType) {
            this.value = str;
            this.valueType = valueType;
        }

        @Required
        public PropertyValue setValue(String str) {
            this.value = str;
            return this;
        }

        @Required
        public String getValue() {
            return this.value;
        }

        @Required
        public PropertyValue setValueType(ValueType valueType) {
            this.valueType = valueType;
            return this;
        }

        @Required
        public ValueType getValueType() {
            return this.valueType;
        }

        public PropertyValue setUnitType(UnitType unitType) {
            this.unitType = Optional.ofNullable(unitType);
            return this;
        }

        public Optional<UnitType> getUnitType() {
            return this.unitType;
        }
    }

    @NamespaceName(name = "SetProperty", namespace = AIApiConstants.WearableController.NAME)
    /* loaded from: classes3.dex */
    public static class SetProperty implements InstructionPayload {
        @Required
        private Mode mode;
        private Optional<Integer> mode_id = Optional.empty();
        @Required
        private List<PropertyEntity> properties;

        public SetProperty() {
        }

        public SetProperty(Mode mode, List<PropertyEntity> list) {
            this.mode = mode;
            this.properties = list;
        }

        @Required
        public SetProperty setMode(Mode mode) {
            this.mode = mode;
            return this;
        }

        @Required
        public Mode getMode() {
            return this.mode;
        }

        @Required
        public SetProperty setProperties(List<PropertyEntity> list) {
            this.properties = list;
            return this;
        }

        @Required
        public List<PropertyEntity> getProperties() {
            return this.properties;
        }

        public SetProperty setModeId(int i) {
            this.mode_id = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getModeId() {
            return this.mode_id;
        }
    }

    @NamespaceName(name = "Switch", namespace = AIApiConstants.WearableController.NAME)
    /* loaded from: classes3.dex */
    public static class Switch implements InstructionPayload {
        @Required
        private Mode mode;
        private Optional<Integer> mode_id = Optional.empty();
        @Required
        private OperationType operation;
        @Required
        private List<PropertyEntity> properties;

        public Switch() {
        }

        public Switch(OperationType operationType, Mode mode, List<PropertyEntity> list) {
            this.operation = operationType;
            this.mode = mode;
            this.properties = list;
        }

        @Required
        public Switch setOperation(OperationType operationType) {
            this.operation = operationType;
            return this;
        }

        @Required
        public OperationType getOperation() {
            return this.operation;
        }

        @Required
        public Switch setMode(Mode mode) {
            this.mode = mode;
            return this;
        }

        @Required
        public Mode getMode() {
            return this.mode;
        }

        @Required
        public Switch setProperties(List<PropertyEntity> list) {
            this.properties = list;
            return this;
        }

        @Required
        public List<PropertyEntity> getProperties() {
            return this.properties;
        }

        public Switch setModeId(int i) {
            this.mode_id = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getModeId() {
            return this.mode_id;
        }
    }
}
