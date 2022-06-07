package com.xiaomi.micolauncher.module.child.childvideo.holder;

import android.content.Context;
import android.view.View;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.IBlockBean;
import com.xiaomi.micolauncher.module.child.BaseChildHolder;

/* loaded from: classes3.dex */
public class BaseChildVideoHolder extends BaseChildHolder {
    public static final int CATEGORY_ID_VIDEO_CARTOON = 1777;
    public ChildVideo.BlocksBean videoBlock;

    public BaseChildVideoHolder(Context context, View view) {
        super(context, view);
    }

    @Override // com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void setBlocksBean(IBlockBean iBlockBean) {
        super.setBlocksBean(iBlockBean);
        this.videoBlock = (ChildVideo.BlocksBean) iBlockBean;
    }
}
