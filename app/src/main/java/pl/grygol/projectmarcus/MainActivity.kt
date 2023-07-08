package pl.grygol.projectmarcus

import CameraFragment
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import pl.grygol.projectmarcus.adapters.ExpandableListAdapter
import pl.grygol.projectmarcus.data.DataSource
import pl.grygol.projectmarcus.data.database.Database
import pl.grygol.projectmarcus.data.database.model.ExpenseEntity
import pl.grygol.projectmarcus.data.database.model.ProjectWithExpenses
import pl.grygol.projectmarcus.databinding.ActivityMainBinding
import pl.grygol.projectmarcus.fragments.DashboardFragment
import pl.grygol.projectmarcus.fragments.ExpenseDetailsFragment
import pl.grygol.projectmarcus.fragments.NewExpenseFormFragment
import pl.grygol.projectmarcus.fragments.ProjectDetailsFragment
import pl.grygol.projectmarcus.interfaces.Navigable
import pl.grygol.projectmarcus.interfaces.Navigable.Destination.Camera
import pl.grygol.projectmarcus.interfaces.Navigable.Destination.Dashboard
import pl.grygol.projectmarcus.interfaces.Navigable.Destination.ExpenseDetails
import pl.grygol.projectmarcus.interfaces.Navigable.Destination.MainDashboard
import pl.grygol.projectmarcus.interfaces.Navigable.Destination.NewExpense
import pl.grygol.projectmarcus.interfaces.Navigable.Destination.ProjectDetails
import kotlin.collections.set
import kotlin.concurrent.thread

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), Navigable {

    private lateinit var projectDetailsFragment: ProjectDetailsFragment
    private lateinit var dashboardFragment: DashboardFragment
    private lateinit var newExpenseFormFragment: NewExpenseFormFragment
    private lateinit var expenseDetailsFragment: ExpenseDetailsFragment
    private lateinit var cameraFragment: CameraFragment
    private lateinit var binding: ActivityMainBinding
    private lateinit var expandableListAdapter: ExpandableListAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        database = Database.open(this)

        setupViews()
        setupToolbar()
        setupNavigationDrawer()

        navigate(Navigable.Destination.MainDashboard)

    }

    private fun setupViews() {
        expandableListAdapter = ExpandableListAdapter(this, prepareExpandableListData())

        dashboardFragment = DashboardFragment()
        newExpenseFormFragment = NewExpenseFormFragment()
        expenseDetailsFragment = ExpenseDetailsFragment()
        cameraFragment = CameraFragment()
        projectDetailsFragment = ProjectDetailsFragment()
    }

    private fun setupToolbar() {
        toolbar = binding.topAppBar
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

        onCreateOptionsMenu(binding.drawerNavigationView.menu)

        binding.drawerNavigationView.menu.apply {
            var projects: List<ProjectWithExpenses>
            var expenses: List<ExpenseEntity>
            thread {
                projects = retrieveProjects()
                expenses = retrieveExpenses()

                runOnUiThread {
                    findItem(R.id.first_section_header)?.apply {
                        setOnMenuItemClickListener {
                            navigate(MainDashboard)
                            binding.drawerLayout.close()
                            true
                        }
                    }

                    //Last Projects menu section
                    addSubMenu(R.string.last_projects_label).apply {
                        Log.d(TAG, "onCreateOptionsMenu: $projects")
                        projects.forEach { project ->
                            this?.add(project.project.name).apply {
                                this?.setOnMenuItemClickListener {
                                    DataSource.currentProjectWithExpenses =
                                        project
                                    navigate(ProjectDetails)
                                    binding.drawerLayout.close()
                                    true
                                }
                            }
                        }
                    }

                    //Expenses menu section
                    addSubMenu(R.string.last_expenses_label).apply {
                        expenses.forEach { expense ->
                            this?.add(expense.title).apply {
                                this?.setOnMenuItemClickListener {
                                    DataSource.currentExpense = expense
                                    navigate(ExpenseDetails)
                                    binding.drawerLayout.close()
                                    true
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun retrieveProjects(): List<ProjectWithExpenses> = database.projects.getLatest(5)

    private fun retrieveExpenses(): List<ExpenseEntity> = database.expenses.getLatest(5)

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
            when (to) {
                MainDashboard -> {
                    replace(R.id.container, dashboardFragment, dashboardFragment.javaClass.name)

                }

                Dashboard -> {
                    replace(R.id.container, dashboardFragment, dashboardFragment.javaClass.name)
                    addToBackStack(projectDetailsFragment.javaClass.name)
                }

                ProjectDetails -> {
                    projectDetailsFragment.reloadData()
                    replace(
                        R.id.container,
                        projectDetailsFragment,
                        projectDetailsFragment.javaClass.name
                    )
                    addToBackStack(projectDetailsFragment.javaClass.name)
                }

                NewExpense -> {
                    replace(
                        R.id.container,
                        newExpenseFormFragment,
                        newExpenseFormFragment.javaClass.name
                    )
                    addToBackStack(newExpenseFormFragment.javaClass.name)
                }

                ExpenseDetails -> {
                    expenseDetailsFragment.reloadData()
                    Log.d(TAG, "navigate: expense currentFragment is null")
                    replace(
                        R.id.container,
                        expenseDetailsFragment,
                        expenseDetailsFragment.javaClass.name
                    )
                    addToBackStack(expenseDetailsFragment.javaClass.name)

                }

                Camera -> {
                    replace(
                        R.id.container,
                        cameraFragment,
                        cameraFragment.javaClass.name
                    )
                    addToBackStack(cameraFragment.javaClass.name)
                }
            }.commit()
        }
    }

    fun showPopup(v: View) {
        val popup = PopupMenu(this, v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.actions, popup.menu)
        popup.show()
    }
}
