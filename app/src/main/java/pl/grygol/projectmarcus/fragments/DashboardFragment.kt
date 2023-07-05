package pl.grygol.projectmarcus.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pl.grygol.projectmarcus.adapters.ProjectAdapter
import pl.grygol.projectmarcus.data.DataSource
import pl.grygol.projectmarcus.databinding.FragmentProjectListBinding
import pl.grygol.projectmarcus.interfaces.Navigable

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentProjectListBinding
    private lateinit var adapter: ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

    private fun setupViews() {
        binding.projectList.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
        binding.btnAdd.setOnClickListener {
            //change later to firstly go to photo screen
            (activity as? Navigable)?.navigate(Navigable.Destination.Camera)
        }
    }
    override fun onStart() {
        super.onStart()
        adapter.replace(DataSource.projects)
    }
}