package pl.grygol.projectmarcus.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import pl.grygol.projectmarcus.adapters.ProjectAdapter
import pl.grygol.projectmarcus.data.DataSource
import pl.grygol.projectmarcus.data.database.Database
import pl.grygol.projectmarcus.databinding.FragmentProjectListBinding
import java.util.Properties
import kotlin.concurrent.thread

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentProjectListBinding
    private lateinit var adapter: ProjectAdapter
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Database.open(requireContext())
        lifecycleScope.launch {
            getExchangeRates()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentProjectListBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProjectAdapter()
        setupViews()
        loadData()
    }

    private fun loadData() {
        thread {
            val projects = database.projects.getAll()
            requireActivity().runOnUiThread {
                while (DataSource.currencies == null){

                }
                adapter.replace(projects)
            }
        }
    }

    private fun setupViews() {
        binding.projectList.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }
    override fun onStart() {
        super.onStart()
        loadData()
    }
    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }
    private suspend fun getExchangeRates(){
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val apiKey = readApiKeyFromConfig()
            val request = Request.Builder()
                .url("https://api.freecurrencyapi.com/v1/latest?apikey=$apiKey")
                .build()
            val response: Response = client.newCall(request).execute()
            val responseData = response.body?.string()
            DataSource.currencies = responseData
        }
    }
    private fun readApiKeyFromConfig(): String {
        val assetManager = context?.assets
        val inputStream = assetManager?.open("api_config.properties")
        val properties = Properties()
        properties.load(inputStream)
        return properties.getProperty("api.key", "")
    }
}