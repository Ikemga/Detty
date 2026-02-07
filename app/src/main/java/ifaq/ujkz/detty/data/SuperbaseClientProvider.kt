package ifaq.ujkz.detty.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage
import io.ktor.client.engine.cio.CIO

object SuperbaseClientProvider {
    private const val SUPABASE_URL = "https://onrqiwrwpeadcjsmtlbo.supabase.co"
    private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9ucnFpd3J3cGVhZGNqc210bGJvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Njg0Mjg3MjAsImV4cCI6MjA4NDAwNDcyMH0.CcnA4CHG9JETjaLdqxEOdveJ-fEMUmVMEMS32u0SFfQ"

    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_ANON_KEY
    ) {
        // Intitialisation du module d'authenfication

            install(Auth) {
                scheme = "retrofy"
                host = "auth/callback"
            }
        install(Postgrest)
        install(Realtime)
        //install(torage)
       // httpEngine = CIO.create()
    }
}
