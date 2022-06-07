package org.seamless.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.fourthline.cling.support.model.dlna.DLNAProfiles;

/* loaded from: classes4.dex */
public class Gfx {
    public static byte[] resizeProportionally(ImageIcon imageIcon, String str, int i, int i2) throws IOException {
        double d = 1.0d;
        double iconWidth = i != imageIcon.getIconWidth() ? i / imageIcon.getIconWidth() : 1.0d;
        if (i2 != imageIcon.getIconHeight()) {
            d = i2 / imageIcon.getIconHeight();
        }
        if (iconWidth < d) {
            i2 = (int) (imageIcon.getIconHeight() * iconWidth);
        } else {
            i = (int) (imageIcon.getIconWidth() * d);
        }
        BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), DLNAProfiles.DLNAMimeTypes.MIME_IMAGE_PNG.equals(str) ? 2 : 1);
        Graphics2D createGraphics = bufferedImage.createGraphics();
        createGraphics.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight(), (ImageObserver) null);
        createGraphics.dispose();
        BufferedImage scaledInstance = getScaledInstance(bufferedImage, i, i2, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR, false);
        String str2 = "";
        if (DLNAProfiles.DLNAMimeTypes.MIME_IMAGE_PNG.equals(str)) {
            str2 = "png";
        } else if ("image/jpeg".equals(str) || "image/jpg".equals(str)) {
            str2 = "jpeg";
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        ImageIO.write(scaledInstance, str2, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static BufferedImage getScaledInstance(BufferedImage bufferedImage, int i, int i2, Object obj, boolean z) {
        BufferedImage bufferedImage2;
        int i3;
        int i4;
        int i5 = 1;
        if (bufferedImage.getTransparency() != 1) {
            i5 = 2;
        }
        if (z) {
            i4 = bufferedImage.getWidth();
            i3 = bufferedImage.getHeight();
            bufferedImage2 = bufferedImage;
        } else {
            bufferedImage2 = bufferedImage;
            i4 = i;
            i3 = i2;
        }
        while (true) {
            if (z && i4 > i) {
                i4 /= 2;
                if (i4 < i) {
                    i4 = i;
                }
            }
            if (z && i3 > i2 && (i3 = i3 / 2) < i2) {
                i3 = i2;
            }
            BufferedImage bufferedImage3 = new BufferedImage(i4, i3, i5);
            Graphics2D createGraphics = bufferedImage3.createGraphics();
            createGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, obj);
            createGraphics.drawImage(bufferedImage2, 0, 0, i4, i3, (ImageObserver) null);
            createGraphics.dispose();
            if (i4 == i && i3 == i2) {
                return bufferedImage3;
            }
            bufferedImage2 = bufferedImage3;
        }
    }
}
