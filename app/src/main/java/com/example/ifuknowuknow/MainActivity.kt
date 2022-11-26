package com.example.ifuknowuknow

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import java.io.IOException

class MainActivity : AppCompatActivity(), SensorEventListener {

    //
    var sensor : Sensor? = null
    var sensorManager : SensorManager? = null
    lateinit var display_image : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //
        display_image = findViewById(R.id.display_img)
        display_image.visibility = View.INVISIBLE

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

    }

    override fun onSensorChanged(event: SensorEvent?) {

        //
        var isRunning = false

        try{
            if(event!!.values[0] < 30 && isRunning == false){ // image is visible when surrounding light is dim i.e. <30
                isRunning = true
                display_image.visibility = View.VISIBLE
            }
            else{   // image is invisible when surroundings is bright
                isRunning = false
                display_image.visibility = View.INVISIBLE
//                display_image.visibility = View.GONE
            }
        }
        catch(e : IOException){

        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }
}