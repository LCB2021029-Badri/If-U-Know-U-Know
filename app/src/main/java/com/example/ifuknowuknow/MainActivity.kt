package com.example.ifuknowuknow

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import java.io.IOException

class MainActivity : AppCompatActivity(), SensorEventListener {

    //
    var sensor : Sensor? = null
    var sensorManager : SensorManager? = null
    lateinit var display_video : VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //image instead of video
        display_video = findViewById(R.id.video1)
        display_video.visibility = View.INVISIBLE

        // video instead of image
        val mediaController = MediaController(this)
        mediaController.setAnchorView(display_video)
        val offlineUri = Uri.parse("android.resource://$packageName/${R.raw.mvd1}")
        display_video.setMediaController(mediaController)
        display_video.setVideoURI(offlineUri)
        display_video.requestFocus()
        display_video.start()

        //sensor implementation
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

    }

    override fun onSensorChanged(event: SensorEvent?) {

        //
        var isRunning = false

        try{
            if(event!!.values[0] < 30 && isRunning == false){ // image is visible when surrounding light is dim i.e. <30
                isRunning = true
                display_video.visibility = View.VISIBLE
                display_video.resume()
//                display_video.resume()
            }
            else{   // image is invisible when surroundings is bright
                isRunning = false
                display_video.visibility = View.INVISIBLE
                display_video.pause()
//                display_image.visibility = View.GONE
//                display_video.pause()
            }
        }
        catch(e : IOException){

        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onResume() {
        super.onResume()
//        display_video.resume()
        sensorManager!!.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
//        display_video.pause()
        sensorManager!!.unregisterListener(this)
    }
}