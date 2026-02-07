package ifaq.ujkz.detty

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import ifaq.ujkz.detty.data.SuperbaseClientProvider
import ifaq.ujkz.detty.datasource.DetteDataSource
import ifaq.ujkz.detty.entity.Dette as DetteEntity
import ifaq.ujkz.detty.repository.DetteRepository
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

class Dette : AppCompatActivity() {

    private lateinit var et_montant: TextInputEditText
    private lateinit var et_detail: TextView
    private lateinit var et_date: TextInputEditText
    private lateinit var et_desc: TextInputEditText
    private lateinit var btn_confirme: Button

    private var cumulTotal: Double = 0.0
    private var dernierOperateur = '+'

    private var selectedClientId: String? = null

    private lateinit var repository: DetteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dette)

        // 1. RÉCUPÉRER L'ID DU CLIENT ENVOYÉ PAR L'ÉCRAN PRÉCÉDENT
        selectedClientId = intent.getStringExtra("CLIENT_ID")

        setupSupabase()
        setupViews()
        setupCalculator()

        btn_confirme.setOnClickListener {
            enregistrerDetteDansSupabase()
        }
    }

    private fun setupSupabase() {
        val client = SuperbaseClientProvider.client
        val dataSource = DetteDataSource(client.postgrest)
        repository = DetteRepository(dataSource)
    }

    private fun setupViews() {
        et_montant = findViewById(R.id.et_MntTotal)
        et_detail = findViewById(R.id.tv_detail_somme)
        et_date = findViewById(R.id.et_data_now)
        et_desc = findViewById(R.id.et_commentaire)
        btn_confirme = findViewById(R.id.bn_confirme)
    }

    private fun setupCalculator() {
        et_montant.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString()
                if (input.isEmpty()) return
                val dernierChar = input.last()
                val operateurs = listOf('+', '-', '*')
                if (operateurs.contains(dernierChar)) {
                    val valeurStr = input.substring(0, input.length - 1).trim()
                    if (valeurStr.isNotEmpty()) {
                        val valeurNum = valeurStr.toDoubleOrNull() ?: 0.0
                        effectuerCalcul(valeurNum)
                        dernierOperateur = dernierChar
                        et_detail.text = "$cumulTotal FCFA"
                        et_montant.post { et_montant.setText("") }
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun effectuerCalcul(valeur: Double) {
        when (dernierOperateur) {
            '+' -> cumulTotal += valeur
            '-' -> cumulTotal -= valeur
            '*' -> cumulTotal *= valeur
        }
    }

    private fun enregistrerDetteDansSupabase() {
        val resteStr = et_montant.text.toString().trim()
        if (resteStr.isNotEmpty()) {
            val resteNum = resteStr.toDoubleOrNull() ?: 0.0
            effectuerCalcul(resteNum)
        }

        if (selectedClientId.isNullOrEmpty()) {
            Toast.makeText(this, "Erreur fatale : Aucun client sélectionné", Toast.LENGTH_SHORT).show()
            return
        }

        if (cumulTotal <= 0) {
            Toast.makeText(this, "Veuillez saisir un montant", Toast.LENGTH_SHORT).show()
            return
        }

        val nouvelleDette = DetteEntity(
            clientId = selectedClientId!!, // C'est ici que l'ID est lié au bon client
            description = et_desc.text.toString().ifEmpty { "Nouvelle dette" },
            montantDette = cumulTotal,
            montantRestant = cumulTotal
        )

        lifecycleScope.launch {
            repository.createDette(nouvelleDette).onSuccess {
                Toast.makeText(this@Dette, "Dette enregistrée : $cumulTotal FCFA", Toast.LENGTH_LONG).show()
                finish()
            }.onFailure { erreur ->
                val message = when {
                    erreur.message?.contains("foreign key") == true -> "Erreur : Le client n'existe pas dans la base."
                    else -> "Erreur réseau : ${erreur.localizedMessage}"
                }
                Toast.makeText(this@Dette, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}