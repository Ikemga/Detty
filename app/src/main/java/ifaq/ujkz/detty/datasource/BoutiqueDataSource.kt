package ifaq.ujkz.detty.datasource

import ifaq.ujkz.detty.entity.Boutique
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class BoutiqueDataSource(
    private val supabase: SupabaseClient
) {

    /**
     * CREATE : Ajouter une nouvelle boutique
     * Correction : Table "boutique" (sans s)
     */
    suspend fun insertBoutique(boutique: Boutique) {
        supabase.from("boutique").insert(boutique)
    }

    /**
     * READ : Récupérer la boutique connectée
     * Correction : On filtre directement par l'ID de la boutique
     */
    suspend fun getBoutiqueById(id: String): Boutique? {
        return supabase.from("boutique")
            .select {
                filter {
                    eq("id", id)
                }
            }
            .decodeSingleOrNull<Boutique>()
    }

    /**
     * UPDATE : Modifier les informations de la boutique
     */
    suspend fun updateBoutique(boutique: Boutique) {
        supabase.from("boutique").update(boutique) {
            filter {
                eq("id", boutique.id)
            }
        }
    }

    /**
     * DELETE : Supprimer une boutique
     */
    suspend fun deleteBoutique(id: String) {
        supabase.from("boutique").delete {
            filter {
                eq("id", id)
            }
        }
    }
}