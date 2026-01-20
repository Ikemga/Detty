package ifaq.ujkz.detty

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class SingIn : AppCompatActivity() {
    /**
     *
     * */
    private  lateinit var singIn: Button
    private lateinit var login: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sing_in)

        login=findViewById<MaterialButton>(R.id.mtb_login)

        login.setOnClickListener {
            connexion()
        }
    }

    private fun connexion(){
        val open_login_form = Intent(this, MainActivity::class.java)
        startActivity(open_login_form)
    }

}
