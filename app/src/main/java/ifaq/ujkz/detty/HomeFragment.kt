package ifaq.ujkz.detty

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ifaq.ujkz.detty.adapter.ClientDetteAdapter
import ifaq.ujkz.detty.data.SuperbaseClientProvider
import ifaq.ujkz.detty.databinding.FragmentHomeBinding
import ifaq.ujkz.detty.datasource.ClientDataSource
import ifaq.ujkz.detty.entity.Client
import ifaq.ujkz.detty.repository.ClientRepository
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var clientDetteAdapter: ClientDetteAdapter

    private val repository by lazy {
        ClientRepository(ClientDataSource(SuperbaseClientProvider.client))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        setupRecyclerView()

        binding.bntAddclient.setOnClickListener {
            startActivity(Intent(requireContext(), AddClientForm::class.java))
        }

        fetchClients()
    }

    private fun setupRecyclerView() {
        clientDetteAdapter = ClientDetteAdapter(mutableListOf()) { client ->
            ouvreDetailsClient(client)
        }
        binding.listClientDette.apply {
            adapter = clientDetteAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun fetchClients() {
        lifecycleScope.launch {
            try {
                val user = SuperbaseClientProvider.client.auth.currentUserOrNull()
                val currentUserId = user?.id

                if (currentUserId != null) {
                    Log.d("DETTY_DEBUG", "Tentative de récupération pour l'ID : $currentUserId")

                    // 1. Essai avec la méthode filtrée SQL
                    var clients = repository.getClientsEndettes(currentUserId)
                    Log.d("DETTY_DEBUG", "Résultat SQL (getClientsEndettes) : ${clients.size} clients")

                    if (clients.isEmpty()) {
                        Log.d("DETTY_DEBUG", "SQL vide, tentative de récupération brute (getAllClient)...")
                        val allClients = repository.getClients(currentUserId)
                        Log.d("DETTY_DEBUG", "Brut reçu : ${allClients.size} clients au total")

                        clients = allClients.filter { it.soldeTotal > 0 }
                        Log.d("DETTY_DEBUG", "Après filtre manuel Kotlin : ${clients.size} clients")
                    }

                    clientDetteAdapter.updateData(clients)
                    updateUI(clients)

                } else {
                    Log.e("DETTY_DEBUG", "Erreur : User ID est NULL")
                    Toast.makeText(requireContext(), "Utilisateur non identifié", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("DETTY_ERROR", "Crash fetchClients: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(clients: List<Client>) {
        val totalDette = clients.sumOf { it.soldeTotal ?: 0.0 }
    }

    private fun ouvreDetailsClient(client: Client) {
        val intent = Intent(requireContext(), DetteOperation::class.java).apply {
            putExtra("CLIENT_ID", client.id.toString())
            putExtra("CLIENT_NOM", client.nomComplet)
            putExtra("CLIENT_SOLD", client.soldeTotal)
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        fetchClients()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}