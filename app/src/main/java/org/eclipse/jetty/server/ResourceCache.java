package org.eclipse.jetty.server;

import com.xiaomi.mipush.sdk.Constants;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpContent;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.io.View;
import org.eclipse.jetty.io.nio.DirectNIOBuffer;
import org.eclipse.jetty.io.nio.IndirectNIOBuffer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceFactory;

/* loaded from: classes5.dex */
public class ResourceCache {
    private static final Logger LOG = Log.getLogger(ResourceCache.class);
    private final ConcurrentMap<String, Content> _cache;
    private final AtomicInteger _cachedFiles;
    private final AtomicInteger _cachedSize;
    private final ResourceFactory _factory;
    private int _maxCacheSize;
    private int _maxCachedFileSize;
    private int _maxCachedFiles;
    private final MimeTypes _mimeTypes;
    private final ResourceCache _parent;
    private boolean _useFileMappedBuffer;

    public ResourceCache(ResourceCache resourceCache, ResourceFactory resourceFactory, MimeTypes mimeTypes, boolean z) {
        this(resourceCache, resourceFactory, mimeTypes);
        setUseFileMappedBuffer(z);
    }

    public ResourceCache(ResourceCache resourceCache, ResourceFactory resourceFactory, MimeTypes mimeTypes) {
        this._useFileMappedBuffer = true;
        this._maxCachedFileSize = 4194304;
        this._maxCachedFiles = 2048;
        this._maxCacheSize = 33554432;
        this._factory = resourceFactory;
        this._cache = new ConcurrentHashMap();
        this._cachedSize = new AtomicInteger();
        this._cachedFiles = new AtomicInteger();
        this._mimeTypes = mimeTypes;
        this._parent = resourceCache;
    }

    public int getCachedSize() {
        return this._cachedSize.get();
    }

    public int getCachedFiles() {
        return this._cachedFiles.get();
    }

    public int getMaxCachedFileSize() {
        return this._maxCachedFileSize;
    }

    public void setMaxCachedFileSize(int i) {
        this._maxCachedFileSize = i;
        shrinkCache();
    }

    public int getMaxCacheSize() {
        return this._maxCacheSize;
    }

    public void setMaxCacheSize(int i) {
        this._maxCacheSize = i;
        shrinkCache();
    }

    public int getMaxCachedFiles() {
        return this._maxCachedFiles;
    }

    public void setMaxCachedFiles(int i) {
        this._maxCachedFiles = i;
        shrinkCache();
    }

    public boolean isUseFileMappedBuffer() {
        return this._useFileMappedBuffer;
    }

    public void setUseFileMappedBuffer(boolean z) {
        this._useFileMappedBuffer = z;
    }

    public void flushCache() {
        if (this._cache == null) {
            return;
        }
        while (this._cache.size() > 0) {
            for (String str : this._cache.keySet()) {
                Content remove = this._cache.remove(str);
                if (remove != null) {
                    remove.invalidate();
                }
            }
        }
    }

    public HttpContent lookup(String str) throws IOException {
        HttpContent lookup;
        Content content = this._cache.get(str);
        if (content != null && content.isValid()) {
            return content;
        }
        HttpContent load = load(str, this._factory.getResource(str));
        if (load != null) {
            return load;
        }
        ResourceCache resourceCache = this._parent;
        if (resourceCache == null || (lookup = resourceCache.lookup(str)) == null) {
            return null;
        }
        return lookup;
    }

    protected boolean isCacheable(Resource resource) {
        long length = resource.length();
        return length > 0 && length < ((long) this._maxCachedFileSize) && length < ((long) this._maxCacheSize);
    }

    private HttpContent load(String str, Resource resource) throws IOException {
        if (resource == null || !resource.exists()) {
            return null;
        }
        if (resource.isDirectory() || !isCacheable(resource)) {
            return new HttpContent.ResourceAsHttpContent(resource, this._mimeTypes.getMimeByExtension(resource.toString()), getMaxCachedFileSize());
        }
        Content content = new Content(str, resource);
        shrinkCache();
        Content putIfAbsent = this._cache.putIfAbsent(str, content);
        if (putIfAbsent == null) {
            return content;
        }
        content.invalidate();
        return putIfAbsent;
    }

    private void shrinkCache() {
        while (this._cache.size() > 0) {
            if (this._cachedFiles.get() > this._maxCachedFiles || this._cachedSize.get() > this._maxCacheSize) {
                TreeSet<Content> treeSet = new TreeSet(new Comparator<Content>() { // from class: org.eclipse.jetty.server.ResourceCache.1
                    public int compare(Content content, Content content2) {
                        if (content._lastAccessed < content2._lastAccessed) {
                            return -1;
                        }
                        if (content._lastAccessed > content2._lastAccessed) {
                            return 1;
                        }
                        if (content._length < content2._length) {
                            return -1;
                        }
                        return content._key.compareTo(content2._key);
                    }
                });
                for (Content content : this._cache.values()) {
                    treeSet.add(content);
                }
                for (Content content2 : treeSet) {
                    if (this._cachedFiles.get() > this._maxCachedFiles || this._cachedSize.get() > this._maxCacheSize) {
                        if (content2 == this._cache.remove(content2.getKey())) {
                            content2.invalidate();
                        }
                    }
                }
            } else {
                return;
            }
        }
    }

