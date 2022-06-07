package com.xiaomi.micolauncher.module.homepage.event;

import com.xiaomi.micolauncher.module.homepage.bean.AudiobookContent;
import com.xiaomi.micolauncher.module.homepage.bean.OprationAudiobook;
import java.util.List;

/* loaded from: classes3.dex */
public class LoadAudiobookRecommendEvent {
    public OprationAudiobook firstRegionData;
    public List<AudiobookContent> recommendDatas;
    public OprationAudiobook secondRegionData;

    public LoadAudiobookRecommendEvent(OprationAudiobook oprationAudiobook, OprationAudiobook oprationAudiobook2, List<AudiobookContent> list) {
        this.firstRegionData = oprationAudiobook;
        this.secondRegionData = oprationAudiobook2;
        this.recommendDatas = list;
    }
}
