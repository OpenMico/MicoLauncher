package androidx.print;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public final class PrintHelper {
    @SuppressLint({"InlinedApi"})
    public static final int COLOR_MODE_COLOR = 2;
    @SuppressLint({"InlinedApi"})
    public static final int COLOR_MODE_MONOCHROME = 1;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    static final boolean a;
    static final boolean b;
    final Context c;
    BitmapFactory.Options d = null;
    final Object e = new Object();
    int f = 2;
    int g = 2;
    int h = 1;

    /* loaded from: classes.dex */
    public interface OnPrintFinishCallback {
        void onFinish();
    }

    static {
        boolean z = false;
        a = Build.VERSION.SDK_INT < 20 || Build.VERSION.SDK_INT > 23;
        if (Build.VERSION.SDK_INT != 23) {
            z = true;
        }
        b = z;
    }

    public static boolean systemSupportsPrint() {
        return Build.VERSION.SDK_INT >= 19;
    }

    public PrintHelper(@NonNull Context context) {
        this.c = context;
    }

    public void setScaleMode(int i) {
        this.f = i;
    }

    public int getScaleMode() {
        return this.f;
    }

    public void setColorMode(int i) {
        this.g = i;
    }

    public int getColorMode() {
        return this.g;
    }

    public void setOrientation(int i) {
        this.h = i;
    }

    public int getOrientation() {
        if (Build.VERSION.SDK_INT < 19 || this.h != 0) {
            return this.h;
        }
        return 1;
    }

    public void printBitmap(@NonNull String str, @NonNull Bitmap bitmap) {
        printBitmap(str, bitmap, (OnPrintFinishCallback) null);
    }

    public void printBitmap(@NonNull String str, @NonNull Bitmap bitmap, @Nullable OnPrintFinishCallback onPrintFinishCallback) {
        PrintAttributes.MediaSize mediaSize;
        if (Build.VERSION.SDK_INT >= 19 && bitmap != null) {
            PrintManager printManager = (PrintManager) this.c.getSystemService("print");
            if (a(bitmap)) {
                mediaSize = PrintAttributes.MediaSize.UNKNOWN_PORTRAIT;
            } else {
                mediaSize = PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE;
            }
            printManager.print(str, new a(str, this.f, bitmap, onPrintFinishCallback), new PrintAttributes.Builder().setMediaSize(mediaSize).setColorMode(this.g).build());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(19)
    /* loaded from: classes.dex */
    public class a extends PrintDocumentAdapter {
        private final String b;
        private final int c;
        private final Bitmap d;
        private final OnPrintFinishCallback e;
        private PrintAttributes f;

        a(String str, int i, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
            this.b = str;
            this.c = i;
            this.d = bitmap;
            this.e = onPrintFinishCallback;
        }

        @Override // android.print.PrintDocumentAdapter
        public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
            this.f = printAttributes2;
            layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.b).setContentType(1).setPageCount(1).build(), !printAttributes2.equals(printAttributes));
        }

        @Override // android.print.PrintDocumentAdapter
        public void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
            PrintHelper.this.a(this.f, this.c, this.d, parcelFileDescriptor, cancellationSignal, writeResultCallback);
        }

        @Override // android.print.PrintDocumentAdapter
        public void onFinish() {
            OnPrintFinishCallback onPrintFinishCallback = this.e;
            if (onPrintFinishCallback != null) {
                onPrintFinishCallback.onFinish();
            }
        }
    }

    public void printBitmap(@NonNull String str, @NonNull Uri uri) throws FileNotFoundException {
        printBitmap(str, uri, (OnPrintFinishCallback) null);
    }

    public void printBitmap(@NonNull String str, @NonNull Uri uri, @Nullable OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
        if (Build.VERSION.SDK_INT >= 19) {
            b bVar = new b(str, uri, onPrintFinishCallback, this.f);
            PrintManager printManager = (PrintManager) this.c.getSystemService("print");
            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            builder.setColorMode(this.g);
            int i = this.h;
            if (i == 1 || i == 0) {
                builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
            } else if (i == 2) {
                builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
            }
            printManager.print(str, bVar, builder.build());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(19)
    /* loaded from: classes.dex */
    public class b extends PrintDocumentAdapter {
        final String a;
        final Uri b;
        final OnPrintFinishCallback c;
        final int d;
        PrintAttributes e;
        AsyncTask<Uri, Boolean, Bitmap> f;
        Bitmap g = null;

        b(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback, int i) {
            this.a = str;
            this.b = uri;
            this.c = onPrintFinishCallback;
            this.d = i;
        }

        /* JADX WARN: Type inference failed for: r11v3, types: [androidx.print.PrintHelper$b$1] */
        @Override // android.print.PrintDocumentAdapter
        public void onLayout(final PrintAttributes printAttributes, final PrintAttributes printAttributes2, final CancellationSignal cancellationSignal, final PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
            synchronized (this) {
                this.e = printAttributes2;
            }
            if (cancellationSignal.isCanceled()) {
                layoutResultCallback.onLayoutCancelled();
            } else if (this.g != null) {
                layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.a).setContentType(1).setPageCount(1).build(), !printAttributes2.equals(printAttributes));
            } else {
                this.f = new AsyncTask<Uri, Boolean, Bitmap>() { // from class: androidx.print.PrintHelper.b.1
                    @Override // android.os.AsyncTask
                    protected void onPreExecute() {
                        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.print.PrintHelper.b.1.1
                            @Override // android.os.CancellationSignal.OnCancelListener
                            public void onCancel() {
                                b.this.a();
                                cancel(false);
                            }
                        });
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    /* renamed from: a */
                    public Bitmap doInBackground(Uri... uriArr) {
                        try {
                            return PrintHelper.this.a(b.this.b);
                        } catch (FileNotFoundException unused) {
                            return null;
                        }
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    /* renamed from: a */
                    public void onPostExecute(Bitmap bitmap) {
                        PrintAttributes.MediaSize mediaSize;
                        super.onPostExecute(bitmap);
                        if (bitmap != null && (!PrintHelper.a || PrintHelper.this.h == 0)) {
                            synchronized (this) {
                                mediaSize = b.this.e.getMediaSize();
                            }
                            if (!(mediaSize == null || mediaSize.isPortrait() == PrintHelper.a(bitmap))) {
                                Matrix matrix = new Matrix();
                                matrix.postRotate(90.0f);
                                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                            }
                        }
                        b bVar = b.this;
                        bVar.g = bitmap;
                        if (bitmap != null) {
                            layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(bVar.a).setContentType(1).setPageCount(1).build(), true ^ printAttributes2.equals(printAttributes));
                        } else {
                            layoutResultCallback.onLayoutFailed(null);
                        }
                        b.this.f = null;
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    /* renamed from: b */
                    public void onCancelled(Bitmap bitmap) {
                        layoutResultCallback.onLayoutCancelled();
                        b.this.f = null;
                    }
                }.execute(new Uri[0]);
            }
        }

        void a() {
            synchronized (PrintHelper.this.e) {
                if (PrintHelper.this.d != null) {
                    if (Build.VERSION.SDK_INT < 24) {
                        PrintHelper.this.d.requestCancelDecode();
                    }
                    PrintHelper.this.d = null;
                }
            }
        }

        @Override // android.print.PrintDocumentAdapter
        public void onFinish() {
            super.onFinish();
            a();
            AsyncTask<Uri, Boolean, Bitmap> asyncTask = this.f;
            if (asyncTask != null) {
                asyncTask.cancel(true);
            }
            OnPrintFinishCallback onPrintFinishCallback = this.c;
            if (onPrintFinishCallback != null) {
                onPrintFinishCallback.onFinish();
            }
            Bitmap bitmap = this.g;
            if (bitmap != null) {
                bitmap.recycle();
                this.g = null;
            }
        }

        @Override // android.print.PrintDocumentAdapter
        public void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
            PrintHelper.this.a(this.e, this.d, this.g, parcelFileDescriptor, cancellationSignal, writeResultCallback);
        }
    }

    static boolean a(Bitmap bitmap) {
        return bitmap.getWidth() <= bitmap.getHeight();
    }

    @RequiresApi(19)
    private static PrintAttributes.Builder a(PrintAttributes printAttributes) {
        PrintAttributes.Builder minMargins = new PrintAttributes.Builder().setMediaSize(printAttributes.getMediaSize()).setResolution(printAttributes.getResolution()).setMinMargins(printAttributes.getMinMargins());
        if (printAttributes.getColorMode() != 0) {
            minMargins.setColorMode(printAttributes.getColorMode());
        }
        if (Build.VERSION.SDK_INT >= 23 && printAttributes.getDuplexMode() != 0) {
            minMargins.setDuplexMode(printAttributes.getDuplexMode());
        }
        return minMargins;
    }

    static Matrix a(int i, int i2, RectF rectF, int i3) {
        float f;
        Matrix matrix = new Matrix();
        float f2 = i;
        float width = rectF.width() / f2;
        if (i3 == 2) {
            f = Math.max(width, rectF.height() / i2);
        } else {
            f = Math.min(width, rectF.height() / i2);
        }
        matrix.postScale(f, f);
        matrix.postTranslate((rectF.width() - (f2 * f)) / 2.0f, (rectF.height() - (i2 * f)) / 2.0f);
        return matrix;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.print.PrintHelper$1] */
    @RequiresApi(19)
    void a(final PrintAttributes printAttributes, final int i, final Bitmap bitmap, final ParcelFileDescriptor parcelFileDescriptor, final CancellationSignal cancellationSignal, final PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
        final PrintAttributes build = b ? printAttributes : a(printAttributes).setMinMargins(new PrintAttributes.Margins(0, 0, 0, 0)).build();
        new AsyncTask<Void, Void, Throwable>() { // from class: androidx.print.PrintHelper.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public Throwable doInBackground(Void... voidArr) {
                RectF rectF;
                try {
                    if (cancellationSignal.isCanceled()) {
                        return null;
                    }
                    PrintedPdfDocument printedPdfDocument = new PrintedPdfDocument(PrintHelper.this.c, build);
                    Bitmap a2 = PrintHelper.a(bitmap, build.getColorMode());
                    if (cancellationSignal.isCanceled()) {
                        return null;
                    }
                    PdfDocument.Page startPage = printedPdfDocument.startPage(1);
                    if (PrintHelper.b) {
                        rectF = new RectF(startPage.getInfo().getContentRect());
                    } else {
                        PrintedPdfDocument printedPdfDocument2 = new PrintedPdfDocument(PrintHelper.this.c, printAttributes);
                        PdfDocument.Page startPage2 = printedPdfDocument2.startPage(1);
                        RectF rectF2 = new RectF(startPage2.getInfo().getContentRect());
                        printedPdfDocument2.finishPage(startPage2);
                        printedPdfDocument2.close();
                        rectF = rectF2;
                    }
                    Matrix a3 = PrintHelper.a(a2.getWidth(), a2.getHeight(), rectF, i);
                    if (!PrintHelper.b) {
                        a3.postTranslate(rectF.left, rectF.top);
                        startPage.getCanvas().clipRect(rectF);
                    }
                    startPage.getCanvas().drawBitmap(a2, a3, null);
                    printedPdfDocument.finishPage(startPage);
                    if (cancellationSignal.isCanceled()) {
                        printedPdfDocument.close();
                        if (parcelFileDescriptor != null) {
                            try {
                                parcelFileDescriptor.close();
                            } catch (IOException unused) {
                            }
                        }
                        if (a2 != bitmap) {
                            a2.recycle();
                        }
                        return null;
                    }
                    printedPdfDocument.writeTo(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
                    printedPdfDocument.close();
                    if (parcelFileDescriptor != null) {
                        try {
                            parcelFileDescriptor.close();
                        } catch (IOException unused2) {
                        }
                    }
                    if (a2 != bitmap) {
                        a2.recycle();
                    }
                    return null;
                } catch (Throwable th) {
                    return th;
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public void onPostExecute(Throwable th) {
                if (cancellationSignal.isCanceled()) {
                    writeResultCallback.onWriteCancelled();
                } else if (th == null) {
                    writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                } else {
                    Log.e("PrintHelper", "Error writing printed content", th);
                    writeResultCallback.onWriteFailed(null);
                }
            }
        }.execute(new Void[0]);
    }

    Bitmap a(Uri uri) throws FileNotFoundException {
        BitmapFactory.Options options;
        if (uri == null || this.c == null) {
            throw new IllegalArgumentException("bad argument to getScaledBitmap");
        }
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inJustDecodeBounds = true;
        a(uri, options2);
        int i = options2.outWidth;
        int i2 = options2.outHeight;
        if (i <= 0 || i2 <= 0) {
            return null;
        }
        int max = Math.max(i, i2);
        int i3 = 1;
        while (max > 3500) {
            max >>>= 1;
            i3 <<= 1;
        }
        if (i3 <= 0 || Math.min(i, i2) / i3 <= 0) {
            return null;
        }
        synchronized (this.e) {
            try {
                this.d = new BitmapFactory.Options();
                this.d.inMutable = true;
                this.d.inSampleSize = i3;
                options = this.d;
            } catch (Throwable th) {
                throw th;
            }
        }
        try {
            Bitmap a2 = a(uri, options);
            synchronized (this.e) {
                try {
                    this.d = null;
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            return a2;
        } catch (Throwable th3) {
            synchronized (this.e) {
                try {
                    this.d = null;
                    throw th3;
                } catch (Throwable th4) {
                    throw th4;
                }
            }
        }
    }

    private Bitmap a(Uri uri, BitmapFactory.Options options) throws FileNotFoundException {
        Context context;
        Throwable th;
        if (uri == null || (context = this.c) == null) {
            throw new IllegalArgumentException("bad argument to loadBitmap");
        }
        InputStream inputStream = null;
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            try {
                Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream, null, options);
                if (openInputStream != null) {
                    try {
                        openInputStream.close();
                    } catch (IOException e) {
                        Log.w("PrintHelper", "close fail ", e);
                    }
                }
                return decodeStream;
            } catch (Throwable th2) {
                th = th2;
                inputStream = openInputStream;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e2) {
                        Log.w("PrintHelper", "close fail ", e2);
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    static Bitmap a(Bitmap bitmap, int i) {
        if (i != 1) {
            return bitmap;
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        canvas.setBitmap(null);
        return createBitmap;
    }
}
