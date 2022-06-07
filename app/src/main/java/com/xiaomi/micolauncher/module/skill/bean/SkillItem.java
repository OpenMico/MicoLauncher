package com.xiaomi.micolauncher.module.skill.bean;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.micolauncher.api.model.SkillStore;
import java.util.List;

/* loaded from: classes3.dex */
public class SkillItem implements Parcelable {
    public static final Parcelable.Creator<SkillItem> CREATOR = new Parcelable.Creator<SkillItem>() { // from class: com.xiaomi.micolauncher.module.skill.bean.SkillItem.1
        /* renamed from: a */
        public SkillItem createFromParcel(Parcel parcel) {
            return new SkillItem(parcel);
        }

        /* renamed from: a */
        public SkillItem[] newArray(int i) {
            return new SkillItem[i];
        }
    };
    public String action;
    public String developerName;
    public String iconUrl;
    public String id;
    public List<String> instructions;
    public String name;
    public float rating;
    public List<String> tips;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isMiBrainSkill(Context context) {
        return false;
    }

    public SkillItem() {
    }

    public SkillItem(SkillStore.SkillV2 skillV2) {
        this.id = skillV2.skillId;
        this.name = skillV2.displayName;
        this.iconUrl = skillV2.getIcon();
        this.rating = skillV2.rating;
        this.action = skillV2.action;
        this.developerName = skillV2.developerName;
        this.tips = skillV2.getContents();
        this.instructions = skillV2.getInstructionList();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.iconUrl);
        parcel.writeFloat(this.rating);
        parcel.writeString(this.action);
        parcel.writeStringList(this.tips);
        parcel.writeStringList(this.instructions);
    }

    protected SkillItem(Parcel parcel) {
        this.id = parcel.readString();
        this.name = parcel.readString();
        this.iconUrl = parcel.readString();
        this.rating = parcel.readFloat();
        this.action = parcel.readString();
        this.tips = parcel.createStringArrayList();
        this.instructions = parcel.createStringArrayList();
    }
}
