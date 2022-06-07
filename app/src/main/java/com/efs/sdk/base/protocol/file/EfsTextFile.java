package com.efs.sdk.base.protocol.file;

import android.text.TextUtils;
import com.efs.sdk.base.a.a.a;
import com.efs.sdk.base.a.c.b;
import com.efs.sdk.base.a.h.d;
import com.efs.sdk.base.protocol.file.section.AbsSection;
import com.efs.sdk.base.protocol.file.section.JSONSection;
import com.efs.sdk.base.protocol.file.section.KVSection;
import com.efs.sdk.base.protocol.file.section.TextSection;
import com.xiaomi.micolauncher.common.stat.PlaybackTrackHelper;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.OneTrack;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import xcrash.TombstoneParser;

/* loaded from: classes.dex */
public class EfsTextFile extends AbsFileLog {
    private static final String FILE_START = "*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***";
    private static final String SECTION_START = "--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---";
    private List<AbsSection> sectionList = new ArrayList();
    private String mLinkKey = null;
    private String mLinkID = null;
    private boolean mHasInitLinkInfo = false;

    public EfsTextFile(String str) {
        super(str);
    }

    private String changeToStr() {
        StringBuilder sb = new StringBuilder("*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***");
        sb.append("\n");
        int i = 0;
        for (AbsSection absSection : this.sectionList) {
            if (i > 0) {
                sb.append("--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---\n");
            }
            sb.append(absSection.changeToStr());
            i++;
        }
        return sb.toString();
    }

    public KVSection createAndAddKVSection(String str) {
        KVSection kVSection = new KVSection(str);
        this.sectionList.add(kVSection);
        return kVSection;
    }

    public TextSection createAndAddTextSection(String str) {
        TextSection textSection = new TextSection(str);
        this.sectionList.add(textSection);
        return textSection;
    }

    public JSONSection createAndAddJSONSection(String str) {
        JSONSection jSONSection = new JSONSection(str);
        this.sectionList.add(jSONSection);
        return jSONSection;
    }

    @Override // com.efs.sdk.base.protocol.ILogProtocol
    public void insertGlobal(b bVar) {
        insertCustomInfoSection();
        List<AbsSection> list = this.sectionList;
        String logType = getLogType();
        ArrayList arrayList = new ArrayList();
        KVSection kVSection = new KVSection("global_head");
        KVSection put = kVSection.put("type", logType).put("appid", bVar.a.get("appid")).put("wid", bVar.a.get("wid")).put(TombstoneParser.keyProcessId, bVar.a.get(TombstoneParser.keyProcessId)).put(OneTrack.Param.PKG, bVar.a.get(OneTrack.Param.PKG)).put("ver", bVar.a.get("ver")).put("vcode", bVar.a.get("vcode")).put("ps", bVar.a.get("ps")).put("stime", bVar.a.get("stime"));
        a.a();
        KVSection put2 = put.put("ctime", Long.valueOf(a.b() / 1000));
        a.a();
        put2.put("w_tm", Long.valueOf(a.b() / 1000)).put("sdk_ver", bVar.a.get("sdk_ver"));
        String valueOf = String.valueOf(bVar.b(OneTrack.Param.UID, ""));
        if (!TextUtils.isEmpty(valueOf)) {
            kVSection.put(OneTrack.Param.UID, valueOf);
        }
        arrayList.add(kVSection);
        KVSection kVSection2 = new KVSection("device_info");
        kVSection2.put("lang", bVar.a.get("lang")).put(Constants.PHONE_BRAND, bVar.a.get(Constants.PHONE_BRAND)).put("model", bVar.a.get("model")).put(PlaybackTrackHelper.ROM_TAG, bVar.a.get(PlaybackTrackHelper.ROM_TAG)).put("sdk", bVar.a.get("sdk")).put("dsp_h", bVar.a.get("dsp_h")).put("dsp_w", bVar.a.get("dsp_w")).put("tzone", bVar.a.get("tzone")).put(OneTrack.Param.NET, bVar.a.get(OneTrack.Param.NET)).put("fr", bVar.a.get("fr"));
        arrayList.add(kVSection2);
        list.addAll(0, arrayList);
    }

    private void insertCustomInfoSection() {
        KVSection kVSection = new KVSection("custom_info");
        for (Map.Entry<String, String> entry : com.efs.sdk.base.a.d.a.a().a().entrySet()) {
            kVSection.put(entry.getKey(), entry.getValue());
        }
        this.sectionList.add(0, kVSection);
    }

    @Override // com.efs.sdk.base.protocol.ILogProtocol
    public byte[] generate() {
        String changeToStr = changeToStr();
        if (com.efs.sdk.base.a.d.a.a().g) {
            d.a("efs.base", changeToStr);
        }
        return changeToStr.getBytes();
    }

    @Override // com.efs.sdk.base.protocol.ILogProtocol
    public String generateString() {
        return changeToStr();
    }

    @Override // com.efs.sdk.base.protocol.ILogProtocol
    public String getLinkKey() {
        initLinkInfo();
        return this.mLinkKey;
    }

    @Override // com.efs.sdk.base.protocol.ILogProtocol
    public String getLinkId() {
        initLinkInfo();
        return this.mLinkID;
    }

    private void initLinkInfo() {
        if ((TextUtils.isEmpty(this.mLinkID) || TextUtils.isEmpty(this.mLinkKey)) && !this.mHasInitLinkInfo) {
            for (AbsSection absSection : this.sectionList) {
                if (absSection instanceof KVSection) {
                    Map<String, Object> dataMap = ((KVSection) absSection).getDataMap();
                    if (TextUtils.isEmpty(this.mLinkID) && dataMap.containsKey(com.efs.sdk.base.Constants.LOG_KEY_LINK_ID)) {
                        this.mLinkID = String.valueOf(dataMap.get(com.efs.sdk.base.Constants.LOG_KEY_LINK_ID));
                    }
                    if (TextUtils.isEmpty(this.mLinkKey) && dataMap.containsKey(com.efs.sdk.base.Constants.LOG_KEY_LINK_KEY)) {
                        this.mLinkKey = String.valueOf(dataMap.get(com.efs.sdk.base.Constants.LOG_KEY_LINK_KEY));
                    }
                }
            }
            this.mHasInitLinkInfo = true;
        }
    }
}
