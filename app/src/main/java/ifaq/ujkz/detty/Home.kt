package ifaq.ujkz.detty

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Home : AppCompatActivity() {
    private lateinit var AddClient: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        AddClient = findViewById<Button>(R.id.bnt_addclient)

        AddClient.setOnClickListener {
            open_client_add_form()
        }
    }
    /**
     * Function open client addition formulaire
     * */

    private fun open_client_add_form(){
        val open = Intent(this,AddClientForm::class.java)
        startActivity(open)
    }


}