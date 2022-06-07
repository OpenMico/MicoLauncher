package com.xiaomi.infra.galaxy.fds.result;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/* loaded from: classes3.dex */
public class UploadPartResultList {
    private List<UploadPartResult> a = new ArrayList();

    public List<UploadPartResult> getUploadPartResultList() {
        return this.a;
    }

    public void setUploadPartResultList(List<UploadPartResult> list) {
        this.a = list;
    }

    public void addUploadPartResult(UploadPartResult uploadPartResult) {
        this.a.add(uploadPartResult);
    }
}
