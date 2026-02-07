package ifaq.ujkz.detty

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import ifaq.ujkz.detty.data.SuperbaseClientProvider
import ifaq.ujkz.detty.datasource.BoutiqueDataSource
import ifaq.ujkz.detty.entity.Boutique
import ifaq.ujkz.detty.repository.BoutiqueRepository
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class SingIn : AppCompatActivity() {

    private lateinit var repository: BoutiqueRepository
    private lateinit var etNom : TextInputEditText
    private lateinit var etMail : TextInputEditText
    private lateinit var etPhone : TextInputEditText
    private lateinit var etPass : TextInputEditText
    private lateinit var etConfirm : TextInputEditText
    private lateinit var signIn: Button
    private lateinit var login: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sing_in)

        // Initialisation des vues
        etNom = findViewById(R.id.et_NomBoutique)
        etMail = findViewById(R.id.et_Mail)
        etPhone = findViewById(R.id.et_telephone)
        etPass = findViewById(R.id.et_password)
        etConfirm = findViewById(R.id.et_ConfirmPassword)
        signIn = findViewById(R.id.bnt_singIn)
        login = findViewById(R.id.bnt_mtb_login)

        // Initialisation du Repository
        repository = BoutiqueRepository(BoutiqueDataSource(SuperbaseClientProvider.client))

        signIn.setOnClickListener {
            val nom = etNom.text.toString().trim()
            val mail = etMail.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val pass = etPass.text.toString().trim()
            val confirm = etConfirm.text.toString().trim()

            if (pass == confirm && pass.isNotEmpty() && nom.isNotEmpty() && mail.isNotEmpty()) {
                susbcribe(nom, mail, pass, phone)
            } else if (pass != confirm) {
                Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }

        login.setOnClickListener {
            connexion()
        }
    }

    private fun susbcribe(nom: String, mail: String, pwd: String, phone: String) {
        val client = SuperbaseClientProvider.client

        lifecycleScope.launch {
            try {
                val authResponse = client.auth.signUpWith(Email) {
                    this.email = mail
                    this.password = pwd
                }

                val userId = authResponse?.id ?: client.auth.currentUserOrNull()?.id

                if (userId != null) {
                    val boutique = Boutique(
                        id = userId, // L'UUID de l'Auth devient l'ID de la boutique
                        nomBoutique = nom,
                        mail = mail,
                        telephone = phone
                    )

                    repository.creerBoutique(boutique)

                    Toast.makeText(this@SingIn, "Bienvenue $nom ! Compte créé.", Toast.LENGTH_LONG).show()

                    connexion()
                } else {
                    Toast.makeText(this@SingIn, "Erreur lors de la récupération de l'ID", Toast.LENGTH_LONG).show()
                }

            } catch (e: Exception) {
                val errorMsg = e.message ?: ""
                Log.e("DETTY_DEBUG", "Erreur Inscription: $errorMsg")
                println("==============================================")
                println(errorMsg)
                println("=====================================================")

                // Cas spécifique : L'utilisateur existe déjà
                if (errorMsg.contains("already_exists", ignoreCase = true) || errorMsg.contains("23505")) {
                    Toast.makeText(this@SingIn, "Ce compte existe déjà. Connectez-vous.", Toast.LENGTH_LONG).show()
                    connexion()
                } else {
                    // Autres erreurs (Réseau, RLS, etc.)
                    Toast.makeText(this@SingIn, "Échec : ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                    println("===============================================")
                    println("${e.localizedMessage}")
                    println("==================================================")
                }
            }
        }
    }

    private fun connexion() {
        val openLoginForm = Intent(this, MainActivity::class.java)
        startActivity(openLoginForm)
        finish()
    }
}