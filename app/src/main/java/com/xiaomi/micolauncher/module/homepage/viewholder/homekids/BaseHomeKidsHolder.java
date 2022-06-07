package com.xiaomi.micolauncher.module.homepage.viewholder.homekids;

import android.content.Context;
import android.view.View;
import com.xiaomi.micolauncher.api.model.IBlockBean;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;

/* loaded from: classes3.dex */
public class BaseHomeKidsHolder extends BaseViewHolder {
    public IBlockBean blocksBean;
    protected Context context;

    public void removeAllMessages() {
    }

    public void startAllMessages() {
    }

    public BaseHomeKidsHolder(View view) {
        super(view);
        this.context = view.getContext();
    }

    public void setBlocksBean(IBlockBean iBlockBean) {
        this.blocksBean = iBlockBean;
    }
}