    protected Buffer getIndirectBuffer(Resource resource) {
        try {
            int length = (int) resource.length();
            if (length < 0) {
                Logger logger = LOG;
                logger.warn("invalid resource: " + String.valueOf(resource) + StringUtils.SPACE + length, new Object[0]);
                return null;
            }
            IndirectNIOBuffer indirectNIOBuffer = new IndirectNIOBuffer(length);
            InputStream inputStream = resource.getInputStream();
            indirectNIOBuffer.readFrom(inputStream, length);
            inputStream.close();
            return indirectNIOBuffer;
        } catch (IOException e) {
            LOG.warn(e);
            return null;
        }
    }

    protected Buffer getDirectBuffer(Resource resource) {
        try {
            if (this._useFileMappedBuffer && resource.getFile() != null) {
                return new DirectNIOBuffer(resource.getFile());
            }
            int length = (int) resource.length();
            if (length < 0) {
                Logger logger = LOG;
                logger.warn("invalid resource: " + String.valueOf(resource) + StringUtils.SPACE + length, new Object[0]);
                return null;
            }
            DirectNIOBuffer directNIOBuffer = new DirectNIOBuffer(length);
            InputStream inputStream = resource.getInputStream();
            directNIOBuffer.readFrom(inputStream, length);
            inputStream.close();
            return directNIOBuffer;
        } catch (IOException e) {
            LOG.warn(e);
            return null;
        }
    }

    public String toString() {
        return "ResourceCache[" + this._parent + Constants.ACCEPT_TIME_SEPARATOR_SP + this._factory + "]@" + hashCode();
    }

    /* loaded from: classes5.dex */
    public class Content implements HttpContent {
        final Buffer _contentType;
        final String _key;
        volatile long _lastAccessed;
        final long _lastModified;
        final Buffer _lastModifiedBytes;
        final int _length;
        final Resource _resource;
        AtomicReference<Buffer> _indirectBuffer = new AtomicReference<>();
        AtomicReference<Buffer> _directBuffer = new AtomicReference<>();

        public boolean isMiss() {
            return false;
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public void release() {
        }

        Content(String str, Resource resource) {
            this._key = str;
            this._resource = resource;
            this._contentType = ResourceCache.this._mimeTypes.getMimeByExtension(this._resource.toString());
            boolean exists = resource.exists();
            this._lastModified = exists ? resource.lastModified() : -1L;
            long j = this._lastModified;
            this._lastModifiedBytes = j < 0 ? null : new ByteArrayBuffer(HttpFields.formatDate(j));
            this._length = exists ? (int) resource.length() : 0;
            ResourceCache.this._cachedSize.addAndGet(this._length);
            ResourceCache.this._cachedFiles.incrementAndGet();
            this._lastAccessed = System.currentTimeMillis();
        }

        public String getKey() {
            return this._key;
        }

        public boolean isCached() {
            return this._key != null;
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public Resource getResource() {
            return this._resource;
        }

        boolean isValid() {
            if (this._lastModified == this._resource.lastModified()) {
                this._lastAccessed = System.currentTimeMillis();
                return true;
            } else if (this != ResourceCache.this._cache.remove(this._key)) {
                return false;
            } else {
                invalidate();
                return false;
            }
        }

        protected void invalidate() {
            ResourceCache.this._cachedSize.addAndGet(-this._length);
            ResourceCache.this._cachedFiles.decrementAndGet();
            this._resource.release();
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public Buffer getLastModified() {
            return this._lastModifiedBytes;
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public Buffer getContentType() {
            return this._contentType;
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public Buffer getIndirectBuffer() {
            Buffer buffer = this._indirectBuffer.get();
            if (buffer == null) {
                Buffer indirectBuffer = ResourceCache.this.getIndirectBuffer(this._resource);
                if (indirectBuffer == null) {
                    Logger logger = ResourceCache.LOG;
                    logger.warn("Could not load " + this, new Object[0]);
                } else {
                    buffer = this._indirectBuffer.compareAndSet(null, indirectBuffer) ? indirectBuffer : this._indirectBuffer.get();
                }
            }
            if (buffer == null) {
                return null;
            }
            return new View(buffer);
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public Buffer getDirectBuffer() {
            Buffer buffer = this._directBuffer.get();
            if (buffer == null) {
                Buffer directBuffer = ResourceCache.this.getDirectBuffer(this._resource);
                if (directBuffer == null) {
                    Logger logger = ResourceCache.LOG;
                    logger.warn("Could not load " + this, new Object[0]);
                } else {
                    buffer = this._directBuffer.compareAndSet(null, directBuffer) ? directBuffer : this._directBuffer.get();
                }
            }
            if (buffer == null) {
                return null;
            }
            return new View(buffer);
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public long getContentLength() {
            return this._length;
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public InputStream getInputStream() throws IOException {
            Buffer indirectBuffer = getIndirectBuffer();
            if (indirectBuffer == null || indirectBuffer.array() == null) {
                return this._resource.getInputStream();
            }
            return new ByteArrayInputStream(indirectBuffer.array(), indirectBuffer.getIndex(), indirectBuffer.length());
        }

        public String toString() {
            Resource resource = this._resource;
            return String.format("%s %s %d %s %s", resource, Boolean.valueOf(resource.exists()), Long.valueOf(this._resource.lastModified()), this._contentType, this._lastModifiedBytes);
        }
    }
}
