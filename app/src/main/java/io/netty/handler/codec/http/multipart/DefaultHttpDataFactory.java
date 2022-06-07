package io.netty.handler.codec.http.multipart;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class DefaultHttpDataFactory implements HttpDataFactory {
    public static final long MAXSIZE = -1;
    public static final long MINSIZE = 16384;
    private final boolean a;
    private final boolean b;
    private long c;
    private long d;
    private Charset e;
    private final Map<HttpRequest, List<HttpData>> f;

    public DefaultHttpDataFactory() {
        this.d = -1L;
        this.e = HttpConstants.DEFAULT_CHARSET;
        this.f = PlatformDependent.newConcurrentHashMap();
        this.a = false;
        this.b = true;
        this.c = 16384L;
    }

    public DefaultHttpDataFactory(Charset charset) {
        this();
        this.e = charset;
    }

    public DefaultHttpDataFactory(boolean z) {
        this.d = -1L;
        this.e = HttpConstants.DEFAULT_CHARSET;
        this.f = PlatformDependent.newConcurrentHashMap();
        this.a = z;
        this.b = false;
    }

    public DefaultHttpDataFactory(boolean z, Charset charset) {
        this(z);
        this.e = charset;
    }

    public DefaultHttpDataFactory(long j) {
        this.d = -1L;
        this.e = HttpConstants.DEFAULT_CHARSET;
        this.f = PlatformDependent.newConcurrentHashMap();
        this.a = false;
        this.b = true;
        this.c = j;
    }

    public DefaultHttpDataFactory(long j, Charset charset) {
        this(j);
        this.e = charset;
    }

    @Override // io.netty.handler.codec.http.multipart.HttpDataFactory
    public void setMaxLimit(long j) {
        this.d = j;
    }

    private List<HttpData> a(HttpRequest httpRequest) {
        List<HttpData> list = this.f.get(httpRequest);
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        this.f.put(httpRequest, arrayList);
        return arrayList;
    }

    @Override // io.netty.handler.codec.http.multipart.HttpDataFactory
    public Attribute createAttribute(HttpRequest httpRequest, String str) {
        if (this.a) {
            DiskAttribute diskAttribute = new DiskAttribute(str, this.e);
            diskAttribute.setMaxSize(this.d);
            a(httpRequest).add(diskAttribute);
            return diskAttribute;
        } else if (this.b) {
            MixedAttribute mixedAttribute = new MixedAttribute(str, this.c, this.e);
            mixedAttribute.setMaxSize(this.d);
            a(httpRequest).add(mixedAttribute);
            return mixedAttribute;
        } else {
            MemoryAttribute memoryAttribute = new MemoryAttribute(str);
            memoryAttribute.setMaxSize(this.d);
            return memoryAttribute;
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpDataFactory
    public Attribute createAttribute(HttpRequest httpRequest, String str, long j) {
        if (this.a) {
            DiskAttribute diskAttribute = new DiskAttribute(str, j, this.e);
            diskAttribute.setMaxSize(this.d);
            a(httpRequest).add(diskAttribute);
            return diskAttribute;
        } else if (this.b) {
            MixedAttribute mixedAttribute = new MixedAttribute(str, j, this.c, this.e);
            mixedAttribute.setMaxSize(this.d);
            a(httpRequest).add(mixedAttribute);
            return mixedAttribute;
        } else {
            MemoryAttribute memoryAttribute = new MemoryAttribute(str, j);
            memoryAttribute.setMaxSize(this.d);
            return memoryAttribute;
        }
    }

    private static void a(HttpData httpData) {
        try {
            httpData.checkSize(httpData.length());
        } catch (IOException unused) {
            throw new IllegalArgumentException("Attribute bigger than maxSize allowed");
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpDataFactory
    public Attribute createAttribute(HttpRequest httpRequest, String str, String str2) {
        Attribute attribute;
        if (this.a) {
            try {
                attribute = new DiskAttribute(str, str2, this.e);
                attribute.setMaxSize(this.d);
            } catch (IOException unused) {
                attribute = new MixedAttribute(str, str2, this.c, this.e);
                attribute.setMaxSize(this.d);
            }
            a(attribute);
            a(httpRequest).add(attribute);
            return attribute;
        } else if (this.b) {
            MixedAttribute mixedAttribute = new MixedAttribute(str, str2, this.c, this.e);
            mixedAttribute.setMaxSize(this.d);
            a(mixedAttribute);
            a(httpRequest).add(mixedAttribute);
            return mixedAttribute;
        } else {
            try {
                MemoryAttribute memoryAttribute = new MemoryAttribute(str, str2, this.e);
                memoryAttribute.setMaxSize(this.d);
                a(memoryAttribute);
                return memoryAttribute;
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpDataFactory
    public FileUpload createFileUpload(HttpRequest httpRequest, String str, String str2, String str3, String str4, Charset charset, long j) {
        if (this.a) {
            DiskFileUpload diskFileUpload = new DiskFileUpload(str, str2, str3, str4, charset, j);
            diskFileUpload.setMaxSize(this.d);
            a(diskFileUpload);
            a(httpRequest).add(diskFileUpload);
            return diskFileUpload;
        } else if (this.b) {
            MixedFileUpload mixedFileUpload = new MixedFileUpload(str, str2, str3, str4, charset, j, this.c);
            mixedFileUpload.setMaxSize(this.d);
            a(mixedFileUpload);
            a(httpRequest).add(mixedFileUpload);
            return mixedFileUpload;
        } else {
            MemoryFileUpload memoryFileUpload = new MemoryFileUpload(str, str2, str3, str4, charset, j);
            memoryFileUpload.setMaxSize(this.d);
            a(memoryFileUpload);
            return memoryFileUpload;
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpDataFactory
    public void removeHttpDataFromClean(HttpRequest httpRequest, InterfaceHttpData interfaceHttpData) {
        if (interfaceHttpData instanceof HttpData) {
            a(httpRequest).remove(interfaceHttpData);
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpDataFactory
    public void cleanRequestHttpData(HttpRequest httpRequest) {
        List<HttpData> remove = this.f.remove(httpRequest);
        if (remove != null) {
            for (HttpData httpData : remove) {
                httpData.delete();
            }
            remove.clear();
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpDataFactory
    public void cleanAllHttpData() {
        Iterator<Map.Entry<HttpRequest, List<HttpData>>> it = this.f.entrySet().iterator();
        while (it.hasNext()) {
            it.remove();
            List<HttpData> value = it.next().getValue();
            if (value != null) {
                for (HttpData httpData : value) {
                    httpData.delete();
                }
                value.clear();
            }
        }
    }

    @Override // io.netty.handler.codec.http.multipart.HttpDataFactory
    public void cleanRequestHttpDatas(HttpRequest httpRequest) {
        cleanRequestHttpData(httpRequest);
    }

    @Override // io.netty.handler.codec.http.multipart.HttpDataFactory
    public void cleanAllHttpDatas() {
        cleanAllHttpData();
    }
}
