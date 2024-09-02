package br.com.sporthub.grupo

import br.com.sporthub.torneio.Torneio
import br.com.sporthub.usuario.Usuario
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "grupos")
data class Grupo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null, // Using nullable UUID to support default value

    var nome: String,

    var descricao: String,

    @CreationTimestamp
    var dataCriacao: LocalDateTime,

    @ManyToMany
    @JoinTable(name = "usuario_grupo",
        joinColumns = [JoinColumn(name = "grupo_id")],
        inverseJoinColumns = [JoinColumn(name = "usuario_id")]
    )
    var usuarios: List<Usuario>,

    @OneToMany(mappedBy = "grupo", cascade = [CascadeType.ALL], orphanRemoval = true)
    var torneios: List<Torneio>
)
