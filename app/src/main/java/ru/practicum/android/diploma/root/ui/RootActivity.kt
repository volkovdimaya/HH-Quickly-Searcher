package ru.practicum.android.diploma.root.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            renderStateViews(destination)
        }

        topToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.actionSharing -> {
                    // ..
                    true
                }

                R.id.action_favorite -> {
                    // ..
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

    private fun renderStateViews(destination: NavDestination) {
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

            R.id.workTerritoriesFragment -> {
                workTerritoriesFragmentState()
            }

            R.id.countriesFragment -> {
                countriesFragmentState()
            }

            R.id.regionsFragment -> {
                regionsFragmentState()
            }

            else -> {
                elseFragmentState()
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

    private fun workTerritoriesFragmentState() {
        bottomNavigationView.visibility = View.GONE
        binding.topToolbar.title = getString(R.string.choosing_work_territory)
        topToolbar.menu.clear()
        setNavigationIcon()
    }

    private fun countriesFragmentState() {
        bottomNavigationView.visibility = View.GONE
        binding.topToolbar.title = getString(R.string.choosing_country)
        topToolbar.menu.clear()
        setNavigationIcon()
    }

    private fun regionsFragmentState() {
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

    private fun deleteNavigationIcon() {
        topToolbar.setNavigationIcon(null)
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }
}
