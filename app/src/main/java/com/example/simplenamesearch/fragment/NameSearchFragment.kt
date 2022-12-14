package com.example.simplenamesearch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.simplenamesearch.NameSearchViewModel
import com.example.simplenamesearch.R
import kotlinx.coroutines.*

class NameSearchFragment : Fragment() {

    private lateinit var nameSearchViewModel: NameSearchViewModel
    private lateinit var nameInputEditText: EditText
    private lateinit var submitNameButton: Button

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
        return inflater.inflate(R.layout.fragment_name_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameInputEditText = view.findViewById(R.id.nameInputEditText)
        submitNameButton = view.findViewById(R.id.submitNameButton)

        submitNameButton.setOnClickListener {
            val name = nameInputEditText.text.toString()
            nameSearchViewModel.retrieveNameInfo(name = name)

            val nextFrag = NameResultFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, nextFrag)
                .addToBackStack(null)
                .commit()

            nameInputEditText.text.clear()
        }
    }

    companion object {
        fun newInstance() = NameSearchFragment()
    }
}