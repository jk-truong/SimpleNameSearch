package com.example.simplenamesearch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenamesearch.Country
import com.example.simplenamesearch.NameSearchViewModel
import com.example.simplenamesearch.R

class NameResultFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    private lateinit var nameSearchViewModel: NameSearchViewModel
    private lateinit var textViewResult: TextView
    private lateinit var textViewAge: TextView
    private lateinit var textViewGender: TextView
    private lateinit var nationalityRecyclerView: RecyclerView
    private lateinit var textViewNationalityInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nameSearchViewModel =
            ViewModelProviders.of(requireActivity())[NameSearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_name_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progressBar)
        textViewResult = view.findViewById(R.id.textViewResult)
        textViewAge = view.findViewById(R.id.textViewAge)
        textViewGender = view.findViewById(R.id.textViewGender)
        nationalityRecyclerView = view.findViewById(R.id.recyclerViewNationality)
        textViewNationalityInfo = view.findViewById(R.id.textViewNationalityInfo)

        nameSearchViewModel.getAge().observe(viewLifecycleOwner) { ageItem ->
            if (ageItem != null) {
                progressBar.visibility = View.GONE
                val ageStr = getString(R.string.age_string)
                textViewAge.text = String.format(ageStr, ageItem.age)

                val headerStr = getString(R.string.your_results)
                textViewResult.text = String.format(headerStr, ageItem.name)
            }
        }

        nameSearchViewModel.getGender().observe(viewLifecycleOwner) { genderItem ->
            if (genderItem != null) {
                val genderStr = getString(R.string.gender_string)
                textViewGender.text =
                    String.format(genderStr, genderItem.gender, genderItem.probability)
            }
        }

        nameSearchViewModel.getNationality().observe(viewLifecycleOwner) { nationalityItem ->
            textViewNationalityInfo.visibility = View.VISIBLE
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            nationalityRecyclerView.layoutManager = layoutManager
            nationalityRecyclerView.adapter = RecyclerViewAdapter(
                countryList = nationalityItem.country
            )
        }
    }

    private inner class NationalityViewHolder(itemLayout: RelativeLayout) :
        RecyclerView.ViewHolder(itemLayout) {
        val textViewNationality: TextView = itemView.findViewById(R.id.textViewNationality)
    }

    private inner class RecyclerViewAdapter(
        val countryList: List<Country>
    ) : RecyclerView.Adapter<NationalityViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NationalityViewHolder {
            val view = layoutInflater.inflate(
                R.layout.list_item_nationality,
                parent,
                false
            ) as RelativeLayout
            return NationalityViewHolder(view)
        }

        override fun onBindViewHolder(holder: NationalityViewHolder, position: Int) {
            val nationalityStr = getString(R.string.country_string)
            holder.textViewNationality.text = String.format(
                nationalityStr,
                countryList[position].countryId,
                countryList[position].probability
            )
        }

        override fun getItemCount(): Int = countryList.size
    }
}