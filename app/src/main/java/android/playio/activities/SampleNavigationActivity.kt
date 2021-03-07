package android.playio.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.playio.R
import android.playio.databinding.ActivitySampleNavigationBinding
import android.playio.fragments.BitmapAllocationFragment
import android.playio.fragments.BitmapScalingFragment
import android.playio.fragments.ListViewAnimationFragment
import android.playio.fragments.WebviewFragment
import android.playio.repository.entities.SampleFragmentInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import java.util.*

class SampleNavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySampleNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleNavigationBinding.inflate(layoutInflater)
        val listView = binding.root
        setContentView(listView)
        val arrayAdapter = ArrayAdapter<SampleFragmentInfo>(this, android.R.layout.simple_list_item_1)
        listView.adapter = arrayAdapter
        val sampleFragmentInfoList = createSampleFragmentInfoList()
        arrayAdapter.addAll(sampleFragmentInfoList)
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, SampleDisplayStandActivity::class.java)
            intent.putExtra(SampleDisplayStandActivity.EXTRA_KEY_FRAGMENT_CLASS_NAME,
                            sampleFragmentInfoList[position].fragmentClass.name)
            startActivity(intent)
        }
    }

    private fun createSampleFragmentInfoList(): List<SampleFragmentInfo> {
        val infoList = ArrayList<SampleFragmentInfo>()
        infoList.add(SampleFragmentInfo(BitmapScalingFragment::class.java))
        infoList.add(SampleFragmentInfo(ListViewAnimationFragment::class.java))
        infoList.add(SampleFragmentInfo(WebviewFragment::class.java))
        infoList.add(SampleFragmentInfo(BitmapAllocationFragment::class.java))
        return infoList
    }

    companion object {
        private val TAG = SampleNavigationActivity::class.java.simpleName
    }
}