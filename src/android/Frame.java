package com.cordovaplugincamerapreview;

/**
 * Created by janmisker on 14-07-2017.
 * https://github.com/auth0/Guardian.Android/blob/master/app/src/main/java/com/auth0/guardian/sample/scanner/utils/Frame.java
 */

import android.graphics.ImageFormat;

import java.nio.ByteBuffer;

public class Frame {

    public static final int ROTATION_0 = 0;
    public static final int ROTATION_90 = 1;
    public static final int ROTATION_180 = 2;
    public static final int ROTATION_270 = 3;

    private Frame.Metadata metadata;
    private ByteBuffer data;

    public Frame.Metadata getMetadata() {
        return metadata;
    }

    public ByteBuffer getGrayscaleImageData() {
        return data;
    }

    private Frame() {
        metadata = new Frame.Metadata();
        data = null;
    }

    public static class Metadata {

        private int width;
        private int height;
        private int id;
        private long timestamp;
        private int rotation;

        public Metadata() {
        }

        public Metadata(Frame.Metadata metadata) {
            width = metadata.getWidth();
            height = metadata.getHeight();
            id = metadata.getId();
            timestamp = metadata.getTimestampMillis();
            rotation = metadata.getRotation();
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getId() {
            return id;
        }

        public long getTimestampMillis() {
            return timestamp;
        }

        public int getRotation() {
            return rotation;
        }

        public void autoRotate() {
            if (rotation % 2 != 0) {
                int aux = width;
                width = height;
                height = aux;
            }

            rotation = 0;
        }

        @Override
        public String toString() {
            return "Metadata{" +
                    "width=" + width +
                    ", height=" + height +
                    ", id=" + id +
                    ", timestamp=" + timestamp +
                    ", rotation=" + rotation +
                    '}';
        }
    }

    public static class Builder {
        private Frame frame = new Frame();

        public Builder() {
        }

        public Frame.Builder setImageData(ByteBuffer data, int width, int height, int format) {
            if (data == null) {
                throw new IllegalArgumentException("Null image data supplied.");
            } else if (data.capacity() < width * height) {
                throw new IllegalArgumentException("Invalid image data size.");
            } else {
                switch (format) {
                    case ImageFormat.NV16:
                    case ImageFormat.NV21:
                    case ImageFormat.YV12:
                        frame.data = data;
                        Frame.Metadata metadata = frame.getMetadata();
                        metadata.width = width;
                        metadata.height = height;
                        return this;
                    case ImageFormat.YUY2:
                    default:
                        throw new IllegalArgumentException("Unsupported image format: " + format);
                }
            }
        }

        public Frame.Builder setId(int id) {
            frame.getMetadata().id = id;
            return this;
        }

        public Frame.Builder setTimestampMillis(long timestampMillis) {
            frame.getMetadata().timestamp = timestampMillis;
            return this;
        }

        public Frame.Builder setRotation(int rotation) {
            frame.getMetadata().rotation = rotation;
            return this;
        }

        public Frame build() {
            if (frame.data == null) {
                throw new IllegalStateException("Missing image data.  Call setImageData to specify the image");
            } else {
                return frame;
            }
        }
    }
}