package com.xiaomi.micolauncher.module.child.childstory.holder;

import android.content.Context;
import android.view.View;
import com.xiaomi.micolauncher.api.model.ChildStory;
import com.xiaomi.micolauncher.api.model.IBlockBean;
import com.xiaomi.micolauncher.module.child.BaseChildHolder;

/* loaded from: classes3.dex */
public class BaseChildStoryHolder extends BaseChildHolder {
    public ChildStory.BlocksBean storyBlock;

    public BaseChildStoryHolder(Context context, View view) {
        super(context, view);
    }

    @Override // com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void setBlocksBean(IBlockBean iBlockBean) {
        super.setBlocksBean(iBlockBean);
        this.storyBlock = (ChildStory.BlocksBean) iBlockBean;
    }
}
