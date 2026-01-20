package ifaq.ujkz.detty

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var singIn: MaterialButton
    private lateinit var login: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //Récupération de l'id du botton se connecter
        singIn = findViewById<MaterialButton>(R.id.singIn)
        login = findViewById<Button>(R.id.login)

        //* Ecouter sur le material button je n'ai pas de compte
        singIn.setOnClickListener {
            open_sing_in()
        }

        //* Ecouter sur le boutton Se conncter
        login.setOnClickListener {
            open_home()
        }

    }
    /***
     * Fonction pour ouvrir l'activité d'inscription
     **/
    private fun open_sing_in(){
        val open = Intent(this, SingIn::class.java)
        startActivity(open)
    }
    /***
     * Fonction pour ouvrir la l'activité home
     **/
    private fun open_home(){
        val open = Intent(this, Home::class.java)
        startActivity(open)
    }

}