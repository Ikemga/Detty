package ifaq.ujkz.detty.repository

import ifaq.ujkz.detty.datasource.DetteDataSource
import ifaq.ujkz.detty.entity.Dette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetteRepository(
    private val dataSource: DetteDataSource
) {

    /**
     * Récupère la liste des dettes d'un client.
     * Utilise Dispatchers.IO pour l'appel réseau Supabase.
     */
    suspend fun fetchDettesByClient(clientId: String): Result<List<Dette>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                dataSource.getDettesByClient(clientId)
            }
        }
    }

    /**
     * Enregistre une nouvelle dette.
     * C'est ici que tu appelleras cette méthode après ton calcul "calcu()".
     */
    suspend fun createDette(dette: Dette): Result<Unit> {
        return withContext(Dispatchers.IO) {
            runCatching {
                dataSource.insertDette(dette)
            }
        }
    }

    /**
     * Met à jour le solde d'une dette spécifique.
     */
    suspend fun updateRemainingAmount(detteId: Int, newAmount: Double): Result<Unit> {
        return withContext(Dispatchers.IO) {
            runCatching {
                dataSource.updateMontantRestant(detteId, newAmount)
            }
        }
    }

    /**
     * Supprime une dette par son ID.
     */
    suspend fun removeDette(detteId: Int): Result<Unit> {
        return withContext(Dispatchers.IO) {
            runCatching {
                dataSource.deleteDette(detteId)
            }
        }
    }
}