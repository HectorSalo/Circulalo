package com.skysam.hchirinos.circulalo

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.skysam.hchirinos.circulalo.common.Permission
import com.skysam.hchirinos.circulalo.databinding.ActivityMainBinding
import com.skysam.hchirinos.circulalo.ui.post.PostActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            startActivity(Intent(this, PostActivity::class.java))
        } else {
            Snackbar.make(binding.root, getString(R.string.error_permission_read), Snackbar.LENGTH_SHORT)
                .setAnchorView(R.id.coordinator).show()
        }
    }

    private val request = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val itemProfile = navView.menu.getItem(4)
        itemProfile.icon = ContextCompat.getDrawable(this, R.drawable.test)
        navView.itemIconTintList = null
        /*Glide.with(this)
            .load(R.drawable.test)
            .centerCrop()
            .circleCrop()
            .placeholder(R.drawable.ic_profile_24)
            .into(itemProfile.icon!!)*/

        binding.fab.setOnClickListener {
            if (Permission.checkPermission()) {
                startActivity(Intent(this, PostActivity::class.java))
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                } else {
                    requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
            //request.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
    }
}