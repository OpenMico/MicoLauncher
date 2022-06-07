package com.zhangyue.we.x2c;

import android.content.Context;
import android.view.View;
import com.zhangyue.we.x2c.layouts.X2C127_Audiobooks_Banner_Item;

/* loaded from: classes4.dex */
public class X2C127_audiobooks_banner_item implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        return new X2C127_Audiobooks_Banner_Item().createView(context);
    }
}
