package ifaq.ujkz.detty.repository

import ifaq.ujkz.detty.datasource.PaiementDataSource
import ifaq.ujkz.detty.entity.Paiement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PaiementRepository(
    private val dataSource: PaiementDataSource
) {

    /**
     * Récupère l'historique des paiements d'un client.
     * Bascule sur le thread IO pour ne pas bloquer l'UI.
     */
    suspend fun fetchPaiementsByClient(clientId: String): Result<List<Paiement>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                dataSource.getPaiementsByClient(clientId)
            }
        }
    }

    /**
     * Enregistre un nouveau remboursement.
     */
    suspend fun createPaiement(paiement: Paiement): Result<Unit> {
        return withContext(Dispatchers.IO) {
            runCatching {
                dataSource.insertPaiement(paiement)
            }
        }
    }

    /**
     * Récupère les paiements spécifiques à une dette.
     */
    suspend fun fetchPaiementsByDette(detteId: Int): Result<List<Paiement>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                dataSource.getPaiementsByDette(detteId)
            }
        }
    }
}