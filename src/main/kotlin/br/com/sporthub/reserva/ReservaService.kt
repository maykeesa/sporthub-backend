package br.com.sporthub.reserva

import br.com.sporthub.service.GenericService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ReservaService(private val reservaRepository: ReservaRepository): GenericService<Reserva>(Reserva::class.java) {

    // Pega a lista de reservas por id do usuario diretamente no banco
    fun getReservasByUsuarioId(pageable: Pageable,usuarioId: String): Page<Reserva> {
        return reservaRepository.findByUsuarioId(pageable,usuarioId)
    }
}
