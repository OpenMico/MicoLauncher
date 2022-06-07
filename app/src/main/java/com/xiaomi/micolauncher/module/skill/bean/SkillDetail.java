package com.xiaomi.micolauncher.module.skill.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.micolauncher.api.model.Skill;
import com.xiaomi.micolauncher.api.model.SkillStore;
import java.util.List;

/* loaded from: classes3.dex */
public class SkillDetail implements Parcelable {
    public static final Parcelable.Creator<SkillDetail> CREATOR = new Parcelable.Creator<SkillDetail>() { // from class: com.xiaomi.micolauncher.module.skill.bean.SkillDetail.1
        /* renamed from: a */
        public SkillDetail createFromParcel(Parcel parcel) {
            return new SkillDetail(parcel);
        }

        /* renamed from: a */
        public SkillDetail[] newArray(int i) {
            return new SkillDetail[i];
        }
    };
    public String iconUrl;
    public String id;
    public String imageUrl;
    public List<String> instructions;
    public String name;
    public List<String> tips;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SkillDetail(SkillStore.SkillV2 skillV2) {
        this.id = skillV2.skillId;
        this.name = skillV2.displayName;
        this.iconUrl = skillV2.getIcon();
        this.tips = skillV2.tips;
        this.instructions = skillV2.getContents();
    }

    public SkillDetail(SkillItem skillItem) {
        this.id = skillItem.id;
        this.name = skillItem.name;
        this.iconUrl = skillItem.iconUrl;
        this.tips = skillItem.tips;
        this.instructions = skillItem.instructions;
    }

    public SkillDetail(Skill.SkillInfo skillInfo) {
        this.id = skillInfo.id;
        this.name = skillInfo.name;
        this.imageUrl = skillInfo.bgImageUrl;
        this.tips = skillInfo.getTips();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.imageUrl);
        parcel.writeString(this.iconUrl);
        parcel.writeStringList(this.tips);
        parcel.writeStringList(this.instructions);
    }

    protected SkillDetail(Parcel parcel) {
        this.id = parcel.readString();
        this.name = parcel.readString();
        this.imageUrl = parcel.readString();
        this.iconUrl = parcel.readString();
        this.tips = parcel.createStringArrayList();
        this.instructions = parcel.createStringArrayList();
    }
}
