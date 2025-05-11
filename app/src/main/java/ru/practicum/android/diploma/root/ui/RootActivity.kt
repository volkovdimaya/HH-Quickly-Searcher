package ru.practicum.android.diploma.root.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRootBinding
    private lateinit var bottomNavigationView : BottomNavigationView
    private lateinit var topToolbar : Toolbar
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
        topToolbar = binding.topToolbar
        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.searchFragment -> {
                    searchFragmentState()
                }
                R.id.favoritesFragment -> {
                    favoritesFragmentState()
                }
                R.id.teamFragment -> {
                    teamFragmentState()
                }
                R.id.filtersFragment -> {
                    filtersFragmentState()
                }
                R.id.vacancyDetailsFragment -> {
                    vacancyDetailsFragmentState()
                }
                R.id.choosingWorkTerritoryFragment -> {
                    choosingWorkterritoryFragmentState()
                }
                R.id.choosingCountryFragment -> {
                    choosingCountryFragmentState()
                }
                R.id.choosingRegionFragment -> {
                    choosingRegionFragmentState()
                }

                else -> {
                    elseFragmentState()
                }
            }
        }

        topToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.actionSharing -> {
                    // TODO
                    true
                }

                R.id.action_favorite -> {
                    // TODO
                    true
                }

                R.id.action_filters_fragment -> {
                    navController.navigate(R.id.action_searchFragment_to_filtersFragment)
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun searchFragmentState() {
        bottomNavigationView.visibility = View.VISIBLE
        topToolbar.title = getString(R.string.search_vacancy)
        topToolbar.inflateMenu(R.menu.search_fragment_toolbar_menu)
        deleteNavigationIcon()
    }

    private fun favoritesFragmentState() {
        bottomNavigationView.visibility = View.VISIBLE
        binding.topToolbar.title = getString(R.string.favorites)
        topToolbar.menu.clear()
        deleteNavigationIcon()
    }

    private fun teamFragmentState() {
        bottomNavigationView.visibility = View.VISIBLE
        binding.topToolbar.title = getString(R.string.team)
        topToolbar.menu.clear()
        deleteNavigationIcon()
    }

    private fun filtersFragmentState() {
        bottomNavigationView.visibility = View.GONE
        binding.topToolbar.title = getString(R.string.filter_settings)
        topToolbar.menu.clear()
        setNavigationIcon()
    }

    private fun vacancyDetailsFragmentState() {
        bottomNavigationView.visibility = View.GONE
        binding.topToolbar.title = getString(R.string.vacancy)
        topToolbar.inflateMenu(R.menu.vacancy_details_fragment_toolbar_menu)
        setNavigationIcon()
    }

    private fun choosingWorkterritoryFragmentState() {
        bottomNavigationView.visibility = View.GONE
        binding.topToolbar.title = getString(R.string.choosing_work_territory)
        topToolbar.menu.clear()
        setNavigationIcon()
    }

    private fun choosingCountryFragmentState() {
        bottomNavigationView.visibility = View.GONE
        binding.topToolbar.title = getString(R.string.choosing_country)
        topToolbar.menu.clear()
        setNavigationIcon()
    }

    private fun choosingRegionFragmentState() {
        bottomNavigationView.visibility = View.GONE
        topToolbar.title = getString(R.string.choosing_region)
        topToolbar.menu.clear()
        setNavigationIcon()
    }


    private fun elseFragmentState() {
        bottomNavigationView.visibility = View.GONE
        binding.topToolbar.visibility = View.GONE
        topToolbar.menu.clear()
    }

    private fun setNavigationIcon() {
        topToolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_leading_icon)
        topToolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }
    }

    private fun deleteNavigationIcon(){
        topToolbar.setNavigationIcon(null)
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }


}
