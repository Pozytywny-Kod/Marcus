package pl.grygol.projectmarcus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.grygol.projectmarcus.data.Project
import pl.grygol.projectmarcus.databinding.DashboardItemBinding

class ProjectViewHolder(private val binding: DashboardItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(project: Project) {
    }
}

class ProjectAdapter : RecyclerView.Adapter<ProjectViewHolder>() {
    private val data = mutableListOf<Project>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding = DashboardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProjectViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val project = data[position]
        holder.bind(project)

    }

    fun replace(newData: List<Project>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}
