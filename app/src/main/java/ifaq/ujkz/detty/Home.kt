package ifaq.ujkz.detty

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {
    private lateinit var AddClient: Button
    private lateinit var navbutton: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        navbutton = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        AddClient = findViewById<Button>(R.id.bnt_addclient)

        AddClient.setOnClickListener {
            open_client_add_form()
        }

        navbutton.setOnItemSelectedListener ( ::open_activity )

    }
    /**
     * Function open client addition formulaire
     * */

    private fun open_client_add_form(){
        val open = Intent(this,AddClientForm::class.java)
        startActivity(open)
    }

    private fun open_activity(item: MenuItem):Boolean{
        when(item.itemId) {
            R.id.home -> {
                // ouvrir fragment ou activity Home
                startActivity(Intent(this, Home::class.java))
                return true
            }
            R.id.historique -> {
                // ouvrir fragment ou activity Profile
                startActivity(Intent(this, Historique::class.java))
                return true
            }
            R.id.profile -> {
                // ouvrir fragment ou activity Profile
                startActivity(Intent(this, Home::class.java))
                return true
            }
            else -> return false
        }
    }


}