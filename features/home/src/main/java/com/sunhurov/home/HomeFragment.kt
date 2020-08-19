package com.sunhurov.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunhurov.common.base.BaseFragment
import com.sunhurov.common.base.BaseViewModel
import com.sunhurov.common.extension.MultiTextWatcher
import com.sunhurov.common.extension.TextWatcherWithInstance
import com.sunhurov.home.databinding.FragmentHomeBinding
import com.sunhurov.model.Station
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * A simple [BaseFragment] subclass
 * that will search [Station]
 */
class HomeFragment : BaseFragment() {

    // FOR DATA
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadStations()
        home_refresh_layout_view.isEnabled = false
        setViewListeners()
    }

    private fun setViewListeners() {
        MultiTextWatcher()
            .registerEditText(text_start_station)
            .setCallback(object : TextWatcherWithInstance {
                override fun onTextChanged(s: CharSequence?) {
                    if (s!= null && s.length >= text_start_station.threshold) {
                        viewModel.onStartStationTextChanged(s.toString());
                    }
                }
            })

        MultiTextWatcher()
            .registerEditText(text_end_station)
            .setCallback(object : TextWatcherWithInstance {
                override fun onTextChanged(s: CharSequence?) {
                    if (s != null && s.length >= text_end_station.threshold) {
                        viewModel.onEndStationTextChanged(s.toString());
                    }
                }
            })

        text_end_station.setOnItemClickListener { _, _, position, _ ->
            viewModel.onStationEndSelected(position)
        }

        text_start_station.setOnItemClickListener { _, _, position, _ ->
            viewModel.onStationStartSelected(position)
        }
    }



    override fun getViewModel(): BaseViewModel = viewModel


}
