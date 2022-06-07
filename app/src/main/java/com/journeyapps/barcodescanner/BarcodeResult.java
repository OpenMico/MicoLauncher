package com.journeyapps.barcodescanner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import java.util.Map;

/* loaded from: classes2.dex */
public class BarcodeResult {
    private final int a = 2;
    protected Result mResult;
    protected SourceData sourceData;

    public int getBitmapScaleFactor() {
        return 2;
    }

    public BarcodeResult(Result result, SourceData sourceData) {
        this.mResult = result;
        this.sourceData = sourceData;
    }

    private static void a(Canvas canvas, Paint paint, ResultPoint resultPoint, ResultPoint resultPoint2, int i) {
        if (resultPoint != null && resultPoint2 != null) {
            float f = i;
            canvas.drawLine(resultPoint.getX() / f, resultPoint.getY() / f, resultPoint2.getX() / f, resultPoint2.getY() / f, paint);
        }
    }

    public Result getResult() {
        return this.mResult;
    }

    public Bitmap getBitmap() {
        return this.sourceData.getBitmap(2);
    }

    public Bitmap getBitmapWithResultPoints(int i) {
        Bitmap bitmap = getBitmap();
        ResultPoint[] resultPoints = this.mResult.getResultPoints();
        if (resultPoints == null || resultPoints.length <= 0 || bitmap == null) {
            return bitmap;
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        Paint paint = new Paint();
        paint.setColor(i);
        if (resultPoints.length == 2) {
            paint.setStrokeWidth(4.0f);
            a(canvas, paint, resultPoints[0], resultPoints[1], 2);
        } else if (resultPoints.length == 4 && (this.mResult.getBarcodeFormat() == BarcodeFormat.UPC_A || this.mResult.getBarcodeFormat() == BarcodeFormat.EAN_13)) {
            a(canvas, paint, resultPoints[0], resultPoints[1], 2);
            a(canvas, paint, resultPoints[2], resultPoints[3], 2);
        } else {
            paint.setStrokeWidth(10.0f);
            for (ResultPoint resultPoint : resultPoints) {
                if (resultPoint != null) {
                    canvas.drawPoint(resultPoint.getX() / 2.0f, resultPoint.getY() / 2.0f, paint);
                }
            }
        }
        return createBitmap;
    }

    public String getText() {
        return this.mResult.getText();
    }

    public byte[] getRawBytes() {
        return this.mResult.getRawBytes();
    }

    public ResultPoint[] getResultPoints() {
        return this.mResult.getResultPoints();
    }

    public BarcodeFormat getBarcodeFormat() {
        return this.mResult.getBarcodeFormat();
    }

    public Map<ResultMetadataType, Object> getResultMetadata() {
        return this.mResult.getResultMetadata();
    }

    public long getTimestamp() {
        return this.mResult.getTimestamp();
    }

    public String toString() {
        return this.mResult.getText();
    }
}
