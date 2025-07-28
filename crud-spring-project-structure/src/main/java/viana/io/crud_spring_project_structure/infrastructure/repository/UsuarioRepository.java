package viana.io.crud_spring_project_structure.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import viana.io.crud_spring_project_structure.infrastructure.entitys.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

    Optional<Usuario> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
