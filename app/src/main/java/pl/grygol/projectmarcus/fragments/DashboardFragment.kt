package pl.grygol.projectmarcus.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pl.grygol.projectmarcus.adapters.ProjectAdapter
import pl.grygol.projectmarcus.data.database.Database
import pl.grygol.projectmarcus.databinding.FragmentProjectListBinding
import pl.grygol.projectmarcus.interfaces.Navigable
import kotlin.concurrent.thread

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentProjectListBinding
    private lateinit var adapter: ProjectAdapter
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Database.open(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentProjectListBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProjectAdapter()
        setupViews()
        loadData()
    }

    private fun loadData() {
        thread {
            val projects = database.projects.getAll()
            requireActivity().runOnUiThread {
                adapter.replace(projects)
            }
        }
    }

    private fun setupViews() {
        binding.projectList.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }
    override fun onStart() {
        super.onStart()
        loadData()
    }
    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }
}