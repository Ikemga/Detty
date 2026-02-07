package ifaq.ujkz.detty

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ifaq.ujkz.detty.databinding.ActivityAddClientFormBinding

class AddClientForm : AppCompatActivity() {

    private lateinit var add : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //bundleVar = ActivityAddClientFormBinding.inflate(layoutInflater)
        //setContentView(bundleVar.root)
        setContentView(R.layout.activity_add_client_form)

        add = findViewById<Button>(R.id.bnt_addclient)
        add.setOnClickListener {
            open()
        }

    }

    private fun open() {
        val open = Intent(this,AjouterClient::class.java)
        startActivity(open)
    }
}