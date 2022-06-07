package org.eclipse.jetty.http;

import java.io.IOException;
import java.io.InputStream;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

/* loaded from: classes5.dex */
public interface HttpContent {
    long getContentLength();

    Buffer getContentType();

    Buffer getDirectBuffer();

    Buffer getIndirectBuffer();

    InputStream getInputStream() throws IOException;

    Buffer getLastModified();

    Resource getResource();

    void release();

    /* loaded from: classes5.dex */
    public static class ResourceAsHttpContent implements HttpContent {
        private static final Logger LOG = Log.getLogger(ResourceAsHttpContent.class);
        final int _maxBuffer;
        final Buffer _mimeType;
        final Resource _resource;

        @Override // org.eclipse.jetty.http.HttpContent
        public Buffer getDirectBuffer() {
            return null;
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public Buffer getLastModified() {
            return null;
        }

        public ResourceAsHttpContent(Resource resource, Buffer buffer) {
            this._resource = resource;
            this._mimeType = buffer;
            this._maxBuffer = -1;
        }

        public ResourceAsHttpContent(Resource resource, Buffer buffer, int i) {
            this._resource = resource;
            this._mimeType = buffer;
            this._maxBuffer = i;
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public Buffer getContentType() {
            return this._mimeType;
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public Buffer getIndirectBuffer() {
            InputStream inputStream = null;
            try {
                try {
                    if (this._resource.length() > 0 && this._maxBuffer >= this._resource.length()) {
                        ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer((int) this._resource.length());
                        inputStream = this._resource.getInputStream();
                        byteArrayBuffer.readFrom(inputStream, (int) this._resource.length());
                        return byteArrayBuffer;
                    }
                    return null;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e2) {
                        LOG.warn("Couldn't close inputStream. Possible file handle leak", e2);
                    }
                }
            }
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public long getContentLength() {
            return this._resource.length();
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public InputStream getInputStream() throws IOException {
            return this._resource.getInputStream();
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public Resource getResource() {
            return this._resource;
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public void release() {
            this._resource.release();
        }
    }
}
