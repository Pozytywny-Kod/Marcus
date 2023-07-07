package pl.grygol.projectmarcus.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pl.grygol.projectmarcus.adapters.ExpenseItemsAdapter
import pl.grygol.projectmarcus.data.DataSource
import pl.grygol.projectmarcus.data.database.model.ExpenseEntity
import pl.grygol.projectmarcus.data.model.Position
import pl.grygol.projectmarcus.databinding.FragmentExpenseDetailsBinding

class ExpenseDetailsFragment : Fragment() {

    private lateinit var binding: FragmentExpenseDetailsBinding
    private lateinit var expenseItemsAdapter: ExpenseItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expenseItemsAdapter = ExpenseItemsAdapter()
        DataSource.currentExpense?.let { expenseItemsAdapter.replace(it.positions) }
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
        binding.amountValue.text = getSum(DataSource.currentExpense)
        expenseItemsAdapter = ExpenseItemsAdapter()
        DataSource.currentExpense?.let { expenseItemsAdapter.replace(it.positions) }
        binding.place.text = DataSource.currentExpense?.location ?: ""
        binding.dateInfo.text = DataSource.currentExpense?.expenseDate.toString()
        binding.nipInfo.text = DataSource.currentExpense?.nip ?: ""
    }

    private fun getSum(currentExpense: ExpenseEntity?): CharSequence {
        var sum = 0f
        if (currentExpense != null) {
            for(position: Position in currentExpense.positions){
                    sum+=position.price
            }
        }
        return sum.toString()
    }

    private fun setupViews() {
        val context = requireContext()

        //image controls
        binding.images.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        //expense positions
        binding.positions.apply {
            adapter = this@ExpenseDetailsFragment.expenseItemsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }


    }

    override fun onStart() {
        super.onStart()
        DataSource.currentExpense?.let { expenseItemsAdapter.replace(it.positions) }
    }
}