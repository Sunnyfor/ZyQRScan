package com.sunny.zxing.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.util.Log
import androidx.core.app.ActivityCompat

/**
 * Desc
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2020/10/25 22:51
 */
object OpenCameraInterface {

    private val TAG = OpenCameraInterface::class.java.name

    fun open(context: Context, cameraId: Int, stateCallback: CameraDevice.StateCallback) {

        val cameraManager = getCameraManager(context)

        val cameraIdList = cameraManager.cameraIdList

        if (cameraIdList.isEmpty()) {
            Log.w(TAG, "No cameras!")
            return
        }

        cameraId.toString().let {
            if (!cameraIdList.contains(it)) {
                Log.w(TAG, "Requested camera does not exist: $cameraId")
                return
            }

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
            {
                Log.w(TAG, "Missing camera permissions")
                return
            }
            cameraManager.openCamera(it, stateCallback, null)
        }
    }


    fun getCameraManager(context: Context): CameraManager {
        return context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }
}