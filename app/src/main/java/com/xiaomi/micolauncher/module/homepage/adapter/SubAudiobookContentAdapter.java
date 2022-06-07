package com.xiaomi.micolauncher.module.homepage.adapter;

import android.view.ViewGroup;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.AudioBookContentViewHolder;

/* loaded from: classes3.dex */
public class SubAudiobookContentAdapter extends AudiobookContentAdapter {
    @Override // com.xiaomi.micolauncher.module.homepage.adapter.AudiobookContentAdapter
    protected AudioBookContentViewHolder getAudioBookContentViewHolder(ViewGroup viewGroup) {
        AudioBookContentViewHolder audioBookContentViewHolder = new AudioBookContentViewHolder(inflateViewByX2C(viewGroup, R.layout.audiobook_item));
        audioBookContentViewHolder.initInMain();
        return audioBookContentViewHolder;
    }
}
