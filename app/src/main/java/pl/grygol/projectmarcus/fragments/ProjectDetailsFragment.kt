package pl.grygol.projectmarcus.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pl.grygol.projectmarcus.adapters.ExpenseAdapter
import pl.grygol.projectmarcus.data.DataSource
import pl.grygol.projectmarcus.databinding.FragmentExpenseListBinding
import pl.grygol.projectmarcus.interfaces.Navigable


class ProjectDetailsFragment : Fragment() {

    private lateinit var binding: FragmentExpenseListBinding
    private lateinit var adapter: ExpenseAdapter
    private var viewCreated: Boolean = false

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
        DataSource.currentProjectWithExpenses?.let { adapter.replace(it.expenses) }
        viewCreated = true
    }

    private fun setupViews() {
        adapter = ExpenseAdapter()
        binding.expenseList.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
        binding.btnAdd.setOnClickListener{
            (context as? Navigable)?.navigate(Navigable.Destination.Camera)
        }
    }

    fun reloadData(){
        if(viewCreated){
            onStart()
        }
    }
    override fun onStart() {
        super.onStart()
        println(DataSource.currentProjectWithExpenses)
        DataSource.currentProjectWithExpenses?.let { adapter.replace(it.expenses) }
    }

}