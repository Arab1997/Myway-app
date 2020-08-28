package com.snatap.myway.ui.activities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.KeyEvent
import com.snatap.myway.R
import com.snatap.myway.base.BaseActivity
import com.snatap.myway.base.BaseViewModel
import com.snatap.myway.base.initialFragment
import com.snatap.myway.ui.screens.BottomNavScreen
import com.snatap.myway.ui.screens.main.home.live.StreamsFragment
import com.snatap.myway.ui.screens.splash.SplashScreen
import com.snatap.myway.utils.extensions.showGone
import com.snatap.myway.utils.preferences.SharedManager
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : BaseActivity(R.layout.activity_main) {

    val viewModel by viewModel<BaseViewModel>()
    val sharedManager: SharedManager by inject()

    override fun onActivityCreated() {
        viewModel.apply {
            parentLayoutId = R.id.fragmentContainer
            navLayoutId = R.id.navContainer

            fetchData()
        }

        sharedManager.code = "4444" // todo

//        debug()
        startFragment()
    }

    private fun debug() = initialFragment(StreamsFragment())

    private fun startFragment() {
        initialFragment(
            if (sharedManager.token.isEmpty()) SplashScreen()
            else BottomNavScreen(), true
        )
    }

    fun showProgress(show: Boolean) {
        progressBar.showGone(show)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_VOLUME_UP -> return false
                KeyEvent.KEYCODE_VOLUME_DOWN -> return false
            }
        }
        return super.dispatchKeyEvent(event)
    }


    fun getFilePath(it: Uri): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) it.path!!
        else {
            //Later we will use this bitmap to create the File.
            val selectedBitmap = getBitmap(this, it)!!

            /*We can access getExternalFileDir() without asking any storage permission.*/
            val selectedImgFile = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                System.currentTimeMillis()
                    .toString() + "_selectedImg.jpg"
            )

            convertBitmapToFile(selectedImgFile, selectedBitmap)
            selectedImgFile.path
        }
    }

    private fun getBitmap(context: Context, imageUri: Uri): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, imageUri))
        } else {
            context.contentResolver.openInputStream(imageUri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        }
    }

    private fun convertBitmapToFile(destinationFile: File, bitmap: Bitmap) {
        //create a file to write bitmap data
        destinationFile.createNewFile()
        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos)
        val bitmapData = bos.toByteArray()
        //write the bytes in file
        val fos = FileOutputStream(destinationFile)
        fos.write(bitmapData)
        fos.flush()
        fos.close()
    }

}
