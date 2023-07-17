package com.hmisael.linternaapp

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.camera2.CameraCharacteristics.FLASH_INFO_AVAILABLE
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.hmisael.linternaapp.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    val camera by lazy {
        activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }
    var cameraId : String = "0"

    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(Build.VERSION_CODES.M)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cameraList = camera.cameraIdList

        //TODO apagado/encendido

        binding.buttonFirst.setOnClickListener {
        cameraList.forEach {
            val caracteristicas = camera.getCameraCharacteristics(it)
            val camaraTieneFlash: Boolean? = caracteristicas.get(FLASH_INFO_AVAILABLE)
            if (cameraId == "0" && camaraTieneFlash == true ){
                cameraId = it
            }
        }

            camera.setTorchMode(cameraId, true)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        camera.setTorchMode(cameraId, false)
    }

}