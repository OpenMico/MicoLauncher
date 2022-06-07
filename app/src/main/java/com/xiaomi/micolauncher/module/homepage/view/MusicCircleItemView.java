package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class MusicCircleItemView extends BaseMusicView {
    @Override // com.xiaomi.micolauncher.module.homepage.view.BaseMusicView
    protected int layoutId() {
        return R.layout.music_circle_item;
    }

    public MusicCircleItemView(Context context) {
        this(context, null);
    }

    public MusicCircleItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MusicCircleItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.view.BaseMusicView
    protected void setCoverImg() {
        Glide.with(getContext()).load(getItem().getItemImageUrl()).apply((BaseRequestOptions<?>) RequestOptions.bitmapTransform(new CircleCrop()).format(DecodeFormat.PREFER_ARGB_8888)).into(this.a);
    }
}
