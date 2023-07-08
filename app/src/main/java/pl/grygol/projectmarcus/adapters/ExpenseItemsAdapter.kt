package pl.grygol.projectmarcus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.grygol.projectmarcus.data.DataSource
import pl.grygol.projectmarcus.data.database.model.ExpenseEntity
import pl.grygol.projectmarcus.data.model.Position
import pl.grygol.projectmarcus.databinding.ExpenseDetailPositionBinding


class ExpenseItemViewHolder(private val binding: ExpenseDetailPositionBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(position: Position) {
        binding.positionName.text = position.name
        binding.positionPrice.text = DataSource.currentExpense?.let {
            format(position.price,
                it.currency)
        }
    }
    private fun format(value: Float, currency: String): String {
        return "$value $currency"
    }
}

class ExpenseItemsAdapter : RecyclerView.Adapter<ExpenseItemViewHolder>(){
    private val data = mutableListOf<Position>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseItemViewHolder {
        val binding = ExpenseDetailPositionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExpenseItemViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ExpenseItemViewHolder, position: Int) {
        val expense = data[position]
        holder.bind(expense)
    }
    fun replace(newData: ArrayList<Position>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}