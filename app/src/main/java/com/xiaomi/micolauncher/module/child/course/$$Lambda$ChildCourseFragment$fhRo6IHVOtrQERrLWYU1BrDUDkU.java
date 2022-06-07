package com.xiaomi.micolauncher.module.child.course;

import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.child.course.-$$Lambda$ChildCourseFragment$fhRo6IHVOtrQERrLWYU1BrDUDkU  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$ChildCourseFragment$fhRo6IHVOtrQERrLWYU1BrDUDkU implements Function {
    public static final /* synthetic */ $$Lambda$ChildCourseFragment$fhRo6IHVOtrQERrLWYU1BrDUDkU INSTANCE = new $$Lambda$ChildCourseFragment$fhRo6IHVOtrQERrLWYU1BrDUDkU();

    private /* synthetic */ $$Lambda$ChildCourseFragment$fhRo6IHVOtrQERrLWYU1BrDUDkU() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ChildVideo.ItemsBean a;
        a = ChildCourseFragment.a((VideoItem) obj);
        return a;
    }
}
