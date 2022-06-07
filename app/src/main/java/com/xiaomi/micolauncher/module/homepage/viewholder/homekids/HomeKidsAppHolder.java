package com.xiaomi.micolauncher.module.homepage.viewholder.homekids;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.child.BaseChildHolder;
import com.xiaomi.micolauncher.module.homepage.SkillPath;

/* loaded from: classes3.dex */
public class HomeKidsAppHolder extends BaseChildHolder {
    public HomeKidsAppHolder(final Context context, View view) {
        super(context, view);
        view.findViewById(R.id.app1).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.homekids.-$$Lambda$HomeKidsAppHolder$Bo1eFyccseyqrCOVC9zIts488Ag
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SchemaManager.handleSchema(context, SkillPath.JIGSAWPUZZLE);
            }
        });
        view.findViewById(R.id.app2).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.homekids.-$$Lambda$HomeKidsAppHolder$mSMIOJLVHcvcXEIwGn_MaEqrCVc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SchemaManager.handleSchema(context, SkillPath.WUKONGSHIZI);
            }
        });
        view.findViewById(R.id.app3).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.homekids.-$$Lambda$HomeKidsAppHolder$UvI0mgFtN7PDvYbbDRpJiyp5DRU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SchemaManager.handleSchema(context, SkillPath.WUKONGSHUXUE);
            }
        });
        view.findViewById(R.id.app4).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.homekids.-$$Lambda$HomeKidsAppHolder$d3U6JkuDEqp5FQP2_2YYf9mZksg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SchemaManager.handleSchema(context, SkillPath.AIENGLISH);
            }
        });
        AnimLifecycleManager.repeatOnAttach((ImageView) view.findViewById(R.id.app1), MicoAnimConfigurator4EntertainmentAndApps.get());
        AnimLifecycleManager.repeatOnAttach((ImageView) view.findViewById(R.id.app2), MicoAnimConfigurator4EntertainmentAndApps.get());
        AnimLifecycleManager.repeatOnAttach((ImageView) view.findViewById(R.id.app3), MicoAnimConfigurator4EntertainmentAndApps.get());
        AnimLifecycleManager.repeatOnAttach((ImageView) view.findViewById(R.id.app4), MicoAnimConfigurator4EntertainmentAndApps.get());
    }
}
