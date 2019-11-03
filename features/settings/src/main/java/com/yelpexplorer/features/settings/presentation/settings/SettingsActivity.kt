package com.yelpexplorer.features.settings.presentation.settings

import android.os.Bundle
import android.view.MenuItem
import com.yelpexplorer.features.settings.R
import com.yelpexplorer.features.settings.databinding.ActivitySettingsBinding
import com.yelpexplorer.libraries.core.BuildConfig
import dagger.android.support.DaggerAppCompatActivity

class SettingsActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.settings)

        render()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun render() {
        binding.tvVersion.text = String.format(getString(R.string.version), BuildConfig.VERSION_NAME)
    }
}
