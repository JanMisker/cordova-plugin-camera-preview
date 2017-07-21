package com.cordovaplugincamerapreview;


import android.util.Log;
import android.util.SparseArray;

import com.google.zxing.Result;

class BarcodeProcessor implements Detector.Processor<Barcode> {
    Listener listener;

    public BarcodeProcessor() {
        Log.d("Detect", "created");
    }

    @Override
    public void release() {
      listener = null;
    }

  public void setListener(Listener listener) {
    this.listener = listener;
  }

    @Override
    public void receiveDetections(Detector.Detections<Barcode> detections) {
        SparseArray<Barcode> codes = detections.getDetectedItems();
        int sizeCheck = detections.getDetectedItems().size();
        for (int i = 0; i < sizeCheck; i++) {
            Barcode barcode = codes.valueAt(i);
            if (this.listener != null) {
              this.listener.onBarcodeDetected(barcode);
            }
        }
    }

    public interface Listener {
      void onBarcodeDetected(Barcode barcode);
    }
}
