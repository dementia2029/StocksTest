package com.test.vadymshved.features.chart

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.formatter.ValueFormatter
import com.test.vadymshved.R
import com.test.vadymshved.databinding.FragmentChartBinding
import com.test.vadymshved.utils.ResourcesHelper
import com.test.vadymshved.utils.refreshData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChartFragment : Fragment(R.layout.fragment_chart) {
    private val chartViewModel: ChartViewModel by viewModels()
    @Inject
    lateinit var formatter: ValueFormatter
    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FragmentChartBinding.bind(view).apply {
            _binding = this
            viewModel = chartViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        chartViewModel.filteredData.observe(viewLifecycleOwner) {
            binding.chart1.refreshData(it)
        }

        chartViewModel.selectedPeriod.observe(viewLifecycleOwner){
            binding.switchButton.text = ResourcesHelper.getTextForSelectedPeriod(it, mContext)
        }

        binding.chart1.apply {
            xAxis.valueFormatter = formatter
            description.text = getString(R.string.chart_description)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}