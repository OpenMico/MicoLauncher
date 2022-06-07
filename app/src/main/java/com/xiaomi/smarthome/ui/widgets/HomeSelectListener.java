package com.xiaomi.smarthome.ui.widgets;

import com.xiaomi.miot.support.core.MiotHome;
import java.util.List;

/* loaded from: classes4.dex */
public interface HomeSelectListener {
    void onHomeChanged(MiotHome miotHome, List<MiotHome> list);
}
