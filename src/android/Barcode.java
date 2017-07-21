package com.cordovaplugincamerapreview;

/**
 * https://github.com/auth0/Guardian.Android/blob/master/app/src/main/java/com/auth0/guardian/sample/scanner/utils/Barcode.java
 */


import android.graphics.RectF;

import com.google.zxing.Result;
import com.google.zxing.ResultPoint;

public class Barcode {

    private final Result result;
    private final Frame.Metadata frameMetadata;
    private final int offsetX;
    private final int offsetY;

    public Barcode(Result result, Frame.Metadata frameMetadata, int offsetX, int offsetY) {
        this.result = result;
        this.frameMetadata = frameMetadata;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public String getText() {
        return result.getText();
    }

    public byte[] getRawBytes() {
        return result.getRawBytes();
    }

    public RectF getBoundingBox() {
        float minX, minY, maxX, maxY;
        ResultPoint[] resultPoints = result.getResultPoints();
        ResultPoint point = resultPoints[0];
        minX = maxX = point.getX();
        minY = maxY = point.getY();
        for (int i=1; i<resultPoints.length; ++i) {
            point = resultPoints[i];
            if (point.getX() > maxX) {
                maxX = point.getX();
            } else if (point.getX() < minX) {
                minX = point.getX();
            }
            if (point.getY() > maxY) {
                maxY = point.getY();
            } else if (point.getY() < minY) {
                minY = point.getY();
            }
        }

        minX += offsetX;
        maxX += offsetX;
        minY += offsetY;
        maxY += offsetY;

        int width, height;
        switch (frameMetadata.getRotation()) {
            case Frame.ROTATION_90:
                // we need to change coordinates: (x,y) -> (y, h-x)
                height = frameMetadata.getHeight();
                //noinspection SuspiciousNameCombination
                return new RectF(height-maxY, minX       , height-minY, maxX);
            case Frame.ROTATION_180:
                width = frameMetadata.getWidth();
                height = frameMetadata.getHeight();
                return new RectF(width-maxX , height-maxY, width-minX , height-minY);
            case Frame.ROTATION_270:
                // we need to change coordinates: (x,y) -> (w-y, x)
                width = frameMetadata.getWidth();
                //noinspection SuspiciousNameCombination
                return new RectF(minY       , width-maxX , maxY       , width-minX);
            case Frame.ROTATION_0:
            default:
                return new RectF(minX       , minY       , maxX       , maxY);
        }
    }
}