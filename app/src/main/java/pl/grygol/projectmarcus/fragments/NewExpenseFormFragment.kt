package pl.grygol.projectmarcus.fragments

import PositionAdapter
import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pl.grygol.projectmarcus.R
import pl.grygol.projectmarcus.data.DataSource
import pl.grygol.projectmarcus.data.model.Position
import pl.grygol.projectmarcus.data.ResourceUriHelper
import pl.grygol.projectmarcus.data.database.Database
import pl.grygol.projectmarcus.data.database.converter.DateConverter
import pl.grygol.projectmarcus.data.database.dao.ExpenseDao
import pl.grygol.projectmarcus.data.database.model.ExpenseEntity
import pl.grygol.projectmarcus.databinding.FragmentCreateNewExpenseBinding
import pl.grygol.projectmarcus.interfaces.Navigable
import java.sql.Date
import kotlin.concurrent.thread


class NewExpenseFormFragment : Fragment() {

    private lateinit var binding: FragmentCreateNewExpenseBinding
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var positionAdapter: PositionAdapter
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Database.open(requireContext())
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
            openCalendarPicker(context)
        }

        // currencies control
        val currencies = resources.getStringArray(R.array.currencies)
        arrayAdapter = ArrayAdapter(requireContext(),R.layout.currencies_dropdown_item,currencies)
        binding.textInputCurrencyEditText.setAdapter(arrayAdapter)

        binding.addImage.setOnClickListener {
            //change to go to taking picture view
            DataSource.pictures.add(ResourceUriHelper.getUriFromDrawableId(context,R.drawable.baseline_add_a_photo_24))
        }

        //positions control
        positionAdapter = PositionAdapter().apply {
            setOnItemClickListener(object : PositionAdapter.OnItemClickListener{
                override fun onLastPositionClick() {
                    DataSource.positions.add(Position("something",0f))
                    replace(DataSource.positions)
                }

            })
        }
        binding.positions.let {
            it.adapter = positionAdapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
        binding.confirmButton.setOnClickListener{
            DataSource.currentExpense = createNewExpense(binding)
            (activity as? Navigable)?.navigate(Navigable.Destination.ExpenseDetails)
        }
    }

    private fun createNewExpense(binding: FragmentCreateNewExpenseBinding): ExpenseEntity? {
        val dataList = ArrayList<Position>()
        for (i in 0 until positionAdapter.itemCount - 1) {
            val item = positionAdapter.getItem(i)
            dataList.add(item)
        }
        val newExpense = DataSource.currentProjectWithExpenses?.project?.id?.let {
            DataSource.date?.let { it1 ->
                ExpenseEntity(
                    title = binding.textInputTitleEditText.text.toString(),
                    expenseDate = it1,
                    nip = binding.textInputNIPEditText.text.toString(),
                    location = binding.textInputShoppingPlaceEditText.text.toString(),
                    description = binding.textInputDescriptionEditText.text.toString(),
                    currency = binding.textInputCurrencyEditText.text.toString(),
                    positions = dataList,
                    photo = DataSource.photo,
                    projectId = it.toLong()
                )
            }
        }
        if (newExpense != null) {
            thread {
                database.expenses.insertExpense(newExpense)
            }
        }
        return newExpense
    }

    private fun openCalendarPicker(context: Context) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                val calendar = Calendar.getInstance()
                calendar.set(selectedYear, selectedMonth, selectedDay)
                val selectedDate = calendar.time
                DataSource.date = selectedDate
                    binding.textInputDateEditText.setText(formattedDate)
            }, year, month, day)

        datePickerDialog.show()
    }

    override fun onStart() {
        super.onStart()
        positionAdapter.replace(DataSource.positions)
    }
    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }

}