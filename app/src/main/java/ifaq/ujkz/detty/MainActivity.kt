package ifaq.ujkz.detty

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import ifaq.ujkz.detty.data.SuperbaseClientProvider
import io.github.jan.supabase.auth.auth // Import nécessaire pour accéder à .auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPass: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var btnGoToSignIn: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialisation des vues
        etEmail = findViewById(R.id.et_email)
        etPass = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.login)
        btnGoToSignIn = findViewById(R.id.singIn)

        // Navigation vers l'inscription
        btnGoToSignIn.setOnClickListener {
            open_sing_in()
        }

        // Action de connexion
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPass.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()){
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun open_sing_in() {
        val intent = Intent(this, SingIn::class.java)
        startActivity(intent)
    }

    private fun loginUser(mail: String, pass: String) {
        // Accès au client Supabase
        val supabase = SuperbaseClientProvider.client

        lifecycleScope.launch {
            try {
                // Tentative de connexion via Supabase Auth
                supabase.auth.signInWith(Email) {
                    email = mail
                    password = pass
                }

                Toast.makeText(this@MainActivity, "Connexion réussie !", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@MainActivity, Home::class.java)
                startActivity(intent)
                finish()

            } catch (e: Exception) {
                // Gestion fine des erreurs
                val errorMsg = e.localizedMessage ?: "Erreur de connexion"

                when {
                    errorMsg.contains("invalid_credentials", true) -> {
                        Toast.makeText(this@MainActivity, "Email ou mot de passe incorrect", Toast.LENGTH_LONG).show()
                    }
                    errorMsg.contains("network", true) -> {
                        Toast.makeText(this@MainActivity, "Problème de connexion internet", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(this@MainActivity, "Erreur : $errorMsg", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}