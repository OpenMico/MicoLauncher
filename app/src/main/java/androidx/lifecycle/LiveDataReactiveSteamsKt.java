package androidx.lifecycle;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;

/* compiled from: LiveDataReactiveSteams.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001f\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0086\b\u001a'\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0086\b¨\u0006\u0007"}, d2 = {"toLiveData", "Landroidx/lifecycle/LiveData;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/reactivestreams/Publisher;", "toPublisher", "lifecycle", "Landroidx/lifecycle/LifecycleOwner;", "lifecycle-reactivestreams-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class LiveDataReactiveSteamsKt {
    @NotNull
    public static final <T> Publisher<T> toPublisher(@NotNull LiveData<T> liveData, @NotNull LifecycleOwner lifecycle) {
        Intrinsics.checkNotNullParameter(liveData, "<this>");
        Intrinsics.checkNotNullParameter(lifecycle, "lifecycle");
        Publisher<T> publisher = LiveDataReactiveStreams.toPublisher(lifecycle, liveData);
        Intrinsics.checkNotNullExpressionValue(publisher, "toPublisher(lifecycle, this)");
        return publisher;
    }

    @NotNull
    public static final <T> LiveData<T> toLiveData(@NotNull Publisher<T> publisher) {
        Intrinsics.checkNotNullParameter(publisher, "<this>");
        LiveData<T> fromPublisher = LiveDataReactiveStreams.fromPublisher(publisher);
        Intrinsics.checkNotNullExpressionValue(fromPublisher, "fromPublisher(this)");
        return fromPublisher;
    }
}
