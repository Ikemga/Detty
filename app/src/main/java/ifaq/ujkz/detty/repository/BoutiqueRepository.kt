package ifaq.ujkz.detty.repository

import ifaq.ujkz.detty.datasource.BoutiqueDataSource
import ifaq.ujkz.detty.entity.Boutique

class BoutiqueRepository(
    private val boutiqueDataSource: BoutiqueDataSource
) {

    /**
     * Enregistre une nouvelle boutique lors de la première utilisation.
     */
    suspend fun creerBoutique(boutique: Boutique) {
        boutiqueDataSource.insertBoutique(boutique)
    }

    /**
     * Met à jour les informations de la boutique (nom, etc.).
     */
    suspend fun modifierBoutique(boutique: Boutique) {
        boutiqueDataSource.updateBoutique(boutique)
    }

    /**
     * Supprime la boutique de la base de données.
     */
    suspend fun supprimerBoutique(id: String) {
        boutiqueDataSource.deleteBoutique(id)
    }
}