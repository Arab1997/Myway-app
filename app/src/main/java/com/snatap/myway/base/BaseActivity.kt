package com.snatap.myway.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.snatap.myway.BuildConfig

abstract class BaseActivity(@LayoutRes private val layoutId: Int) : AppCompatActivity() {

    private lateinit var updateManager: UpdateManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        onActivityCreated()

        initUpdateManager()
    }

    private fun initUpdateManager() {
        updateManager = UpdateManager(this).apply {
            if (!BuildConfig.DEBUG) checkUpdate()
        }
    }

    abstract fun onActivityCreated()

    override fun onBackPressed() {
        when {
            supportFragmentManager.backStackEntryCount > 0 -> finishFragment()
            supportFragmentManager.backStackEntryCount == 0 -> exitVariant()
            else -> super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        updateManager.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fragmentsActivityResults(requestCode, resultCode, data)
        updateManager.onActivityResult(requestCode, resultCode)
    }

    private fun fragmentsActivityResults(requestCode: Int, resultCode: Int, data: Intent?) {
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

}