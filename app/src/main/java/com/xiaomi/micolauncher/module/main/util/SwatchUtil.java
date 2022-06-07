package com.xiaomi.micolauncher.module.main.util;

import android.graphics.Bitmap;
import com.android.internal.graphics.palette.Palette;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.util.List;

/* loaded from: classes3.dex */
public class SwatchUtil {
    public static Observable<Integer> getSwatchColorAsync(final Bitmap bitmap) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.main.util.-$$Lambda$SwatchUtil$VA0VHQd9nZJQBJ00v1ipsCxoNAw
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                SwatchUtil.a(bitmap, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Bitmap bitmap, final ObservableEmitter observableEmitter) throws Exception {
        try {
            Palette.Builder builder = new Palette.Builder(bitmap);
            builder.maximumColorCount(8);
            builder.generate(new Palette.PaletteAsyncListener() { // from class: com.xiaomi.micolauncher.module.main.util.-$$Lambda$SwatchUtil$_3kMfjslx-123_PSFpoBv5MgJu8
                public final void onGenerated(Palette palette) {
                    SwatchUtil.a(ObservableEmitter.this, palette);
                }
            });
        } catch (Exception e) {
            observableEmitter.onError(e);
            observableEmitter.onComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(ObservableEmitter observableEmitter, Palette palette) {
        List<Palette.Swatch> swatches = palette.getSwatches();
        int i = 0;
        if (swatches != null && swatches.size() > 0) {
            Palette.Swatch swatch = null;
            int i2 = 0;
            for (Palette.Swatch swatch2 : swatches) {
                if (swatch2 != null && swatch2.getPopulation() > i2) {
                    i2 = swatch2.getPopulation();
                    swatch = swatch2;
                }
            }
            if (swatch != null) {
                i = swatch.getRgb();
            }
        }
        observableEmitter.onNext(Integer.valueOf(i));
        observableEmitter.onComplete();
    }
}
