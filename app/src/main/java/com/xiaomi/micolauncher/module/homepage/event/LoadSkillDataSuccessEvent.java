package com.xiaomi.micolauncher.module.homepage.event;

import com.xiaomi.micolauncher.api.model.Skill;
import java.util.List;

/* loaded from: classes3.dex */
public class LoadSkillDataSuccessEvent {
    public List<Skill.SkillBean> skillBeans;

    public LoadSkillDataSuccessEvent(List<Skill.SkillBean> list) {
        this.skillBeans = list;
    }
}
