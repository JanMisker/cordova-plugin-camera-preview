package com.cordovaplugincamerapreview;

import android.util.SparseArray;


/**
 * Created by janmisker on 14-07-2017.
 */

public abstract class Detector<T> {
    Detector.Processor<T> mProcessor;

    abstract SparseArray<T> detect(Frame frame);

    public void receiveFrame(Frame frame) {
        if (mProcessor != null) {
            Detector.Detections detections = new Detector.Detections(detect(frame));
            mProcessor.receiveDetections(detections);
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
