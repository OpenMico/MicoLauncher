package com.xiaomi.micolauncher.module.applist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ThirdAppInfo;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.utils.ApkTool;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ThirdAppListActivity extends BaseActivity {
    private a a;
    private List<ThirdAppInfo> b;
    private ListView c;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_third_app_list);
        this.c = (ListView) findViewById(R.id.list);
        a();
        this.c.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.xiaomi.micolauncher.module.applist.-$$Lambda$ThirdAppListActivity$557GOtcB-XHiR9aWlP5NccuuNn0
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                ThirdAppListActivity.this.a(adapterView, view, i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AdapterView adapterView, View view, int i, long j) {
        ActivityLifeCycleManager.startActivityQuietly(getPackageManager().getLaunchIntentForPackage(this.b.get(i).getPackageName()));
    }

    private void a() {
        ThreadUtil.getWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.module.applist.-$$Lambda$ThirdAppListActivity$MRkYPdHS1A8Y56C-vMUHSuaH2FE
            @Override // java.lang.Runnable
            public final void run() {
                ThirdAppListActivity.this.b();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b() {
        this.b = ApkTool.scanLocalInstallAppList(getPackageManager());
        L.base.i("third app size  %d", Integer.valueOf(ContainerUtil.getSize(this.b)));
        runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.applist.-$$Lambda$ThirdAppListActivity$dDSFM3vtG_dWVrlQmlRQhPlWAow
            @Override // java.lang.Runnable
            public final void run() {
                ThirdAppListActivity.this.c();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c() {
        this.a = new a();
        this.c.setAdapter((ListAdapter) this.a);
        this.a.a(this.b);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class a extends BaseAdapter {
        List<ThirdAppInfo> a = new ArrayList();

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return 0L;
        }

        a() {
        }

        public void a(List<ThirdAppInfo> list) {
            this.a = list;
            notifyDataSetChanged();
        }

        @Override // android.widget.Adapter
        public int getCount() {
            L.base.i("getCount third app size  %d", Integer.valueOf(ContainerUtil.getSize(ThirdAppListActivity.this.b)));
            return ContainerUtil.getSize(ThirdAppListActivity.this.b);
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            if (ContainerUtil.isOutOfBound(i, ThirdAppListActivity.this.b)) {
                return null;
            }
            return this.a.get(i);
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2;
            C0166a aVar;
            ThirdAppInfo thirdAppInfo = this.a.get(i);
            if (view == null) {
                aVar = new C0166a();
                view2 = LayoutInflater.from(ThirdAppListActivity.this.getBaseContext()).inflate(R.layout.item_app_info, (ViewGroup) null);
                aVar.a = (ImageView) view2.findViewById(R.id.app_icon_iv);
                aVar.b = (TextView) view2.findViewById(R.id.app_name_tv);
                aVar.c = (TextView) view2.findViewById(R.id.package_name_tv);
                view2.setTag(aVar);
            } else {
                aVar = (C0166a) view.getTag();
                view2 = view;
            }
            aVar.a.setImageDrawable(thirdAppInfo.getImage());
            aVar.b.setText(thirdAppInfo.getAppName());
            aVar.c.setText(thirdAppInfo.getPackageName());
            return view2;
        }

        /* renamed from: com.xiaomi.micolauncher.module.applist.ThirdAppListActivity$a$a  reason: collision with other inner class name */
        /* loaded from: classes3.dex */
        class C0166a {
            ImageView a;
            TextView b;
            TextView c;

            C0166a() {
            }
        }
    }
}
