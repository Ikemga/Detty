package ifaq.ujkz.detty.repository

import ifaq.ujkz.detty.datasource.DetteDataSource
import ifaq.ujkz.detty.datasource.PaiementDataSource
import ifaq.ujkz.detty.entity.Operation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OperationRepository(
    private val detteDS: DetteDataSource,
    private val paiementDS: PaiementDataSource
) {

    /**
     * Récupère l'historique complet (Dettes + Paiements) trié
     * CORRECTION : Le clientId passe de Int à String pour supporter les UUID
     */
    suspend fun getFullHistory(clientId: String): List<Operation> = withContext(Dispatchers.IO) {
        try {
            // Ces appels vont maintenant aussi nécessiter String dans les DataSources
            val dettes = detteDS.getDettesByClient(clientId)
            val paiements = paiementDS.getPaiementsByClient(clientId)

            // Fusion des deux listes
            val fullList: MutableList<Operation> = mutableListOf()
            fullList.addAll(dettes)
            fullList.addAll(paiements)



            fullList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}