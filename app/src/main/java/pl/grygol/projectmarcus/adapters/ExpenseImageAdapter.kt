package pl.grygol.projectmarcus.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.grygol.projectmarcus.R
import pl.grygol.projectmarcus.data.Project
import pl.grygol.projectmarcus.databinding.ExpenseImageBinding

class ExpenseImageViewHolder(private val binding: ExpenseImageBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(imageUri: Uri) {
        binding.image.setImageURI(imageUri)
    }
}

class ExpenseImageAdapter : RecyclerView.Adapter<ExpenseImageViewHolder>() {
    private val data = mutableListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseImageViewHolder {
        val binding = ExpenseImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExpenseImageViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ExpenseImageViewHolder, position: Int) {
        val image = data[position]
        holder.bind(image)
    }
    fun replace(newData: List<Uri>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}
