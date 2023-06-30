package pl.grygol.projectmarcus.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.grygol.projectmarcus.data.Expense
import pl.grygol.projectmarcus.databinding.ProjectDetailsItemBinding

class ExpenseViewHolder(private val binding: ProjectDetailsItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(expense: Expense) {
    }
}

class ExpenseAdapter : RecyclerView.Adapter<ExpenseViewHolder>(){
    private val data = mutableListOf<Expense>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ProjectDetailsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExpenseViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = data[position]
        holder.bind(expense)
    }
    fun replace(newData: List<Expense>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

}