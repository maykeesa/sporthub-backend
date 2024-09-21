package br.com.sporthub.usuario

import br.com.sporthub.reserva.Reserva
import br.com.sporthub.reserva.ReservaService
import br.com.sporthub.usuario.dto.UsuarioDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/usuario")
class UsuarioController {

    @Autowired
    private lateinit var usuarioService: UsuarioService
    @Autowired
    private lateinit var usuarioRep: UsuarioRepository
    @Autowired
    private lateinit var reservaService: ReservaService

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma lista de usuários"),
        ApiResponse(responseCode = "204", description = "Não há usuários cadastrados")
    ])
    fun getAll(@PageableDefault(sort = ["nome"], direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable): ResponseEntity<Page<UsuarioDto>>{
        val usuariosPage: Page<Usuario> = this.usuarioRep.findAll(paginacao)
        val usuariosDtoPage = usuariosPage.map { usuario -> UsuarioDto(usuario) }

        return ResponseEntity.ok(usuariosDtoPage)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um usuário pelo ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna um usuário"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ])
    fun getOne(@PathVariable id: String): ResponseEntity<Any>{
        val usuarioOpt: Optional<Usuario> = this.usuarioRep.findById(UUID.fromString(id))

        if(usuarioOpt.isPresent){
            return ResponseEntity.ok(UsuarioDto(usuarioOpt.get()))
        }

        return  ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna o usuário atualizado"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ])
    fun update(@PathVariable id: String, @RequestBody usuarioForm: Map<String,Any>): ResponseEntity<Any>{
        val usuarioOpt: Optional<Usuario> = this.usuarioRep.findById(UUID.fromString(id))

        if (usuarioOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        val usuarioAtualizado= this.usuarioService.atualizarEntidade(usuarioOpt.get(), usuarioForm)
        return ResponseEntity.status(202).body(UsuarioDto(usuarioAtualizado as Usuario))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ])
    fun delete(@PathVariable id: String): ResponseEntity<Void>{
        val usuarioOpt: Optional<Usuario> = this.usuarioRep.findById(UUID.fromString(id))

        if(usuarioOpt.isEmpty){
            return ResponseEntity.notFound().build()
        }

        this.usuarioRep.deleteById(UUID.fromString(id))
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{id}/reservas")
    @Operation(summary = "Listar todos os agendamentos de um usuário")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Retorna uma lista de agendamentos"),
        ApiResponse(responseCode = "204", description = "Não há agendamentos cadastrados")
    ])
    fun getReservas(@PathVariable id: String, @PageableDefault(sort = ["data"], direction = Sort.Direction.ASC,
        page = 0, size = 10) paginacao: Pageable): ResponseEntity<Page<Reserva>>{

        val reservasPage: Page<Reserva> = this.reservaService.getReservasByUsuarioId(paginacao, id)

        if(reservasPage.isEmpty){
            return ResponseEntity.noContent().build()
        }

        return ResponseEntity.ok(reservasPage)
    }

}