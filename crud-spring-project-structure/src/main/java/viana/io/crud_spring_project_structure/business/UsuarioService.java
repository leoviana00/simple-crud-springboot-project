package viana.io.crud_spring_project_structure.business;

import org.springframework.stereotype.Service;

import viana.io.crud_spring_project_structure.infrastructure.entitys.Usuario;
import viana.io.crud_spring_project_structure.infrastructure.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void salvarUsuario(Usuario usuario){
        usuarioRepository.saveAndFlush(usuario);
    }

    public Usuario buscUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Email não encontrado!")
        );
    }    

    public void deletarUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public void atualizarUsuarioPorId(Integer id, Usuario usuario){
        Usuario usuarioEntity = usuarioRepository.findById(id).orElseThrow(() -> 
                new RuntimeException("Usuário não encontrado"));       
        Usuario usuarioAtualizado = Usuario.builder()
                .email(usuario.getEmail() != null ? 
                    usuario.getEmail() : usuarioEntity.getEmail())
                .nome(usuario.getNome() != null ?
                    usuario.getNome() : usuarioEntity.getNome())
                .id(usuarioEntity.getId())
                .build();
        
        usuarioRepository.saveAndFlush(usuarioAtualizado);
    }


}
