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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ifuknowuknow.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity(), SensorEventListener {


    lateinit var binding: ActivityMainBinding
    lateinit var display_video: VideoView

    //stuff for basic functionality
    var sensor: Sensor? = null
    var sensorManager: SensorManager? = null
    var mediaController: MediaController? = null
    var isRunning: Boolean = true
    var flag = 0

    //stuff for recycler view
    private val LIST_VIEW = "LIST_VIEW"
    var currentView = LIST_VIEW

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
                binding.notStudying.visibility = View.VISIBLE
                display_video.visibility = View.VISIBLE
                display_video.start()
                binding.studying.visibility = View.GONE
                binding.studyMaterial2.visibility = View.VISIBLE
                binding.studyMaterial1.visibility = View.GONE
            }
            else{   //if not checked
                flag = 1
                binding.notStudying.visibility = View.GONE
                display_video.visibility = View.GONE
                binding.studying.visibility = View.VISIBLE
                binding.studyMaterial2.visibility = View.GONE
                binding.studyMaterial1.visibility = View.VISIBLE
            }
        }

        //sensor manager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

        //recycker view
        listView()

    }

    override fun onSensorChanged(event: SensorEvent?) {
        //---------------------------
//        isRunning = false
            try {
                if (event!!.values[0] < 30 && isRunning == false && flag == 0) { // image is visible when surrounding light is dim i.e. <30
                    isRunning = true
                    binding.notStudying.visibility = View.VISIBLE
                    display_video.visibility = View.VISIBLE
                    display_video.start()
                    binding.studying.visibility = View.GONE
                    binding.studyMaterial2.visibility = View.VISIBLE
                    binding.studyMaterial1.visibility = View.GONE
                } else {   // image is invisible when surroundings is bright
                    isRunning = false
                    binding.notStudying.visibility = View.GONE
                    display_video.visibility = View.GONE
                    binding.studying.visibility = View.VISIBLE
                    binding.studyMaterial2.visibility = View.GONE
                    binding.studyMaterial1.visibility = View.VISIBLE
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

    //working on recycler view
    private fun getItemList():ArrayList<String>{
        val list = ArrayList<String>()
        list.add("Take your first steps programming in Kotlin, add images and text to your Android apps, and learn how to use classes, objects, and conditionals to create an interactive app for your users.")
        list.add("Build two different apps, and improve the user interface of your app by learning about layouts, Material Design guidelines, and best practices for UI development.")
        list.add("Enhance your users’ ability to navigate across, into and back out from the various screens within your app for a consistent and predictable user experience.")
        list.add("Write coroutines for complex code, and learn about HTTP and REST to get data from the internet. Then, use the Coil library to display images in your app.")
        list.add("Keep your apps working through any disruptions to essential networks or processes for a smooth and consistent user experience.")
        list.add("Use Android Jetpack’s WorkManager API to schedule necessary background work, like backing up data or downloading fresh content, that keeps running even if the app exits or the device restarts.")
        list.add("Use Kotlin coroutines to perform multiple tasks at once, and learn about HTTP and REST to get data from the internet using Retrofit. Then use the Coil library to display images in your app.")
        list.add("Build apps that display a list of data and learn how to make your apps more beautiful with Material Design.")
        list.add("Continue learning the fundamentals of Kotlin, and start building more interactive apps.Learn the best practices of app architecture to build more complex apps.")
        list.add("Learn the basics of building Android apps with Jetpack Compose, the new UI toolkit for building Android apps. Along the way, you'll develop a collection of apps to start your journey as an Android developer.")

        return list
    }

    private fun listView(){
        currentView = LIST_VIEW
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ItemAdapter(getItemList())
    }

}
