package android.playio.fragments

import androidx.fragment.app.Fragment

import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.widget.LinearLayout
import android.os.Bundle
import android.playio.R
import android.playio.databinding.FragmentBitmapScalingBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class BitmapScalingFragment : Fragment(R.layout.fragment_bitmap_scaling) {
    private lateinit var binding: FragmentBitmapScalingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBitmapScalingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val container = binding.root
        val originalImageView = binding.originalImageHolder
        val bitmap = BitmapFactory.decodeResource(
            resources,
            R.drawable.jellybean_statue
        )
        originalImageView.setImageBitmap(bitmap)
        for (i in 2..9) {
            addScaledImageView(bitmap, i, container)
        }
    }

    private fun addScaledImageView(original: Bitmap, sampleSize: Int, container: LinearLayout) {

        // inSampleSize tells the loader how much to scale the final image, which it does at
        // load time by simply reading less pixels for every pixel value in the final bitmap.
        // Note that it only scales by powers of two, so a value of two results in a bitmap
        // 1/2 the size of the original and a value of four results in a bitmap 1/4 the original
        // size. Intermediate values are rounded down, so a value of three results in a bitmap 1/2
        // the original size.
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inSampleSize = sampleSize
        val scaledBitmap = BitmapFactory.decodeResource(
            resources,
            R.drawable.jellybean_statue, bitmapOptions
        )
        val scaledImageView = ImageView(context)
        scaledImageView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        scaledImageView.setImageBitmap(scaledBitmap)
        container.addView(scaledImageView)
    }

}