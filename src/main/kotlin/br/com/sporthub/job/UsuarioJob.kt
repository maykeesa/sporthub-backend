package br.com.sporthub.job

import br.com.sporthub.usuario.UsuarioService
import lombok.extern.log4j.Log4j2
import org.apache.logging.log4j.LogManager
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Log4j2
@Component
class UsuarioJob {

    private val logger = LoggerFactory.getLogger(UsuarioJob::class.java)

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Scheduled(fixedRate = 300000)
    fun attEsportesFavoritosUsuarios() {
        try {
            logger.info("Setores atualizados com sucesso")
        } catch (e: Exception) {
            logger.warn("Não foi possível atualizar os setores no momento")
        }
    }
}