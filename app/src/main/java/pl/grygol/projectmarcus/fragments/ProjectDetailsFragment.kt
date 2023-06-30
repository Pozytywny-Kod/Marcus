package pl.grygol.projectmarcus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import pl.grygol.projectmarcus.adapter.ExpenseAdapter
import pl.grygol.projectmarcus.data.DataSource
import pl.grygol.projectmarcus.databinding.FragmentExpenseListBinding


class ProjectDetailsFragment : Fragment() {

    private lateinit var binding: FragmentExpenseListBinding
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentExpenseListBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        adapter = ExpenseAdapter()
        binding.expenseList.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }
    override fun onStart() {
        super.onStart()
        adapter.replace(DataSource.expenses)
    }

}