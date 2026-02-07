package ifaq.ujkz.detty.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Boutique(
    @SerialName("id")
    val id: String,

    @SerialName("nom_boutique")
    val nomBoutique: String,

    @SerialName("telephone")
    val telephone: String,

    @SerialName("mail")
    val mail: String,

    @SerialName("created_at")
    val createdAt: String? = null
)