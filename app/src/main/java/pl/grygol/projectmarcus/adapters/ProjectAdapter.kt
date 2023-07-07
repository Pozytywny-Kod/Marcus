package pl.grygol.projectmarcus.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.grygol.projectmarcus.data.DataSource
import pl.grygol.projectmarcus.data.database.model.ExpenseEntity
import pl.grygol.projectmarcus.data.database.model.ProjectEntity
import pl.grygol.projectmarcus.data.database.model.ProjectWithExpenses
import pl.grygol.projectmarcus.databinding.DashboardItemBinding
import pl.grygol.projectmarcus.interfaces.Navigable
import java.util.Calendar
import java.util.Date

class ProjectViewHolder(private val context: Context, private val binding: DashboardItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(projectWithExpenses: ProjectWithExpenses) {
        val project = projectWithExpenses.project
        val expenses = projectWithExpenses.expenses

        val restOfBudget = calculateRestOfBudget(project.budget, expenses)
        val restOfBudgetText = format(restOfBudget, project.currency)

        binding.projectName.text = project.name
        binding.companyName.text = project.companyName
        binding.restOfBudget.text = restOfBudgetText
        binding.yourExpensesPriceValue.text = format(getSumOfYourExpenses(expenses, null), project.currency)
        binding.lastExpensesPriceValue.text = format(getSumOfYourExpenses(expenses, getDateMinus30Days()), project.currency)

        binding.addNewExpenseButton.setOnClickListener {
            DataSource.currentProjectWithExpenses = projectWithExpenses
            (context as? Navigable)?.navigate(Navigable.Destination.Camera)
        }
        binding.detailsButton.setOnClickListener {
            DataSource.currentProjectWithExpenses = projectWithExpenses
            (context as? Navigable)?.navigate(Navigable.Destination.ProjectDetails)
        }
    }

    private fun getDateMinus30Days(): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -30)
        return calendar.time
    }
    private fun format(value: Float, currency: String): String {
        return "$value $currency"
    }

    private fun getSumOfYourExpenses(expenses: List<ExpenseEntity>, date: Date?): Float {
        var sum = 0f
        for (expense in expenses) {
            if (date == null || expense.expenseDate.after(date)) {
                for (position in expense.positions) {
                    sum += position.price
                }
            }
        }
        return sum
    }

    private fun calculateRestOfBudget(budget: Float, expenses: List<ExpenseEntity>): Float {
        return budget - getSumOfYourExpenses(expenses,null)
    }

}

class ProjectAdapter : RecyclerView.Adapter<ProjectViewHolder>(){
        private val data = mutableListOf<ProjectWithExpenses>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
            val binding = DashboardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ProjectViewHolder(parent.context, binding)
        }

        override fun getItemCount(): Int = data.size

        override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
            val project = data[position]
            holder.bind(project)
        }
        fun replace(newData: List<ProjectWithExpenses>) {
            data.clear()
            data.addAll(newData)
            notifyDataSetChanged()
        }
}