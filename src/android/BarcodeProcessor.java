package com.cordovaplugincamerapreview;


import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;

class BarcodeProcessor implements Detector.Processor<Barcode> {

    public BarcodeProcessor() {
        Log.d("Detect", "created");
    }

    @Override
    public void release() {

    }

    @Override
    public void receiveDetections(Detector.Detections<Barcode> detections) {
        SparseArray<Barcode> codes = detections.getDetectedItems();
        int sizeCheck = detections.getDetectedItems().size();
        for (int i = 0; i < sizeCheck; i++) {
            Barcode barcode = codes.valueAt(i);
            Log.d("Detect", barcode.displayValue);
        }
    }
}
