package pl.grygol.projectmarcus.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import org.json.JSONObject
import pl.grygol.projectmarcus.data.DataSource
import pl.grygol.projectmarcus.data.database.model.ExpenseEntity
import pl.grygol.projectmarcus.data.database.model.ProjectWithExpenses
import pl.grygol.projectmarcus.databinding.DashboardItemBinding
import pl.grygol.projectmarcus.interfaces.Navigable
import java.io.IOException
import java.util.Calendar
import java.util.Date
import kotlin.math.pow

class ProjectViewHolder(
    private val context: Context,
    private val binding: DashboardItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun bind(projectWithExpenses: ProjectWithExpenses) {
        val project = projectWithExpenses.project
        val expenses = projectWithExpenses.expenses

        val restOfBudget = calculateRestOfBudget(project.budget, expenses, project.currency)
        val restOfBudgetText = format(restOfBudget, project.currency)

        binding.projectName.text = project.name
        binding.companyName.text = project.companyName
        binding.restOfBudget.text = restOfBudgetText
        binding.yourExpensesPriceValue.text = format(getSumOfYourExpenses(expenses, null, project.currency), project.currency)
        binding.lastExpensesPriceValue.text = format(getSumOfYourExpenses(expenses, getDateMinus30Days(), project.currency), project.currency)

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

    private fun getSumOfYourExpenses(
        expenses: List<ExpenseEntity>,
        date: Date?,
        targetCurrency: String
    ): Float {
        var sum = 0f
        for (expense in expenses) {
            if (date == null || expense.expenseDate.after(date)) {
                for (position in expense.positions) {
                    val convertedPrice = convertCurrency(position.price, expense.currency, targetCurrency)
                    val roundedPrice = roundUp(convertedPrice)
                    sum += roundedPrice
                }
            }
        }
        return sum
    }

    private fun roundUp(value: Float): Float {
        val decimalPlaces = 2 // Number of decimal places to round up to
        val multiplier = 10.0.pow(decimalPlaces.toDouble())
        return (kotlin.math.ceil(value * multiplier) / multiplier).toFloat()
    }

    private fun getExchangeRate(sourceCurrency: String, targetCurrency: String): Float =
            try {
                DataSource.currencies?.let {
                    val jsonData = JSONObject(it)
                    val ratesObject = jsonData.getJSONObject("data")

                    val sourceRate = ratesObject.getDouble(sourceCurrency).toFloat()
                    val targetRate = ratesObject.getDouble(targetCurrency).toFloat()
                    targetRate / sourceRate
                } ?: 1f
            } catch (e: IOException) {
                e.printStackTrace()
                1f
            }


    private fun convertCurrency(amount: Float, sourceCurrency: String, targetCurrency: String): Float {
        val exchangeRate = getExchangeRate(sourceCurrency, targetCurrency)
        return amount * exchangeRate
    }

    private fun calculateRestOfBudget(budget: Float, expenses: List<ExpenseEntity>, currency: String): Float {
        return budget - getSumOfYourExpenses(expenses, null, currency)
    }

    fun onDestroy() {
        coroutineScope.cancel()
    }
}

class ProjectAdapter : RecyclerView.Adapter<ProjectViewHolder>() {
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
