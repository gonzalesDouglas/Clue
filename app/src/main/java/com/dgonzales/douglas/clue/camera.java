package com.dgonzales.douglas.clue;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class camera extends AppCompatActivity {
    private SurfaceView mCamera;
    private BarcodeDetector mBarcodeDetector;
    private CameraSource mCameraSource;
    private TextView data;
    final int RequestCameraPermisionId = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        //inicializar

        mCamera = (SurfaceView) findViewById(R.id.mCameraView);
        data = (TextView) findViewById(R.id.txtResultado);

        //barcode
        mBarcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        mCameraSource = new CameraSource.Builder(this, mBarcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        //añadir eventos
        mCamera.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //solicitamos permiso
                    ActivityCompat.requestPermissions(camera.this,
                            new String[]{Manifest.permission.CAMERA}, RequestCameraPermisionId);
                    return;
                }
                try {
                    mCameraSource.start(mCamera.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mCameraSource.stop();
            }
        });

        mBarcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCode = detections.getDetectedItems();
                if(qrCode.size()!= 0){
                    data.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator mVibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            mVibrator.vibrate(1000);
                            data.setText(qrCode.valueAt(0).displayValue);
                        }
                    });
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case RequestCameraPermisionId:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    try {
                        mCameraSource.start(mCamera.getHolder());
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }

                break;
        }
    }
}
