package ifaq.ujkz.detty.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ifaq.ujkz.detty.R
import ifaq.ujkz.detty.entity.Dette
import ifaq.ujkz.detty.entity.Operation
import ifaq.ujkz.detty.entity.Paiement

class OperationAdapter(
    private var list: List<Operation> = emptyList()
) : RecyclerView.Adapter<OperationAdapter.OperationViewHolder>() {

    // Liste interne qui stocke l'opération ET le solde calculé à cet instant
    private var operationsWithBalance: List<Pair<Operation, Double>> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dette_operation, parent, false)
        return OperationViewHolder(view)
    }

    override fun onBindViewHolder(holder: OperationViewHolder, position: Int) {
        val (operation, balanceAtThatTime) = operationsWithBalance[position]
        holder.bind(operation, balanceAtThatTime)
    }

    override fun getItemCount(): Int = operationsWithBalance.size

    fun updateList(newList: List<Operation>) {
        // 1. Trier du plus ANCIEN au plus RÉCENT pour calculer le solde
        val sortedOldestFirst = newList.sortedBy { it.dateOperation }

        var runningBalance = 0.0
        val calculatedList = sortedOldestFirst.map { op ->
            if (op is Dette) {
                runningBalance += op.montantOperation
            } else if (op is Paiement) {
                runningBalance -= op.montantOperation
            }
            Pair(op, runningBalance)
        }

        // 2. Inverser pour l'affichage (Plus RÉCENT en haut de la liste)
        operationsWithBalance = calculatedList.reversed()
        notifyDataSetChanged()
    }

    class OperationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvDateTime = view.findViewById<TextView>(R.id.tv_dataTime)
        private val tvSolde = view.findViewById<TextView>(R.id.tv_solde) // Affiche le solde cumulé
        private val tvSomme = view.findViewById<TextView>(R.id.tv_somme) // Affiche le montant +/-
        private val tvType = view.findViewById<TextView>(R.id.tv_type)

        fun bind(operation: Operation, balance: Double) {
            tvDateTime.text = operation.dateOperation.take(16).replace("T", " ")

            // Affichage du solde après cette opération
            tvSolde.text = "Solde: ${balance.toInt()} F"

            when (operation) {
                is Dette -> {
                    tvSomme.text = "+ ${operation.montantOperation.toInt()} F"
                    tvSomme.setTextColor(Color.parseColor("#E91E63")) // Rose/Rouge
                    tvType.text = "Dette (${operation.description})"
                }
                is Paiement -> {
                    tvSomme.text = "- ${operation.montantOperation.toInt()} F"
                    tvSomme.setTextColor(Color.parseColor("#2E7D32")) // Vert
                    tvType.text = "Paiement"
                }
            }
        }
    }
}