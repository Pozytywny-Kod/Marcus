import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import pl.grygol.projectmarcus.data.Position
import pl.grygol.projectmarcus.databinding.PositionsItemBinding
import pl.grygol.projectmarcus.databinding.PositionsItemLastBinding

class PositionViewHolder(private val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root){

    fun bind(position: Position) {
        // Bind your data to the views in the layout
    }

}

class PositionAdapter : RecyclerView.Adapter<PositionViewHolder>() {
    interface OnItemClickListener {
        fun onLastPositionClick()
    }
    private val data = mutableListOf<Position>()
    private var itemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = when (viewType) {
            VIEW_TYPE_LAST_ITEM -> PositionsItemLastBinding.inflate(inflater, parent, false)
            else -> PositionsItemBinding.inflate(inflater, parent, false)
        }
        return PositionViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) VIEW_TYPE_LAST_ITEM else VIEW_TYPE_NORMAL
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        val positionData = data[position]
        val isLastPosition = position == itemCount - 1
        if (!isLastPosition) {
            holder.bind(positionData)
        }else {
            holder.itemView.setOnClickListener {
                itemClickListener?.onLastPositionClick()
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun replace(newData: List<Position>) {
        data.clear()
        data.addAll(newData)
        data.add(Position())
        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_NORMAL = 0
        private const val VIEW_TYPE_LAST_ITEM = 1
    }
}
