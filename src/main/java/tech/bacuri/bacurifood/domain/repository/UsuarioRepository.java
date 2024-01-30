package tech.bacuri.bacurifood.domain.repository;

import tech.bacuri.bacurifood.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
}
