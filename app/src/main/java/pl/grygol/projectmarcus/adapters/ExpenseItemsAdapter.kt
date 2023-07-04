package pl.grygol.projectmarcus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.grygol.projectmarcus.data.Position
import pl.grygol.projectmarcus.data.Project
import pl.grygol.projectmarcus.databinding.ExpenseDetailPositionBinding


class ExpenseItemViewHolder(private val binding: ExpenseDetailPositionBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(project: Position) {
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
        val project = data[position]
        holder.bind(project)
    }
    fun replace(newData: List<Position>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}