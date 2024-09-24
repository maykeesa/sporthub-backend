package br.com.sporthub.esporte

import br.com.sporthub.quadra.Quadra
import br.com.sporthub.usuario.Usuario
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "esportes")
data class Esporte(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var nome: String,

    @ManyToMany(mappedBy = "esportes")
    var quadras : MutableList<Quadra>,

    @ManyToMany(mappedBy = "usuarios")
    @JsonManagedReference
    var usuarios : MutableList<Usuario>
) {
    override fun toString(): String {
        return "Esporte(" +
                "id=$id, " +
                "nome='$nome', " +
                "quadras=${quadras.map{ it.id }})"
    }
}
