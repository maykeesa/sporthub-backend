package br.com.sporthub.usuario

import br.com.sporthub.grupo.Grupo
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "usuarios")
data class Usuario(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var cpf: String,
    var nome: String,
    var email: String,
    var senha: String,
    var dataNascimento: LocalDate,
    var genero: String,
    var telefone: String,
    @CreationTimestamp
    var dataCriacao: LocalDateTime,

    @ManyToMany(mappedBy = "usuarios")
    var grupos: List<Grupo>
    ) {
}