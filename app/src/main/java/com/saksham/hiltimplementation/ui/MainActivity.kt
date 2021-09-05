package com.saksham.hiltimplementation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.saksham.hiltimplementation.R
import com.saksham.hiltimplementation.model.Blog
import com.saksham.hiltimplementation.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogsEvent)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, {
            when (it) {
                is DataState.Success<List<Blog>> -> {
                    displayProgressBar(false)
                    appendBlogTitles(it.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(it.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(message: String?) {
        if (message != null)
            findViewById<TextView>(R.id.text).text = message
        else
            findViewById<TextView>(R.id.text).text = "Unknown Error"
    }

    private fun displayProgressBar(isDiaplayed: Boolean) {
        findViewById<ProgressBar>(R.id.progress_bar).visibility =
            if (isDiaplayed) View.VISIBLE else View.GONE
    }

    private fun appendBlogTitles(blog: List<Blog>) {
        val sb = StringBuilder()
        blog.forEach {
            sb.append(it.title + "\n")
        }
        findViewById<TextView>(R.id.text).text = sb.toString()

    }
}