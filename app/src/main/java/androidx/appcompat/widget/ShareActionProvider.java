package androidx.appcompat.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.ActivityChooserModel;
import androidx.core.view.ActionProvider;

/* loaded from: classes.dex */
public class ShareActionProvider extends ActionProvider {
    public static final String DEFAULT_SHARE_HISTORY_FILE_NAME = "share_history.xml";
    final Context a;
    OnShareTargetSelectedListener c;
    private ActivityChooserModel.OnChooseActivityListener f;
    private int d = 4;
    private final b e = new b();
    String b = DEFAULT_SHARE_HISTORY_FILE_NAME;

    /* loaded from: classes.dex */
    public interface OnShareTargetSelectedListener {
        boolean onShareTargetSelected(ShareActionProvider shareActionProvider, Intent intent);
    }

    @Override // androidx.core.view.ActionProvider
    public boolean hasSubMenu() {
        return true;
    }

    public ShareActionProvider(Context context) {
        super(context);
        this.a = context;
    }

    public void setOnShareTargetSelectedListener(OnShareTargetSelectedListener onShareTargetSelectedListener) {
        this.c = onShareTargetSelectedListener;
        a();
    }

    @Override // androidx.core.view.ActionProvider
    public View onCreateActionView() {
        ActivityChooserView activityChooserView = new ActivityChooserView(this.a);
        if (!activityChooserView.isInEditMode()) {
            activityChooserView.setActivityChooserModel(ActivityChooserModel.a(this.a, this.b));
        }
        TypedValue typedValue = new TypedValue();
        this.a.getTheme().resolveAttribute(R.attr.actionModeShareDrawable, typedValue, true);
        activityChooserView.setExpandActivityOverflowButtonDrawable(AppCompatResources.getDrawable(this.a, typedValue.resourceId));
        activityChooserView.setProvider(this);
        activityChooserView.setDefaultActionButtonContentDescription(R.string.abc_shareactionprovider_share_with_application);
        activityChooserView.setExpandActivityOverflowButtonContentDescription(R.string.abc_shareactionprovider_share_with);
        return activityChooserView;
    }

    @Override // androidx.core.view.ActionProvider
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        ActivityChooserModel a2 = ActivityChooserModel.a(this.a, this.b);
        PackageManager packageManager = this.a.getPackageManager();
        int a3 = a2.a();
        int min = Math.min(a3, this.d);
        for (int i = 0; i < min; i++) {
            ResolveInfo a4 = a2.a(i);
            subMenu.add(0, i, i, a4.loadLabel(packageManager)).setIcon(a4.loadIcon(packageManager)).setOnMenuItemClickListener(this.e);
        }
        if (min < a3) {
            SubMenu addSubMenu = subMenu.addSubMenu(0, min, min, this.a.getString(R.string.abc_activity_chooser_view_see_all));
            for (int i2 = 0; i2 < a3; i2++) {
                ResolveInfo a5 = a2.a(i2);
                addSubMenu.add(0, i2, i2, a5.loadLabel(packageManager)).setIcon(a5.loadIcon(packageManager)).setOnMenuItemClickListener(this.e);
            }
        }
    }

    public void setShareHistoryFileName(String str) {
        this.b = str;
        a();
    }

    public void setShareIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if ("android.intent.action.SEND".equals(action) || "android.intent.action.SEND_MULTIPLE".equals(action)) {
                a(intent);
            }
        }
        ActivityChooserModel.a(this.a, this.b).a(intent);
    }

    /* loaded from: classes.dex */
    private class b implements MenuItem.OnMenuItemClickListener {
        b() {
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
            Intent b = ActivityChooserModel.a(ShareActionProvider.this.a, ShareActionProvider.this.b).b(menuItem.getItemId());
            if (b == null) {
                return true;
            }
            String action = b.getAction();
            if ("android.intent.action.SEND".equals(action) || "android.intent.action.SEND_MULTIPLE".equals(action)) {
                ShareActionProvider.this.a(b);
            }
            ShareActionProvider.this.a.startActivity(b);
            return true;
        }
    }

    private void a() {
        if (this.c != null) {
            if (this.f == null) {
                this.f = new a();
            }
            ActivityChooserModel.a(this.a, this.b).a(this.f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class a implements ActivityChooserModel.OnChooseActivityListener {
        a() {
        }

        @Override // androidx.appcompat.widget.ActivityChooserModel.OnChooseActivityListener
        public boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent) {
            if (ShareActionProvider.this.c == null) {
                return false;
            }
            ShareActionProvider.this.c.onShareTargetSelected(ShareActionProvider.this, intent);
            return false;
        }
    }

    void a(Intent intent) {
        if (Build.VERSION.SDK_INT >= 21) {
            intent.addFlags(134742016);
        } else {
            intent.addFlags(524288);
        }
    }
}
