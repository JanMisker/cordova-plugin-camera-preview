package com.cordovaplugincamerapreview;

import android.util.SparseArray;


/**
 * Created by janmisker on 14-07-2017.
 * Adapted from auth0/Guardian.Android 
 * https://github.com/auth0/Guardian.Android/blob/master/app/src/main/java/com/auth0/guardian/sample/scanner/utils/Frame.java
 */
/*
 * Copyright (c) 2016 Auth0 (http://auth0.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public abstract class Detector<T> {
    Detector.Processor<T> mProcessor;

    abstract SparseArray<T> detect(Frame frame);

    public void receiveFrame(Frame frame) {
        Object var2 = lock;
        synchronized(lock) {
            if(processor == null) {
                throw new IllegalStateException("Detector processor must first be set with setProcessor in order to receive detection results.");
            } else {
                SparseArray detectedItems = detect(frame);
                Detector.Detections detections = new Detector.Detections(detectedItems);
                processor.receiveDetections(detections);
            }
        }
    }

    public void setProcessor(Detector.Processor<T> processor) {
        mProcessor = processor;
    }

    public void release() {
        if (mProcessor != null) {
            mProcessor.release();
        }
    }

    public interface Processor<T> {

        void receiveDetections(Detector.Detections<T> detections);
        void release();
    }

    public static class Detections<T> {
        private SparseArray<T> mDetections;

        public Detections(SparseArray<T> detections) {
            mDetections = detections;
        }

        public SparseArray<T> getDetectedItems() {
            return mDetections;
        }
    }
}
