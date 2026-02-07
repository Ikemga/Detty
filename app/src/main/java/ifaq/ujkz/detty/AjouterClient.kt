package ifaq.ujkz.detty

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import ifaq.ujkz.detty.data.SuperbaseClientProvider
import ifaq.ujkz.detty.datasource.ClientDataSource
import ifaq.ujkz.detty.entity.Client
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

class AjouterClient : AppCompatActivity() {

    private lateinit var etNomComplet: TextInputEditText
    private lateinit var etCodePays: TextInputEditText
    private lateinit var etTelephone: TextInputEditText
    private lateinit var etAdresse: TextInputEditText
    private lateinit var btnConfirmer: Button
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajouter_client)

        etNomComplet = findViewById(R.id.et_NomComplet)
        etCodePays = findViewById(R.id.et_codePays)
        etTelephone = findViewById(R.id.et_Telephone)
        etAdresse = findViewById(R.id.et_Adresse)
        btnConfirmer = findViewById(R.id.confirme)
        toolbar = findViewById(R.id.toolbar)

        etCodePays.setText("+226")

        toolbar.setNavigationOnClickListener {
            finish()
        }

        btnConfirmer.setOnClickListener {
            sauvegarderClient()
        }
    }

    private fun sauvegarderClient() {
        val nom = etNomComplet.text.toString().trim()
        val code = etCodePays.text.toString().trim()
        val tel = etTelephone.text.toString().trim()
        val adresse = etAdresse.text.toString().trim()

        if (nom.isEmpty() || tel.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir le nom et le téléphone", Toast.LENGTH_SHORT).show()
            return
        }

        val telephoneComplet = "$code $tel"
        val supabase = SuperbaseClientProvider.client
        val dataSource = ClientDataSource(supabase)

        lifecycleScope.launch {
            try {
                // Récupérer l'ID de l'utilisateur (Boutiquier) connecté
                val currentUserId = supabase.auth.currentUserOrNull()?.id

                if (currentUserId != null) {

                    val nouveauClient = Client(
                        id = null,
                        userId = currentUserId,
                        nomComplet = nom,
                        phone = telephoneComplet,
                        mail = adresse,
                        soldeTotal = 0.0
                    )

                    dataSource.inserClient(nouveauClient)

                    Toast.makeText(this@AjouterClient, "Client enregistré !", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@AjouterClient, "Erreur : Session expirée", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@AjouterClient, "Erreur : ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}