package pl.grygol.projectmarcus.fragments

import PositionAdapter
import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pl.grygol.projectmarcus.R
import pl.grygol.projectmarcus.adapters.ExpenseImageAdapter
import pl.grygol.projectmarcus.data.DataSource
import pl.grygol.projectmarcus.data.Position
import pl.grygol.projectmarcus.data.ResourceUriHelper
import pl.grygol.projectmarcus.databinding.FragmentCreateNewExpenseBinding


class NewExpenseFormFragment : Fragment() {

    private lateinit var binding: FragmentCreateNewExpenseBinding
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var expenseImageAdapter: ExpenseImageAdapter
    private lateinit var positionAdapter: PositionAdapter

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

        //date picker control

        binding.textInputDateLayout.setEndIconOnClickListener {
            val context = requireContext()
            openCalendarPicker(context)
        }
        // currencies control
        val currencies = resources.getStringArray(R.array.currencies)
        arrayAdapter = ArrayAdapter(requireContext(),R.layout.currencies_dropdown_item,currencies)
        binding.textInputCurrencyEditText.setAdapter(arrayAdapter)

        //image controls
        expenseImageAdapter = ExpenseImageAdapter()
        binding.images.apply {
            adapter = this@NewExpenseFormFragment.expenseImageAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.addImage.setOnClickListener {
            //change to go to taking picture view
            DataSource.pictures.add(ResourceUriHelper.getUriFromDrawableId(context,R.drawable.baseline_add_a_photo_24))
            expenseImageAdapter.replace(DataSource.pictures)
        }

        //positions control
        positionAdapter = PositionAdapter().apply {
            setOnItemClickListener(object : PositionAdapter.OnItemClickListener{
                override fun onLastPositionClick() {
                    DataSource.positions.add(Position())
                    replace(DataSource.positions)
                }

            })
        }
        binding.positions.let {
            it.adapter = positionAdapter
            it.layoutManager = LinearLayoutManager(requireContext())
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
        expenseImageAdapter.replace(DataSource.pictures)
        positionAdapter.replace(DataSource.positions)
    }
}