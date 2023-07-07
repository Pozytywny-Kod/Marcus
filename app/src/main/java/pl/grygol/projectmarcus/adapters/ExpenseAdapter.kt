package pl.grygol.projectmarcus.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.grygol.projectmarcus.data.DataSource
import pl.grygol.projectmarcus.data.database.model.ExpenseEntity
import pl.grygol.projectmarcus.data.model.Expense
import pl.grygol.projectmarcus.data.model.Position
import pl.grygol.projectmarcus.databinding.ProjectDetailsItemBinding
import pl.grygol.projectmarcus.interfaces.Navigable
import java.util.ArrayList
import java.util.Date

class ExpenseViewHolder(private val context: Context, private val binding: ProjectDetailsItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(expense: ExpenseEntity) {
        binding.expenseDate.text = expense.expenseDate.toString()
        binding.expenseName.text = expense.title
        binding.ExpensePriceValue.text = format(getSumOfExpenses(expense), expense.currency)
        binding.DetailsButton.setOnClickListener {
            DataSource.currentExpense = expense
            (context as? Navigable)?.navigate(Navigable.Destination.ExpenseDetails)
        }
    }

    private fun getSumOfExpenses(expense: ExpenseEntity): Float {
        var sum = 0f
        for (position in expense.positions) {
            sum += position.price

        }
        return sum
    }
    private fun format(value: Float, currency: String): String {
        return "$value $currency"
    }
}

class ExpenseAdapter : RecyclerView.Adapter<ExpenseViewHolder>(){
    private val data = mutableListOf<ExpenseEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ProjectDetailsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExpenseViewHolder(parent.context, binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = data[position]
        holder.bind(expense)
    }
    fun replace(newData: List<ExpenseEntity>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

}