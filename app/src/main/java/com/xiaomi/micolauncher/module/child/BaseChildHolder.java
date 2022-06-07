package com.xiaomi.micolauncher.module.child;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.api.model.IBlockBean;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;

/* loaded from: classes3.dex */
public class BaseChildHolder extends BaseViewHolder {
    protected static final String PAGE_VIP = "vip";
    public IBlockBean blocksBean;
    protected Context context;

    public void removeAllMessages() {
    }

    public void startAllMessages() {
    }

    public BaseChildHolder(Context context, @NonNull View view) {
        super(view);
        this.context = context;
    }

    public void setBlocksBean(IBlockBean iBlockBean) {
        this.blocksBean = iBlockBean;
    }
}
