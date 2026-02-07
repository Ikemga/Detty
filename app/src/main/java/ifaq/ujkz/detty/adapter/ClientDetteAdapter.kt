package ifaq.ujkz.detty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ifaq.ujkz.detty.R
import ifaq.ujkz.detty.entity.Client

class ClientDetteAdapter(
    private val clients: MutableList<Client>,
    private val onItemClick: (Client) -> Unit
) : RecyclerView.Adapter<ClientDetteAdapter.ClientViewHolder>() {

    inner class ClientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNom: TextView = itemView.findViewById(R.id.tv_Nom)
        val tvContact: TextView = itemView.findViewById(R.id.tv_Contact)
        val tvSolde: TextView = itemView.findViewById(R.id.tv_Solde)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_client, parent, false)
        return ClientViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val client = clients[position]

        // On utilise les propriétés de ton entité Client
        holder.tvNom.text = client.nomComplet
        holder.tvContact.text = client.phone ?: "pas de contact"

        // Affichage du solde formaté (Ex: 5000 FCFA)
        val solde = client.soldeTotal ?: 0.0
        holder.tvSolde.text = "$solde FCFA"

        // Changement de couleur
        if (solde > 0) {
            holder.tvSolde.setTextColor(android.graphics.Color.RED)
        } else {
            holder.tvSolde.setTextColor(android.graphics.Color.GREEN)
        }

        // Gestion du clic

        holder.itemView.setOnClickListener {
            onItemClick(client)
        }
    }

    override fun getItemCount(): Int = clients.size

    /**
     * Met à jour la liste des clients et rafraîchit le RecyclerView
     */
    fun updateData(newList: List<Client>) {
        clients.clear()
        clients.addAll(newList)
        notifyDataSetChanged()
    }
}