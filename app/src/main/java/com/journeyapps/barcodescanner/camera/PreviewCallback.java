package com.journeyapps.barcodescanner.camera;

import com.journeyapps.barcodescanner.SourceData;

/* loaded from: classes2.dex */
public interface PreviewCallback {
    void onPreview(SourceData sourceData);

    void onPreviewError(Exception exc);
}
