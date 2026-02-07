package ifaq.ujkz.detty

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.text.replace

class Home : AppCompatActivity() {
    private lateinit var navbutton: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        initNavigation()
    }
        private fun initNavigation() {
            navbutton = findViewById<BottomNavigationView>(R.id.bottomNavigation)
            navbutton.setOnItemSelectedListener ( ::open_activity )

        }

    private fun open_activity(item: MenuItem):Boolean{
        val fragment = when (item.itemId) {
            R.id.home -> HomeFragment()
            R.id.historique -> HistoriqueFragment()
            R.id.profile -> ProfilFragment()
            else -> null
        }
        return if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            true
        } else {
            false
        }
    }
}


