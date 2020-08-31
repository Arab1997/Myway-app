package com.snatap.myway.ui.screens.main.profile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.UserRequest
import com.snatap.myway.utils.extensions.*
import com.snatap.myway.utils.validators.TextValidator
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_edit_profile.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class EditProfileScreen : BaseFragment(R.layout.screen_edit_profile) {

    @SuppressLint("SimpleDateFormat")
    private val dateMonthForUser = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))

    private val calendar = Calendar.getInstance()
    private var request = false
    override fun initialize() {

        windowAdjustResize()

        setClicks()

        initProfile()
    }

    private var compressedImg: File? = null
    private fun setClicks() {

        back.setOnClickListener { finishFragment() }

        title.text = "Личные данные"

        addPhoto.setOnClickListener {
            TedImagePicker.with(requireContext())
                .start {
                    compressedImg = File(mainActivity.getFilePath(it))
                    image.loadImage(compressedImg)
                }
        }

        male.setOnClickListener { setGender(true) }
        female.setOnClickListener { setGender(false) }
        dob.setOnClickListener {
            loge("click")
            setDate()
        }

        next.setOnClickListener {
            it.blockClickable()
            showProgress(true)
            request = true
            editUserData()
            editUserImage(compressedImg)
        }
    }

    private fun setDate() {
        DatePickerDialog(
            requireContext(), { _, year, monthOfYear, dayOfMonth ->
                val cal = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, monthOfYear)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }
                dob.setText(dateMonthForUser.format(cal.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun observe() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            if (request) {
                request = false
                showProgress(false)
                toast(requireContext(), "Данные обновлены")
                finishFragment()
            }
        })
    }

    private fun initProfile() {
        sharedManager.user.apply {
            image.loadImage(avatar)
            phoneNumber.setText(phone)
            full_name?.let { name.setText(it) }
            job?.let { userJob.setText(it) }
            email?.let { userEmail.setText(it) }
            post_index?.let { index.setText(it) }
            date_of_birth?.let { dob.setText(it) }
            address?.let { userAddress.setText(it) }
            instagram?.let { userInstagram.setText(it) }
            setGender(gender == "m")
        }

        userEmail.onTextChanged {
            if (!TextValidator.isEmail(it)) userEmail.setError("Email не является допустимым")
            else userEmail.showHideError(false)
        }
    }

    private fun setGender(isMale: Boolean) {
        male.apply {
            setBackgroundResource(if (isMale) R.drawable.rounded_red_card_transparent else R.drawable.rounded_edt_card)
            setTextColorRes(if (isMale) R.color.red else R.color.black)
            setDrawableStart(if (isMale) R.drawable.ic_male_red else R.drawable.ic_male)
        }
        female.apply {
            setBackgroundResource(if (!isMale) R.drawable.rounded_red_card_transparent else R.drawable.rounded_edt_card)
            setTextColorRes(if (!isMale) R.color.red else R.color.black)
            setDrawableStart(if (!isMale) R.drawable.ic_female_red else R.drawable.ic_female)
        }
    }

    private fun editUserData() {
        viewModel.editUser(
            UserRequest(
                name.getText().getOrNull(),
                userInstagram.getText().getOrNull(),
                userAddress.getText().getOrNull(),
                userEmail.getText().getOrNull(),
                phoneNumber.getText().getOrNull(),
                userJob.getText().getOrNull(),
                dob.getText().getOrNull(),
                index.getText().getOrNull()
            )
        )
    }

    private fun String.getOrNull(): String? = if (this.isEmpty()) null else this

    private fun editUserImage(file: File?) {
        file?.let {
            val requestBody = createBuilder(it).build()
            viewModel.updateUserPhoto(requestBody)
        }
    }

    private fun createBuilder(file: File): MultipartBody.Builder {
        val builder: MultipartBody.Builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart(
            "avatar", file.name, file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        )
        return builder
    }

}