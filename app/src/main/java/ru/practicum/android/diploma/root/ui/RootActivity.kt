package ru.practicum.android.diploma.root.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private var _binding: ActivityRootBinding? = null
    private val binding get() = _binding!!

    private var _bottomNavigationView: BottomNavigationView? = null
    private val bottomNavigationView get() = _bottomNavigationView!!

    private var _topToolbar: Toolbar? = null
    private val topToolbar get() = _topToolbar!!

    private var _navController: NavController? = null
    private val navController get() = _navController!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
        _topToolbar = binding.topToolbar
        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment

        _navController = navHostFragment.navController

        _bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setupWithNavController(navController)

        setSupportActionBar(topToolbar)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.teamFragment, R.id.searchFragment, R.id.favoritesFragment)
        )

        topToolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.searchFragment,
                R.id.teamFragment,
                R.id.favoritesFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    binding.divider.visibility = View.VISIBLE
                }

                else -> {
                    bottomNavigationView.visibility = View.GONE
                    binding.divider.visibility = View.GONE
                }
            }
        }

    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }
}
