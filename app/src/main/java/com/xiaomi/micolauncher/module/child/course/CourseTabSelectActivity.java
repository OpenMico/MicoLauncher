package com.xiaomi.micolauncher.module.child.course;

import android.os.Bundle;
import android.view.MotionEvent;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.CourseTab;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.child.childvideo.SpaceItemDecoration;
import com.xiaomi.micolauncher.module.child.course.CourseTabAdapter;
import com.xiaomi.micolauncher.module.child.enent.CourseTabChangeEvent;
import com.xiaomi.micolauncher.module.homepage.event.MainPageMaskEvent;
import java.util.List;

/* loaded from: classes3.dex */
public class CourseTabSelectActivity extends BaseActivity {
    private RecyclerView a;
    private CourseTabAdapter b;
    private List<CourseTab> c;
    private int d;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_course_tab_select);
        this.a = (RecyclerView) findViewById(R.id.course_list_rv);
        this.d = SystemSetting.getKeySelectCourseTabIndex();
        this.b = new CourseTabAdapter(this, this.d);
        this.a.setAdapter(this.b);
        this.c = KidCourseDataManager.getManager().getCourseTabs();
        this.b.a(this.c);
        this.b.a(new CourseTabAdapter.OnCourseItemClickListener() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$CourseTabSelectActivity$wyimU-btiYpK5KcOvJM8UusocX0
            @Override // com.xiaomi.micolauncher.module.child.course.CourseTabAdapter.OnCourseItemClickListener
            public final void onCourseClick(int i) {
                CourseTabSelectActivity.this.a(i);
            }
        });
        this.a.setLayoutManager(new GridLayoutManager(this, 3));
        this.a.addItemDecoration(new SpaceItemDecoration(3, getResources().getDimensionPixelOffset(R.dimen.course_tab_item_space)));
        if (this.a.getItemAnimator() != null) {
            ((SimpleItemAnimator) this.a.getItemAnimator()).setSupportsChangeAnimations(false);
        }
        setDefaultScheduleDuration();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i) {
        SystemSetting.setKeySelectCourseTabIndex(i);
        if (i != this.d) {
            EventBusRegistry.getEventBus().post(new CourseTabChangeEvent());
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        EventBusRegistry.getEventBus().post(new MainPageMaskEvent(true));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        EventBusRegistry.getEventBus().post(new MainPageMaskEvent(false));
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getX() >= 604.0f) {
            return super.dispatchTouchEvent(motionEvent);
        }
        finish();
        return true;
    }
}
