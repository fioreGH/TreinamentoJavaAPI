package br.com.springboot.aprendizado.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.springboot.aprendizado.model.Usuario;
import br.com.springboot.aprendizado.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {

	@Autowired /* IC/CD ou CDI - injeção de dependência */
	private UsuarioRepository usuarioRepository;

	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */
	@RequestMapping(value = "/nome/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String greetingText(@PathVariable String name) {
		
		return "Bem vindo: " + name + "!";
	}
	
	
	//Método para listar todos do BD
	@GetMapping(value = "listatodos")
	@ResponseBody
	public ResponseEntity<List<Usuario>> listaUsuario(){

		List<Usuario> usuarios = usuarioRepository.findAll();

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);

	}
	
	//Método para salvar dados no BD
	@PostMapping(value = "salvar")
	@ResponseBody
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){
		
		Usuario user = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED); 
	}
	
	
	//Método para attualizar registro no BD
	@PutMapping(value = "atualizar")
	@ResponseBody
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario){
		
		if(usuario.getId() == null) {
			return new ResponseEntity<String>(" Id não informado para atualização.", HttpStatus.OK);
		}
		
		Usuario user = usuarioRepository.saveAndFlush(usuario);
		
		return new ResponseEntity<Usuario>(user, HttpStatus.OK); 
	}
	
	
	//Método para excluir registro no BD
	@DeleteMapping(value = "deletar")
	@ResponseBody
	public ResponseEntity<?> deletar(@RequestParam Long idUser){

		usuarioRepository.deleteById(idUser);
		
		
		return new ResponseEntity<String>("Usuário deletado com sucesso", HttpStatus.OK);

	}
	
	
	//Método para localizar registro específico por ID no BD
	@GetMapping(value = "buscarId")
	@ResponseBody
	public ResponseEntity<Usuario> buscarId(@RequestParam(name = "idUser") Long idUser){
		
		Usuario user = usuarioRepository.findById(idUser).get();
		
		return new ResponseEntity<Usuario>(user, HttpStatus.OK); 
	}
	
	//Método para localizar registro específico por nome no BD
		@GetMapping(value = "buscarPorNome")
		@ResponseBody
		public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String nameUser){
			
			List<Usuario> user = usuarioRepository.busarPorNome(nameUser.trim().toUpperCase());
			
			return new ResponseEntity<List<Usuario>>(user, HttpStatus.OK); 
		}

}
