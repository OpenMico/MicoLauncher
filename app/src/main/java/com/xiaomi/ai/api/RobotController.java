package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;

/* loaded from: classes3.dex */
public class RobotController {

    /* loaded from: classes3.dex */
    public enum RobotAction {
        STANDING(0),
        DOWN(1),
        COME(2),
        BACK(3),
        GO_ROUND(4),
        HIGH_FIVE(5),
        BACK_SOMERSAULT(6),
        DANCE(7),
        MOVE_FORWARD(8),
        FOLLOW_ME(9),
        CHANGE_HANDS(10),
        SIT_DOWN(11),
        JUMP(12),
        LOVE_BEHAVE(13),
        WAGGING_TAIL(14),
        SHAKE_HEAD(15),
        NOD(16),
        BOW(17),
        PLAY_DEAD(18),
        GO_SLEEP(19),
        BALLET(20),
        SPACE_WALK(21);
        
        private int id;

        RobotAction(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "Operate", namespace = AIApiConstants.RobotController.NAME)
    /* loaded from: classes3.dex */
    public static class Operate implements InstructionPayload {
        @Required
        private RobotAction action;
        private Optional<Integer> count = Optional.empty();

        public Operate() {
        }

        public Operate(RobotAction robotAction) {
            this.action = robotAction;
        }

        @Required
        public Operate setAction(RobotAction robotAction) {
            this.action = robotAction;
            return this;
        }

        @Required
        public RobotAction getAction() {
            return this.action;
        }

        public Operate setCount(int i) {
            this.count = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getCount() {
            return this.count;
        }
    }
}
