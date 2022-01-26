package com.opemweather.test.presentation.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseFragment<BINDING : ViewBinding, VIEW_MODEL : BaseViewModel> : Fragment() {

    protected val binding: BINDING
        get() = _binding!!

    protected var _binding: BINDING? = null

    protected abstract val viewModel: VIEW_MODEL

    private val jobs: MutableList<Job> = mutableListOf()

    protected fun subscribe(block: suspend () -> Unit) {
        jobs.add(
            lifecycleScope.launch { block.invoke() }
        )
    }

    override fun onDestroyView() {
        jobs.forEach { job -> job.cancel() }
        super.onDestroyView()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}