package com.example.buddy.ui.home.upload

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.buddy.R
import com.example.buddy.databinding.FragmentUploadBinding
import com.example.buddy.domain.models.Posts
import com.example.buddy.domain.models.UserFirebase
import com.example.buddy.domain.repo.FireStoreRepository
import com.example.buddy.ui.home.feeds.Feeds
import com.example.buddy.ui.home.profile.ProfileViewModel


class Upload : Fragment(R.layout.fragment_upload) {

    companion object {
        fun getInstance() = Upload()
    }

    private lateinit var binding: FragmentUploadBinding
    private var photoURI: Uri? = null

    private val uploadViewModel by lazy {
        ViewModelProvider(
            this, UploadViewModel.Factory(fireStoreRepository = FireStoreRepository.get())
        )[UploadViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUploadBinding.bind(view)
        binding.lifecycleOwner = this

        binding.userPostImage.setOnClickListener {
            checkPermissionForImage()
        }

        binding.submitButton.setOnClickListener {

           photoURI?.let {
               submitImage(it)
           }
            Toast.makeText(requireContext(), "succcc", Toast.LENGTH_SHORT).show()
        }
    }

    private fun submitImage(photoUri: Uri) {

        uploadViewModel.uploadPostInFirebase(photoUri, onSuccess = {
//            findNavController().navigate(R.id.fragment_feeds)
            Toast.makeText(requireContext(), "post uploaded", Toast.LENGTH_SHORT).show()

        }, getPost = {
            Posts(
                description =  binding.descriptionEditText.text.toString(),
                imageUrl = it,
                creationTimeMs = System.currentTimeMillis(),
                user = UserFirebase("ravi", age = 50)
            )
        })

//        if (photoURI == null ||binding.descriptionEditText.text.isBlank()) {
//            Toast.makeText(requireContext(), "No photo selected or Description cannot be empty ", Toast.LENGTH_SHORT).show()
//            return
//        }

    }

    private fun checkPermissionForImage() {
        if ((ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED)
            && (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED)
        ) {
            val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            val permissionWrite = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

            requestPermissions(
                permission,
                1001
            ) // GIVE AN INTEGER VALUE FOR PERMISSION_CODE_READ LIKE 1001
            requestPermissions(
                permissionWrite,
                1002
            ) // GIVE AN INTEGER VALUE FOR PERMISSION_CODE_WRITE LIKE 1002
        } else {
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


            if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
                data?.data?.let {
                    binding.userPostImage.setImageURI(it)
                    uploadingImageInFirebase(it)
                }
            } else {
                Toast.makeText(requireContext(), "Image picker action canceled", Toast.LENGTH_SHORT)
                    .show()
            }

    }

    private fun uploadingImageInFirebase(it: Uri) {
        photoURI = it

//        uploadViewModel.uploadPostInFirebase(it, onSuccess = {
////            findNavController().navigate(R.id.fragment_feeds)
//            Toast.makeText(requireContext(), "post uploaded", Toast.LENGTH_SHORT).show()
//            submitImage()
//
//        }, getPost = {
//            Posts(
//                description =  binding.descriptionEditText.text.toString(),
//                imageUrl = it,
//                creationTimeMs = System.currentTimeMillis(),
//                user = UserFirebase("ravi", age = 50)
//            )
//        })

    }

}