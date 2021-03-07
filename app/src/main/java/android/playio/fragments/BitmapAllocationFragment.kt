package android.playio.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.playio.R
import android.playio.databinding.FragmentBitmapAllocationBinding
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class BitmapAllocationFragment : Fragment(R.layout.fragment_bitmap_allocation) {
    private lateinit var binding: FragmentBitmapAllocationBinding

    private var mCurrentIndex = 0
    private var mCurrentBitmap: Bitmap? = null
    private lateinit var mBitmapOptions: BitmapFactory.Options
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBitmapAllocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imageIDs = intArrayOf(
            R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
            R.drawable.e, R.drawable.f
        )

        val checkbox = binding.checkbox
        val durationTextview = binding.loadDuration
        val imageview = binding.imageview

        // Create bitmap to be re-used, based on the size of one of the bitmaps

        // Create bitmap to be re-used, based on the size of one of the bitmaps
        mBitmapOptions = BitmapFactory.Options()
        mBitmapOptions.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.a, mBitmapOptions)
        mCurrentBitmap = Bitmap.createBitmap(
            mBitmapOptions.outWidth,
            mBitmapOptions.outHeight, Bitmap.Config.ARGB_8888
        )
        mBitmapOptions.inJustDecodeBounds = false
        mBitmapOptions.inBitmap = mCurrentBitmap
        mBitmapOptions.inSampleSize = 1
        BitmapFactory.decodeResource(resources, R.drawable.a, mBitmapOptions)
        imageview.setImageBitmap(mCurrentBitmap)

        // When the user clicks on the image, load the next one in the list

        // When the user clicks on the image, load the next one in the list
        imageview.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % imageIDs.size
            var bitmapOptions: BitmapFactory.Options? = null
            if (checkbox.isChecked) {
                // Re-use the bitmap by using BitmapOptions.inBitmap
                Log.d(TAG, "checked")
                bitmapOptions = mBitmapOptions
                bitmapOptions.inBitmap = mCurrentBitmap
            }
            val startTime = System.currentTimeMillis()
            mCurrentBitmap = BitmapFactory.decodeResource(
                resources,
                imageIDs[mCurrentIndex], bitmapOptions
            )
            imageview.setImageBitmap(mCurrentBitmap)

            // One way you can see the difference between reusing and not is through the
            // timing reported here. But you can also see a huge impact in the garbage
            // collector if you look at logcat with and without reuse. Avoiding garbage
            // collection when possible, especially for large items like bitmaps,
            // is always a good idea.
            durationTextview.text = "Load took ${System.currentTimeMillis() - startTime}"
        }
    }

    companion object {
        private val TAG = BitmapAllocationFragment::class.java.simpleName
    }
}