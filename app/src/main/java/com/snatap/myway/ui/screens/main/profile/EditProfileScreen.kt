package com.snatap.myway.ui.screens.main.profile

import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.User
import com.snatap.myway.utils.extensions.blockClickable
import com.snatap.myway.utils.extensions.loadImage
import com.snatap.myway.utils.extensions.setDrawableStart
import com.snatap.myway.utils.extensions.setTextColorRes
import gun0912.tedimagepicker.builder.TedImagePicker
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_edit_profile.*
import kotlinx.coroutines.launch
import java.io.File

class EditProfileScreen : BaseFragment(R.layout.screen_edit_profile) {

    private var request = false
    override fun initialize() {

        setClicks()

        setDatas()
    }

    private fun setClicks() {

        back.setOnClickListener { finishFragment() }

        title.text = "Личные данные"

        addPhoto.setOnClickListener {
            TedImagePicker.with(requireContext())
                .start {
                    lifecycleScope.launch {
                        val compressedImg = Compressor.compress(
                            requireContext(), File(mainActivity.getFilePath(it))
                        )
                    }
                    image.loadImage(mainActivity.getFilePath(it))
                }
        }

        male.setOnClickListener { setGender(true) }
        female.setOnClickListener { setGender(false) }

        next.setOnClickListener {
            it.blockClickable()
            request = true
            showProgress(true)
        }
    }

    override fun observe() {
        viewModel.data.observe(viewLifecycleOwner, Observer {
            if (request && it is User) {
                request = false
                showProgress(false)
                finishFragment()
            }
        })
    }

    private fun setDatas() {
        sharedManager.user.apply {
            image.loadImage(avatar)
            name.setHint(full_name)
            phoneNumber.setHint(phone)
            userEmail.setHint(email)
            phoneNumber.setHint(phone)
            instagram?.let { userInstagram.setHint(it) }
            address?.let { userAddress.setHint(it) }
//            job.setHint(full_name) // todo
//            dob.setHint() // todo
//            setGender // todo
//            index // todo
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

}