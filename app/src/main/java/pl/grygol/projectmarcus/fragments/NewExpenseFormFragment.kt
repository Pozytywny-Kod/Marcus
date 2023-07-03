package pl.grygol.projectmarcus.fragments

import android.app.DatePickerDialog
import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pl.grygol.projectmarcus.R
import pl.grygol.projectmarcus.adapters.ExpenseAdapter
import pl.grygol.projectmarcus.adapters.ExpenseImageAdapter
import pl.grygol.projectmarcus.data.DataSource
import pl.grygol.projectmarcus.data.ResourceUriHelper
import pl.grygol.projectmarcus.databinding.FragmentCreateNewExpenseBinding


class NewExpenseFormFragment : Fragment() {

    private lateinit var binding: FragmentCreateNewExpenseBinding
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var adapter: ExpenseImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCreateNewExpenseBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        val context = requireContext()
        binding.textInputDateLayout.setEndIconOnClickListener {
            openCalendarPicker(context)
        }
        val currencies = resources.getStringArray(R.array.currencies)
        arrayAdapter = ArrayAdapter(requireContext(),R.layout.currencies_dropdown_item,currencies)
        adapter = ExpenseImageAdapter()
        binding.textInputCurrencyEditText.setAdapter(arrayAdapter)
        binding.images.apply {
            adapter = this@NewExpenseFormFragment.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.addImage.setOnClickListener {
            //change to go to taking picture view
            DataSource.pictures.add(ResourceUriHelper.getUriFromDrawableId(context,R.drawable.baseline_add_a_photo_24))
            adapter.replace(DataSource.pictures)
            println(DataSource.pictures)
        }
    }

    private fun openCalendarPicker(context: Context) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                binding.textInputDateEditText.setText(formattedDate)
            }, year, month, day)

        datePickerDialog.show()
    }

    override fun onStart() {
        super.onStart()
        adapter.replace(DataSource.pictures)
    }

}