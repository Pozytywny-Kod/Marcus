package pl.grygol.projectmarcus.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pl.grygol.projectmarcus.adapters.ExpenseImageAdapter
import pl.grygol.projectmarcus.adapters.ExpenseItemsAdapter
import pl.grygol.projectmarcus.data.DataSource
import pl.grygol.projectmarcus.databinding.FragmentExpenseDetailsBinding

class ExpenseDetailsFragment : Fragment() {

    private lateinit var binding: FragmentExpenseDetailsBinding
    private lateinit var expenseImageAdapter: ExpenseImageAdapter
    private lateinit var expenseItemsAdapter: ExpenseItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentExpenseDetailsBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        val context = requireContext()

        //image controls
        expenseImageAdapter = ExpenseImageAdapter()
        binding.images.apply {
            adapter = this@ExpenseDetailsFragment.expenseImageAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        //expense positions
        expenseItemsAdapter = ExpenseItemsAdapter()
        binding.positions.apply {
            adapter = this@ExpenseDetailsFragment.expenseItemsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }


    }

    override fun onStart() {
        super.onStart()
        expenseImageAdapter.replace(DataSource.pictures)
        expenseItemsAdapter.replace(DataSource.positions)
    }
}