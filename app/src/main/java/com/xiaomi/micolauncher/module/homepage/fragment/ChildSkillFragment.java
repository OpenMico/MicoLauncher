package com.xiaomi.micolauncher.module.homepage.fragment;

import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.skill.DomainConfig;
import com.xiaomi.micolauncher.module.homepage.SkillPath;
import com.xiaomi.micolauncher.module.skill.bean.SkillApp;
import com.xiaomi.micolauncher.module.skill.manager.SkillStatHelper;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildSkillFragment extends BaseSkillFragment {
    public static final String TAG = "ChildSkillFragment";

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseSkillFragment, com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public String identifier() {
        return TAG;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseSkillFragment, com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void recordShow() {
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseSkillFragment
    public List<Object> buildAppData() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new SkillApp(R.drawable.kidskill_phonecall, SkillPath.CONTACTS, true, "call"));
        arrayList.add(new SkillApp(R.drawable.kidskill_schedule, SkillPath.ALARM, false, "alarm"));
        arrayList.add(new SkillApp(R.drawable.kidskill_book, SkillPath.GKID, true, SkillStatHelper.SKILL_STAT_GKID));
        arrayList.add(new SkillApp(R.drawable.kidskill_sound, SkillPath.WHITE_NOISE, true, SkillStatHelper.SKILL_STAT_WHITE_NOISE));
        arrayList.add(new SkillApp(R.drawable.kidskill_photos, SkillPath.PHOTO, false, SkillStatHelper.SKILL_STAT_PHOTO));
        arrayList.add(new SkillApp(R.drawable.kidskill_weather, SkillPath.WEATHER, true, DomainConfig.Weather.DOMAIN_NAME));
        arrayList.add(new SkillApp(R.drawable.kids_skill_51talk, SkillPath.TALK51, true, "51talk"));
        arrayList.add(new SkillApp(R.drawable.kids_skill_schooltimetable, SkillPath.CURRICULUM, true, "schedule"));
        return arrayList;
    }
}
