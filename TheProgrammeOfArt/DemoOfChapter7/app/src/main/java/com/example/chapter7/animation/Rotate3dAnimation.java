package com.example.chapter7.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3dAnimation extends Animation {

    private float mFromDegrees;
    private float mToDegrees;
    private float mCenterX;
    private float mCenterY;
    private float mDepthZ;
    private boolean mReverse;
    private Camera mCamera;

    public Rotate3dAnimation(float fromDegrees, float toDegrees,
                             float centerX, float centerY,
                             float depthZ, boolean reverse){
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        mReverse = reverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees +  ((mToDegrees - fromDegrees)*interpolatedTime);
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;

        final Matrix matrix = t.getMatrix();
        camera.save();

        if (mReverse){
            camera.translate(0.0f, 0.0f, mDepthZ*interpolatedTime);;
        }else {
            camera.translate(0.0f, 0.0f, mDepthZ*(1.0f - interpolatedTime));
        }
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX,-centerX);
        matrix.postTranslate(centerX,centerY);

    }
}
