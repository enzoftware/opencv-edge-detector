package com.projects.enzoftware.edge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View

import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.JavaCameraView
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class MainActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {
    internal var javaCameraView: JavaCameraView? = null
    internal var mRGBA: Mat ?= null
    internal var imgGray: Mat ?= null
    internal var imgCanny: Mat ?= null
    internal var mLoader: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                BaseLoaderCallback.SUCCESS -> {
                    javaCameraView!!.enableView()
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        javaCameraView = findViewById<JavaCameraView>(R.id.java_camera_view)
        javaCameraView!!.visibility = SurfaceView.VISIBLE
        javaCameraView!!.setCvCameraViewListener(this)
    }

    override fun onPause() {
        super.onPause()
        if (javaCameraView != null) {
            javaCameraView!!.disableView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (javaCameraView != null) {
            javaCameraView!!.disableView()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "OpenCv has some problems while loading ... ")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_3_0, this, mLoader)
        } else {
            Log.d(TAG, "Success OpenCV loading xd")
            mLoader.onManagerConnected(LoaderCallbackInterface.SUCCESS)

        }
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        mRGBA = Mat(height, width, CvType.CV_8UC4)
        imgGray = Mat(height, width, CvType.CV_8UC1)
        imgCanny = Mat(height, width, CvType.CV_8UC1)
    }

    override fun onCameraViewStopped() {
        mRGBA!!.release()
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat {
        mRGBA = inputFrame.rgba()
        Imgproc.cvtColor(mRGBA, imgGray, Imgproc.COLOR_RGBA2GRAY)
        Imgproc.Canny(imgGray, imgCanny, 50.0, 100.0)
        return imgCanny!!
    }

    companion object {
        var TAG = "MAINACTIVITY"
    }

}
