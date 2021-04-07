package br.edu.utfpr.pb.pw25s.aula5.repository;

import br.edu.utfpr.pb.pw25s.aula5.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByUsername(String username);
	
}
