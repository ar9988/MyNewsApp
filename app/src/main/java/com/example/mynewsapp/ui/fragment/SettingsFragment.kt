package com.example.mynewsapp.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mynewsapp.R
import com.example.mynewsapp.databinding.SettingsFragmentBinding
import com.example.mynewsapp.di.datastore.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment:Fragment() {
    private val dsViewModel:DataStoreViewModel by viewModels()
    private var _binding:SettingsFragmentBinding? = null
    private val binding get() = _binding!!
    private val countryList = arrayOf(
        "United States (US)", "United Kingdom (UK)", "Canada (CA)",
        "Australia (AU)", "Germany (DE)", "France (FR)",
        "Italy (IT)", "Japan (JP)", "South Korea (KR)",
        "Brazil (BR)", "India (IN)", "Russia (RU)",
        "Netherlands (NL)", "Spain (ES)", "Sweden (SE)"
    )

    private val countryCodes = arrayOf(
        "us", "gb", "ca", "au", "de", "fr",
        "it", "jp", "kr", "br", "in", "ru",
        "nl", "es", "se"
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            interestSettingBtn.setOnClickListener{
                parentFragmentManager.beginTransaction().replace(
                    R.id.contentFrame,
                    CategoryFragment()
                ).commit()
            }
            selectNationBtn.setOnClickListener {
                val dialogView = layoutInflater.inflate(R.layout.dialog_country_selection, null)
                val countryListContainer = dialogView.findViewById<LinearLayout>(R.id.country_list_container)
                val dialogBuilder = AlertDialog.Builder(requireContext())
                dialogBuilder.setView(dialogView)
                val dialog = dialogBuilder.create()
                // 국가 목록을 동적으로 추가
                for (i in countryList.indices) {
                    val textView = TextView(requireContext())
                    textView.text = countryList[i]
                    textView.setPadding(16, 16, 16, 16) // Padding 추가
                    textView.setOnClickListener {
                        val selectedCountryCode = countryCodes[i]
//                        saveSelectedCountry(selectedCountryCode)
                        dialog.dismiss()
                    }
                    countryListContainer.addView(textView)
                }

                dialog.show()

            }
            editExcludeNewsComBtn.setOnClickListener {
                val dialogView = layoutInflater.inflate(R.layout.dialog_exclude_news, null)
                val excludeNewsEditText = dialogView.findViewById<EditText>(R.id.exclude_news_edit_text)
                val saveExcludeNewsBtn = dialogView.findViewById<Button>(R.id.save_exclude_news_btn)

                // 다이얼로그 생성
                val dialogBuilder = AlertDialog.Builder(requireContext())
                dialogBuilder.setView(dialogView)
                val dialog = dialogBuilder.create()

                // 저장 버튼 클릭 리스너 설정
                saveExcludeNewsBtn.setOnClickListener {
                    val excludedDomains = excludeNewsEditText.text.toString()
                    if (excludedDomains.isNotEmpty()) {
//                        saveExcludedDomains(excludedDomains)
                        dialog.dismiss() // 다이얼로그 닫기
                    } else {
                        Toast.makeText(requireContext(), "Please enter at least one domain.", Toast.LENGTH_SHORT).show()
                    }
                }

                dialog.show()
            }
            editSearchNewComBtn.setOnClickListener {
                val dialogView = layoutInflater.inflate(R.layout.dialog_search_news, null)
                val searchNewsEditText = dialogView.findViewById<EditText>(R.id.search_news_edit_text)
                val saveSearchNewsBtn = dialogView.findViewById<Button>(R.id.save_search_news_btn)

                // 다이얼로그 생성
                val dialogBuilder = AlertDialog.Builder(requireContext())
                dialogBuilder.setView(dialogView)
                val dialog = dialogBuilder.create()

                // 저장 버튼 클릭 리스너 설정
                saveSearchNewsBtn.setOnClickListener {
                    val searchDomains = searchNewsEditText.text.toString()
                    if (searchDomains.isNotEmpty()) {
//                        saveSearchDomains(searchDomains)
                        dialog.dismiss() // 다이얼로그 닫기
                    } else {
                        Toast.makeText(requireContext(), "Please enter at least one domain.", Toast.LENGTH_SHORT).show()
                    }
                }

                dialog.show()
            }
            editSortBtn.setOnClickListener {
                val dialogView = layoutInflater.inflate(R.layout.dialog_sort_by, null)
                val radioGroup = dialogView.findViewById<RadioGroup>(R.id.sort_radio_group)
                val saveSortBtn = dialogView.findViewById<Button>(R.id.save_sort_btn)

                // 다이얼로그 생성
                val dialogBuilder = AlertDialog.Builder(requireContext())
                dialogBuilder.setView(dialogView)
                val dialog = dialogBuilder.create()

                // 저장 버튼 클릭 리스너 설정
                saveSortBtn.setOnClickListener {
                    val selectedSortOption = when (radioGroup.checkedRadioButtonId) {
                        R.id.radio_relevancy -> "relevancy"
                        R.id.radio_popularity -> "popularity"
                        R.id.radio_publishedAt -> "publishedAt"
                        else -> "publishedAt" // 기본값 설정
                    }

//                    saveSortOption(selectedSortOption)
                    dialog.dismiss() // 다이얼로그 닫기
                }

                dialog.show()
            }
        }
    }

//    private fun saveSortOption(sortOption: String) {
//        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
//        with(sharedPreferences.edit()) {
//            putString("sort_option", sortOption)
//            apply()
//        }
//        Toast.makeText(this, "Sort option saved!", Toast.LENGTH_SHORT).show()
//    }

    //    private fun saveSelectedCountry(countryCode: String) {
//        val preferences = getSharedPreferences("app_settings", MODE_PRIVATE)
//        val editor = preferences.edit()
//        editor.putString("selected_country", countryCode)
//        editor.apply()
//    }
//
//    private fun saveExcludedDomains(domains: String) {
//        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
//        with(sharedPreferences.edit()) {
//            putString("excluded_domains", domains)
//            apply() // 변경 사항 적용
//        }
//        Toast.makeText(requireContext(), "Excluded domains saved!", Toast.LENGTH_SHORT).show()
//    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}