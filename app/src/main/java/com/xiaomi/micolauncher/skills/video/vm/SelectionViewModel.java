package com.xiaomi.micolauncher.skills.video.vm;

import android.app.Application;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.model.ChildVideoPlayList;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class SelectionViewModel extends AndroidViewModel {
    private final CompositeDisposable a = new CompositeDisposable();

    public SelectionViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadMore() {
        String serialId = VideoModel.getInstance().getSerialId();
        final int page = VideoModel.getInstance().getPage() + 1;
        final String[] strArr = new String[1];
        this.a.add(ChildVideoDataManager.getManager().getMiTvPlayListAsync(serialId, page, 100).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.video.vm.-$$Lambda$SelectionViewModel$FCnQrrpR824hbpK_8B4rTWTTFCM
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a;
                a = SelectionViewModel.a(strArr, (ChildVideoPlayList) obj);
                return a;
            }
        }).map($$Lambda$Buqtf5wefcWEH9A6pHl9cFgqxyo.INSTANCE).toList().subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.video.vm.-$$Lambda$SelectionViewModel$HwrHSlsU9APCJZXQmZXs0yv7_XM
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SelectionViewModel.a(page, strArr, (List) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ObservableSource a(String[] strArr, ChildVideoPlayList childVideoPlayList) throws Exception {
        VideoModel.getInstance().setMiTvHasNext(childVideoPlayList.getData().getPages().isHas_next());
        strArr[0] = childVideoPlayList.getData().getMedianame();
        return Observable.fromIterable(childVideoPlayList.getData().getMediaciinfo());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(int i, String[] strArr, List list) throws Exception {
        boolean z = false;
        L.video.d("SelectionViewModel#loadMore  page %d  items size  %d", Integer.valueOf(i), Integer.valueOf(list.size()));
        if (list.size() == 1) {
            ((VideoItem) list.get(0)).setTitle(strArr[0]);
        }
        if (ContainerUtil.hasData(VideoModel.getInstance().getPlayList())) {
            VideoItem videoItem = (VideoItem) list.get(0);
            Iterator<VideoItem> it = VideoModel.getInstance().getPlayList().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                VideoItem next = it.next();
                if (TextUtils.equals(next.getTitle(), videoItem.getTitle()) && next.getCi() == videoItem.getCi()) {
                    z = true;
                    break;
                }
            }
        }
        if (!z) {
            VideoModel.getInstance().addPlayList(list);
            VideoModel.getInstance().setPage(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        this.a.clear();
    }
}
