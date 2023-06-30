package pl.grygol.projectmarcus.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import pl.grygol.projectmarcus.R

class ExpandableListAdapter(private val context: Context, private val expandableListData: LinkedHashMap<String, List<String>>) :
    BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return expandableListData.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        val groupTitle = expandableListData.keys.toList()[groupPosition]
        return expandableListData[groupTitle]?.size ?: 0
    }

    override fun getGroup(groupPosition: Int): Any {
        return expandableListData.keys.toList()[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        val groupTitle = expandableListData.keys.toList()[groupPosition]
        return expandableListData[groupTitle]?.get(childPosition) ?: ""
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        val groupTitle = getGroup(groupPosition) as String

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = convertView ?: inflater.inflate(R.layout.nav_list_group, null)

        val titleTextView = view.findViewById<TextView>(R.id.listTitle)
        titleTextView.text = groupTitle

        return view
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        val groupTitle = getGroup(groupPosition) as String
        val childTitle = getChild(groupPosition, childPosition) as String

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = convertView ?: inflater.inflate(R.layout.nav_list_item, null)

        val titleTextView = view.findViewById<TextView>(R.id.expandableListItem)
        titleTextView.text = childTitle

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}
