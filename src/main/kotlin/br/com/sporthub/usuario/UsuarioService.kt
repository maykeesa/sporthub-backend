package br.com.sporthub.usuario

import br.com.sporthub.grupo.form.GrupoForm
import br.com.sporthub.reserva.Reserva
import br.com.sporthub.reserva.ReservaRepository
import br.com.sporthub.service.GenericService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsuarioService : GenericService<Usuario>(Usuario::class.java){

    @Autowired
    private lateinit var usuarioRep: UsuarioRepository

    @Autowired
    private lateinit var reservaRep: ReservaRepository

    fun getListUsuarios(grupoForm: GrupoForm): ArrayList<Usuario>{
        val usuariosAny: ArrayList<Any> = transformarListIdToEntity(grupoForm.usuarios)
        val usuarios: ArrayList<Usuario> = ArrayList(usuariosAny.map { it as Usuario })
        grupoForm.usuarios = ArrayList()

        return usuarios
    }

    /*
    fun attEsportesFavoritosUsuarios(){
        val map = HashMap<String, Int>()
        val usuarios: List<Usuario> = this.usuarioRep.findAll()

        usuarios.forEach{usuario ->
            var reservas: List<Reserva> = reservaRep.findReservasByUsuarioId(usuario.id)

            reservas.forEach{reserva ->
                reserva.horario.quadra
            }
        }
     */
    }

}