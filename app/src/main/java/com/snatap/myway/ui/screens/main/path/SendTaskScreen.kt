package com.snatap.myway.ui.screens.main.path

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import androidx.lifecycle.Observer
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.SuccessResp
import com.snatap.myway.ui.adapters.ImageData
import com.snatap.myway.ui.adapters.TaskImagesAdapter
import com.snatap.myway.ui.adapters.Types
import com.snatap.myway.ui.screens.main.common.ImageScreen
import com.snatap.myway.ui.screens.main.common.VideoScreen
import com.snatap.myway.utils.extensions.blockClickable
import com.snatap.myway.utils.extensions.invisible
import com.snatap.myway.utils.extensions.snackbar
import com.snatap.myway.utils.extensions.toast
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_send_task.*
import java.io.File


class SendTaskScreen : BaseFragment(R.layout.screen_send_task) {

    companion object {
        private var lessonId: Int = 0
        fun newInstance(lessonId: Int): SendTaskScreen {
            this.lessonId = lessonId
            return SendTaskScreen()
        }
    }

    private var request = false
    private var adapter: TaskImagesAdapter? = null
    private var selectedFiles = ArrayList<ImageData>()

    override fun initialize() {

        windowAdjustResize()

        initViews()

        setClicks()
    }

    private fun initViews() {
        back.invisible()

        title.text = "Отправить задание"

        right.setImageResource(R.drawable.ic_close)

        adapter = TaskImagesAdapter(true) { delete, data ->
            if (delete) {
                selectedFiles.remove(data)
                adapter!!.setData(selectedFiles)
            } else {
                when (data.type) {
                    Types.IMAGE -> addFragment(ImageScreen.newInstance(data.path))
                    Types.VIDEO -> addFragment(VideoScreen.newInstance(data.path))
                    Types.FILE -> mainActivity.openPdf(data.path)
                }
            }
        }
        recycler.adapter = adapter
    }

    private fun setClicks() {

        right.setOnClickListener { finishFragment() }

        chooseFileBtn.setOnClickListener {
            it.blockClickable()
            showDialog()
        }

        send.setOnClickListener { view ->
            view.blockClickable()

            message.text.toString().let {
                if (it.isEmpty()) {
                    snackbar(requireView(), "Поле сообщение обязательно для заполнения.")
                    return@setOnClickListener
                }
            }
            request = true
            showProgress(true)
            val images = selectedFiles.filter { it.type == Types.IMAGE }.mapIndexed { index, it ->
                mainActivity.createFileMultipart("photo[$index]", File(it.path))
            }.distinct()
            val videos = selectedFiles.filter { it.type == Types.VIDEO }.mapIndexed { index, it ->
                mainActivity.createFileMultipart("video[$index]", File(it.path))
            }.distinct()
            val files = selectedFiles.filter { it.type == Types.FILE }.mapIndexed { index, it ->
                mainActivity.createFileMultipart("file[$index]", File(it.path))
            }.distinct()

            viewModel.uploadReport(
                lessonId,
                message.text.toString(),
                if (images.isNotEmpty()) images else null,
                if (videos.isNotEmpty()) videos else null,
                if (files.isNotEmpty()) files else null
            )
        }
    }

    private fun showDialog() {
        val selectItems = arrayOf("Выбрать фото", "Выбать видео", "Выбрать файл", "Отмена")
        val alertDialog = context?.let { AlertDialog.Builder(it) }
        alertDialog?.setItems(selectItems) { dialog, which ->
            when (which) {
                0 -> openImg()
                1 -> openVideo()
                2 -> openFile()
                3 -> dialog.cancel()
            }
        }
        alertDialog?.show()
    }

    private fun openImg() {
        val intent = Intent(requireContext(), FilePickerActivity::class.java).apply {
            putExtra(
                FilePickerActivity.CONFIGS, Configurations.Builder()
                    .setCheckPermission(true)
                    .setShowImages(true)
                    .setShowVideos(false)
                    .setShowFiles(false)
                    .enableImageCapture(true)
                    .setSingleChoiceMode(true)
                    .setSkipZeroSizeFiles(true)
                    .build()
            )
        }
        startActivityForResult(intent, 123)
    }

    private fun openVideo() {
        val intent = Intent(requireContext(), FilePickerActivity::class.java).apply {
            putExtra(
                FilePickerActivity.CONFIGS, Configurations.Builder()
                    .setCheckPermission(true)
                    .setShowVideos(true)
                    .setShowFiles(false)
                    .setShowImages(false)
                    .enableVideoCapture(true)
                    .setSingleChoiceMode(true)
                    .setSkipZeroSizeFiles(true)
                    .build()
            )
        }
        startActivityForResult(intent, 123)
    }

    private fun openFile() {
        val intent = Intent(requireContext(), FilePickerActivity::class.java).apply {
            putExtra(
                FilePickerActivity.CONFIGS, Configurations.Builder()
                    .setCheckPermission(true)
                    .setShowFiles(true)
                    .setShowImages(false)
                    .setShowVideos(false)
                    .setSingleChoiceMode(true)
                    .setSkipZeroSizeFiles(true)
                    .build()
            )
        }
        startActivityForResult(intent, 123)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123 && resultCode == RESULT_OK && data != null) {
            data.getParcelableArrayListExtra<MediaFile>(FilePickerActivity.MEDIA_FILES)?.let {
                selectedFiles.addAll(it.map {
                    val types: Types = when (it.mediaType) {
                        MediaFile.TYPE_IMAGE -> Types.IMAGE
                        MediaFile.TYPE_VIDEO -> Types.VIDEO
                        MediaFile.TYPE_FILE -> Types.FILE
                        else -> Types.FILE
                    }
                    ImageData(it.path, types)
                })
                adapter?.setData(selectedFiles)
            }
        }
    }

    override fun observe() {
        viewModel.data.observe(viewLifecycleOwner, Observer {
            if (request && it is SuccessResp) {
                request = false
                showProgress(false)
                toast(requireContext(), if (it.success) "Отправлено" else "Попробуйте еще раз")
                finishFragment()
            }
        })
    }

}
