package com.xiaomi.micolauncher.common.model;

import com.xiaomi.ai.api.Template;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class ImageSource implements Serializable {
    public boolean authentication;
    public String bgUrl;
    public int height;
    public int id;
    public String md5;
    public String size;
    public String token;
    public String url;
    public int width;

    public ImageSource(Template.ImageSource imageSource) {
        this.url = imageSource.getUrl();
        if (imageSource.isAuthentication().isPresent()) {
            this.authentication = imageSource.isAuthentication().get().booleanValue();
        }
        if (imageSource.getToken().isPresent()) {
            this.token = imageSource.getToken().get();
        }
        if (imageSource.getMd5().isPresent()) {
            this.url = imageSource.getMd5().get();
        }
        if (imageSource.getWidth().isPresent()) {
            this.width = imageSource.getWidth().get().intValue();
        }
        if (imageSource.getHeight().isPresent()) {
            this.height = imageSource.getHeight().get().intValue();
        }
        if (imageSource.getHeight().isPresent()) {
            this.size = imageSource.getSize().get();
        }
        if (imageSource.getBgUrl().isPresent()) {
            this.bgUrl = imageSource.getBgUrl().get();
        }
        if (imageSource.getId().isPresent()) {
            this.id = imageSource.getId().get().intValue();
        }
    }
}
