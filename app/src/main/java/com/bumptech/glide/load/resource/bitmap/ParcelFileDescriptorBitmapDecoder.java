package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import java.io.IOException;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class ParcelFileDescriptorBitmapDecoder implements ResourceDecoder<ParcelFileDescriptor, Bitmap> {
    private final Downsampler a;

    public ParcelFileDescriptorBitmapDecoder(Downsampler downsampler) {
        this.a = downsampler;
    }

    public boolean handles(@NonNull ParcelFileDescriptor parcelFileDescriptor, @NonNull Options options) {
        return a(parcelFileDescriptor) && this.a.handles(parcelFileDescriptor);
    }

    private boolean a(@NonNull ParcelFileDescriptor parcelFileDescriptor) {
        return (!"HUAWEI".equalsIgnoreCase(Build.MANUFACTURER) && !"HONOR".equalsIgnoreCase(Build.MANUFACTURER)) || parcelFileDescriptor.getStatSize() <= 536870912;
    }

    @Nullable
    public Resource<Bitmap> decode(@NonNull ParcelFileDescriptor parcelFileDescriptor, int i, int i2, @NonNull Options options) throws IOException {
        return this.a.decode(parcelFileDescriptor, i, i2, options);
    }
}
