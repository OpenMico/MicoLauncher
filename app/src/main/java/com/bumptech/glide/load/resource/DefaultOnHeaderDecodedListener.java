package com.bumptech.glide.load.resource;

import android.graphics.ColorSpace;
import android.graphics.ImageDecoder;
import android.os.Build;
import android.util.Log;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.PreferredColorSpace;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.load.resource.bitmap.HardwareConfigState;

@RequiresApi(api = 28)
/* loaded from: classes.dex */
public final class DefaultOnHeaderDecodedListener implements ImageDecoder.OnHeaderDecodedListener {
    private final HardwareConfigState a = HardwareConfigState.getInstance();
    private final int b;
    private final int c;
    private final DecodeFormat d;
    private final DownsampleStrategy e;
    private final boolean f;
    private final PreferredColorSpace g;

    public DefaultOnHeaderDecodedListener(int i, int i2, @NonNull Options options) {
        this.b = i;
        this.c = i2;
        this.d = (DecodeFormat) options.get(Downsampler.DECODE_FORMAT);
        this.e = (DownsampleStrategy) options.get(DownsampleStrategy.OPTION);
        this.f = options.get(Downsampler.ALLOW_HARDWARE_CONFIG) != null && ((Boolean) options.get(Downsampler.ALLOW_HARDWARE_CONFIG)).booleanValue();
        this.g = (PreferredColorSpace) options.get(Downsampler.PREFERRED_COLOR_SPACE);
    }

    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
    public void onHeaderDecoded(@NonNull ImageDecoder imageDecoder, @NonNull ImageDecoder.ImageInfo imageInfo, @NonNull ImageDecoder.Source source) {
        boolean z = true;
        if (this.a.isHardwareConfigAllowed(this.b, this.c, this.f, false)) {
            imageDecoder.setAllocator(3);
        } else {
            imageDecoder.setAllocator(1);
        }
        if (this.d == DecodeFormat.PREFER_RGB_565) {
            imageDecoder.setMemorySizePolicy(0);
        }
        imageDecoder.setOnPartialImageListener(new ImageDecoder.OnPartialImageListener() { // from class: com.bumptech.glide.load.resource.DefaultOnHeaderDecodedListener.1
            @Override // android.graphics.ImageDecoder.OnPartialImageListener
            public boolean onPartialImage(@NonNull ImageDecoder.DecodeException decodeException) {
                return false;
            }
        });
        Size size = imageInfo.getSize();
        int i = this.b;
        if (i == Integer.MIN_VALUE) {
            i = size.getWidth();
        }
        int i2 = this.c;
        if (i2 == Integer.MIN_VALUE) {
            i2 = size.getHeight();
        }
        float scaleFactor = this.e.getScaleFactor(size.getWidth(), size.getHeight(), i, i2);
        int round = Math.round(size.getWidth() * scaleFactor);
        int round2 = Math.round(size.getHeight() * scaleFactor);
        if (Log.isLoggable("ImageDecoder", 2)) {
            Log.v("ImageDecoder", "Resizing from [" + size.getWidth() + "x" + size.getHeight() + "] to [" + round + "x" + round2 + "] scaleFactor: " + scaleFactor);
        }
        imageDecoder.setTargetSize(round, round2);
        if (this.g == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 28) {
            if (this.g != PreferredColorSpace.DISPLAY_P3 || imageInfo.getColorSpace() == null || !imageInfo.getColorSpace().isWideGamut()) {
                z = false;
            }
            imageDecoder.setTargetColorSpace(ColorSpace.get(z ? ColorSpace.Named.DISPLAY_P3 : ColorSpace.Named.SRGB));
        } else if (Build.VERSION.SDK_INT >= 26) {
            imageDecoder.setTargetColorSpace(ColorSpace.get(ColorSpace.Named.SRGB));
        }
    }
}
