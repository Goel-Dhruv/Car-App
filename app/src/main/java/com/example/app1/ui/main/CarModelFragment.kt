package com.example.app1.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import com.example.app1.R


class CarModelFragment : Fragment() {
    var surfaceView: SurfaceView? = null
    var customViewer: CustomViewer = CustomViewer()


    lateinit var rootView :View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_car_model, container, false)
        // Inflate the layout for this fragment
        init()
        return rootView
    }


    fun init() {

        surfaceView = rootView.findViewById<View>(R.id.surface_view) as SurfaceView
        customViewer.run {
            loadEntity()
            setSurfaceView(requireNotNull(surfaceView))

           var pos =  arguments?.get("position")
            if(pos.toString().equals("1")){
                loadGlb(context as Context, "swift", "swift")

            }else {
                //directory and model each as param
                loadGlb(context as Context, "jimny", "jimny")
                //loadGltf(this@MainActivity, "warcraft", "scene");

            }


            //Enviroments and Lightning (OPTIONAL)
            loadIndirectLight(context as Context, "venetian_crossroads_2k")
            //loadEnviroment(this@MainActivity, "venetian_crossroads_2k");
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CarModelFragment().apply {
                arguments = Bundle().apply {
                    putString("ARG_PARAM1", param1)
                }
            }
    }


    override fun onResume() {
        super.onResume()
        customViewer.onResume()
    }

    override fun onPause() {
        super.onPause()
        customViewer.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        customViewer.onDestroy()
    }


}