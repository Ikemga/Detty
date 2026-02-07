package ifaq.ujkz.detty

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import ifaq.ujkz.detty.adapter.OperationAdapter
import ifaq.ujkz.detty.data.SuperbaseClientProvider
import ifaq.ujkz.detty.datasource.DetteDataSource
import ifaq.ujkz.detty.datasource.PaiementDataSource
import ifaq.ujkz.detty.entity.Dette
import ifaq.ujkz.detty.entity.Paiement
import ifaq.ujkz.detty.repository.OperationRepository
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class DetteOperation : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var tvSoldeGeneral: TextView
    private lateinit var tvEmptyState: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnPaiement: Button
    private lateinit var btnDette: Button
    private lateinit var operationAdapter: OperationAdapter

    private var currentClientId: String? = null

    private val repository by lazy {
        OperationRepository(
            DetteDataSource(SuperbaseClientProvider.client.postgrest),
            PaiementDataSource(SuperbaseClientProvider.client.postgrest)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dette_operation)

        setupViews()
        setupRecyclerView()

        currentClientId = intent.getStringExtra("CLIENT_ID")
        val clientNom = intent.getStringExtra("CLIENT_NOM")

        toolbar.title = clientNom ?: "Détails Client"
        toolbar.setNavigationOnClickListener { finish() }

        btnPaiement.setOnClickListener { openPaiementForm() }
        btnDette.setOnClickListener { openDetteForm() }
    }

    override fun onResume() {
        super.onResume()

        currentClientId?.let { chargerHistorique(it) }
    }

    private fun setupViews() {
        toolbar = findViewById(R.id.mt_toolbar)
        tvSoldeGeneral = findViewById(R.id.tv_Solde)
        tvEmptyState = findViewById(R.id.tv_empty_state)
        recyclerView = findViewById(R.id.recyclerViewOpération)
        btnPaiement = findViewById(R.id.bnt_paie)
        btnDette = findViewById(R.id.bnt_dette)
    }

    private fun setupRecyclerView() {
        operationAdapter = OperationAdapter()
        recyclerView.apply {
            adapter = operationAdapter
            layoutManager = LinearLayoutManager(this@DetteOperation)
            setHasFixedSize(true)
        }
    }

    private fun chargerHistorique(clientId: String) {
        lifecycleScope.launch {
            try {
                val historique = repository.getFullHistory(clientId)

                if (historique.isEmpty()) {
                    toggleEmptyState(true)
                    operationAdapter.updateList(emptyList())
                    majAffichageSolde(0.0)
                } else {
                    toggleEmptyState(false)
                    operationAdapter.updateList(historique)


                    val soldeTotal = historique.sumOf { op ->
                        when (op) {
                            is Dette -> op.montantDette
                            is Paiement -> -op.montant
                            else -> 0.0
                        }
                    }
                    majAffichageSolde(soldeTotal)
                }
            } catch (e: Exception) {
                Toast.makeText(this@DetteOperation, "Erreur de synchronisation", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun majAffichageSolde(solde: Double) {
        val format = NumberFormat.getInstance(Locale.FRANCE)
        tvSoldeGeneral.text = "${format.format(solde.toInt())} FCFA"

        tvSoldeGeneral.setTextColor(
            if (solde > 0) Color.parseColor("#E91E63")
            else Color.parseColor("#2E7D32")
        )
    }

    private fun toggleEmptyState(isEmpty: Boolean) {
        recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
        tvEmptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }


    private fun openPaiementForm() {
        val intent = Intent(this, ifaq.ujkz.detty.Paiment::class.java)
        intent.putExtra("CLIENT_ID", currentClientId)
        startActivity(intent)
    }

    private fun openDetteForm() {
        val intent = Intent(this, ifaq.ujkz.detty.Dette::class.java)
        intent.putExtra("CLIENT_ID", currentClientId)
        startActivity(intent)
    }
}