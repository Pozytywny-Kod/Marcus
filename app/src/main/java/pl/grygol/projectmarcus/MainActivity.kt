package pl.grygol.projectmarcus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import pl.grygol.projectmarcus.Adapters.ExpandableListAdapter
import pl.grygol.projectmarcus.Fragments.DashboardFragment
import pl.grygol.projectmarcus.Fragments.ProjectDetailsFragment
import pl.grygol.projectmarcus.Interfaces.Navigable
import pl.grygol.projectmarcus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),Navigable {

    private lateinit var projectDetailsFragment: ProjectDetailsFragment
    private lateinit var dashboardFragment: DashboardFragment
    private lateinit var binding: ActivityMainBinding
    private lateinit var expandableListAdapter: ExpandableListAdapter
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        setupViews()
        setupToolbar()
        setupNavigationDrawer()

    }

    private fun setupViews() {
        expandableListAdapter = ExpandableListAdapter(this, prepareExpandableListData())

        val headerView = LayoutInflater.from(this).inflate(R.layout.nav_header, binding.drawerLayout, false)
        binding.expandableListView.addHeaderView(headerView)
        binding.expandableListView.setAdapter(expandableListAdapter)

        dashboardFragment = DashboardFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.container, dashboardFragment, dashboardFragment.javaClass.name)
            .commit()
    }

    private fun setupToolbar() {
        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
    }

    private fun setupNavigationDrawer() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun prepareExpandableListData(): LinkedHashMap<String, List<String>> {
        val listData = LinkedHashMap<String, List<String>>()

        val group1 = "Zespoły"
        val group2 = "Projekty"
        val group3 = "Ostatnie skany"

        val group1Items = listOf("Zespół 1", "Zespół 2", "Zespół 3")
        val group2Items = listOf("Projekt 1", "Projekt 2", "Projekt 3")
        val group3Items = listOf("Skan 7", "Skan 8")

        listData[group1] = group1Items
        listData[group2] = group2Items
        listData[group3] = group3Items

        return listData
    }

    override fun navigate(to: Navigable.Destination) {
        supportFragmentManager.beginTransaction().apply {
        when(to){
            Navigable.Destination.Dashboard -> {
                replace(R.id.container, dashboardFragment, dashboardFragment.javaClass.name)
                addToBackStack(projectDetailsFragment.javaClass.name)
            }
            Navigable.Destination.ProjectDetails -> {
                replace(R.id.container, projectDetailsFragment, projectDetailsFragment.javaClass.name)
                addToBackStack(projectDetailsFragment.javaClass.name)
            }

        }.commit()
        }
    }
}
