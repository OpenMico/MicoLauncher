package com.xiaomi.micolauncher.skills.ancientpoem;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.mico.common.ContainerUtil;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class AncientPoemEntity implements Parcelable {
    public static final Parcelable.Creator<AncientPoemEntity> CREATOR = new Parcelable.Creator<AncientPoemEntity>() { // from class: com.xiaomi.micolauncher.skills.ancientpoem.AncientPoemEntity.1
        /* renamed from: a */
        public AncientPoemEntity createFromParcel(Parcel parcel) {
            return new AncientPoemEntity(parcel);
        }

        /* renamed from: a */
        public AncientPoemEntity[] newArray(int i) {
            return new AncientPoemEntity[i];
        }
    };
    public String audioId;
    public List<String> authors;
    public String dynasty;
    public String meaning;
    public String name;
    public String verse;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AncientPoemEntity() {
    }

    protected AncientPoemEntity(Parcel parcel) {
        this.audioId = parcel.readString();
        this.name = parcel.readString();
        this.authors = parcel.createStringArrayList();
        this.verse = parcel.readString();
        this.dynasty = parcel.readString();
        this.meaning = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.audioId);
        parcel.writeString(this.name);
        parcel.writeStringList(this.authors);
        parcel.writeString(this.verse);
        parcel.writeString(this.dynasty);
        parcel.writeString(this.meaning);
    }

    public String getAudioId() {
        return this.audioId;
    }

    public void setAudioId(String str) {
        this.audioId = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public List<String> getAuthors() {
        return this.authors;
    }

    public void setAuthors(List<String> list) {
        this.authors = list;
    }

    public String getVerse() {
        return this.verse;
    }

    public void setVerse(String str) {
        this.verse = str;
    }

    public String getDynasty() {
        return this.dynasty;
    }

    public void setDynasty(String str) {
        this.dynasty = str;
    }

    public String getMeaning() {
        return this.meaning;
    }

    public void setMeaning(String str) {
        this.meaning = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String a() {
        return !ContainerUtil.isEmpty(this.authors) ? String.join(StringUtils.SPACE, this.authors) : "";
    }
}
