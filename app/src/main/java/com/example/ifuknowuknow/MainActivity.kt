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
import android.widget.Toast
import android.widget.VideoView
import com.example.ifuknowuknow.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity(), SensorEventListener {

    //
    var sensor: Sensor? = null
    var sensorManager: SensorManager? = null
    lateinit var display_video: VideoView
    var mediaController: MediaController? = null
    lateinit var binding: ActivityMainBinding
    var isRunning: Boolean = true
    var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //image instead of video
        display_video = findViewById(R.id.video1)
        display_video.visibility = View.INVISIBLE

        // video instead of image
        mediaController = MediaController(this)
        mediaController!!.setAnchorView(display_video)
        val offlineUri = Uri.parse("android.resource://$packageName/${R.raw.mvd1}")
        display_video.setMediaController(mediaController)
        display_video.setVideoURI(offlineUri)
        display_video.requestFocus()

        //sensor implementation
        binding.toggleButton.setOnCheckedChangeListener { compoundButton, b ->
            if(b){  //if checked
                flag = 0
                display_video.visibility = View.VISIBLE
                display_video.start()
                binding.studying.visibility = View.GONE
            }
            else{   //if not checked
                flag = 1
                display_video.visibility = View.GONE
                binding.studying.visibility = View.VISIBLE
            }
        }
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

    }

    override fun onSensorChanged(event: SensorEvent?) {
        //---------------------------
//        isRunning = false
            try {
                if (event!!.values[0] < 30 && isRunning == false && flag == 0) { // image is visible when surrounding light is dim i.e. <30
                    isRunning = true
                    display_video.visibility = View.VISIBLE
                    display_video.start()
                    binding.studying.visibility = View.GONE
                } else {   // image is invisible when surroundings is bright
                    isRunning = false
                    display_video.visibility = View.GONE
                    binding.studying.visibility = View.VISIBLE
                }
            }
            catch (e: IOException){
            }
        //--------------------------

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }
}
