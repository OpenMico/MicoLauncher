package com.xiaomi.micolauncher.common.statemodel;

import com.xiaomi.micolauncher.common.statemodel.StateModel;
import com.xiaomi.micolauncher.skills.openplatform.model.OpenPlatformModel;

/* loaded from: classes3.dex */
public class StateModelHelper {
    public static boolean isStateWithUIGoingOn() {
        StateModel.StateInfo curStateInfo = StateModel.getInstance().getCurStateInfo();
        return curStateInfo != null && curStateInfo.equals(fromSkillClass(OpenPlatformModel.class));
    }

    public static StateModel.StateInfo fromSkill(Class cls) {
        return new StateModel.StateInfo(cls.getName());
    }

    public static StateModel.StateInfo fromSkillClass(Class cls) {
        return new StateModel.StateInfo(cls.getName());
    }
}
