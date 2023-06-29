package pl.grygol.projectmarcus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import pl.grygol.projectmarcus.Adapters.ExpandableListAdapter
import pl.grygol.projectmarcus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var expandableListAdapter: ExpandableListAdapter
    private lateinit var header: View
    private lateinit var footer: View
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        expandableListAdapter = ExpandableListAdapter(this, prepareExpandableListData())

        header = LayoutInflater.from(this).inflate(R.layout.nav_header, binding.drawerLayout, false)

        binding.expandableListView.addHeaderView(header)
        binding.expandableListView.setAdapter(expandableListAdapter)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

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
        val id = item.itemId
        return super.onOptionsItemSelected(item)
    }
    private fun prepareExpandableListData(): LinkedHashMap<String, List<String>> {
        val listData = LinkedHashMap<String, List<String>>()

        val group1 = "Group 1"
        val group2 = "Group 2"
        val group3 = "Group 3"

        val group1Items = listOf("Item 1", "Item 2", "Item 3")
        val group2Items = listOf("Item 4", "Item 5", "Item 6")
        val group3Items = listOf("Item 7", "Item 8", "Item 9")

        listData[group1] = group1Items
        listData[group2] = group2Items
        listData[group3] = group3Items

        return listData
    }
}