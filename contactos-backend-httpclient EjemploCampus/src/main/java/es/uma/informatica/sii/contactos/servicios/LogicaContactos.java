package es.uma.informatica.sii.contactos.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uma.informatica.sii.contactos.entidades.Contacto;
import es.uma.informatica.sii.contactos.repositorios.ContactoRepo;
import es.uma.informatica.sii.contactos.servicios.excepciones.ContactoNoEncontrado;

@Service
@Transactional
public class LogicaContactos {
	
	private ContactoRepo repo;
	
	@Autowired
	public LogicaContactos(ContactoRepo repo) {
		this.repo=repo;
	}
	
	public List<Contacto> getTodosContactos() {
		return repo.findAll();
	}
	
	public Contacto aniadirContacto(Contacto contacto) {
		contacto.setId(null);
		return repo.save(contacto);
	}
	
	public Optional<Contacto> getContactoPorId(Long id) {
		return repo.findById(id);
	}
	
	public Contacto modificarContacto(Contacto contacto) {
		if (repo.existsById(contacto.getId())) {
			return repo.save(contacto);
		} else {
			throw new ContactoNoEncontrado();
		}
	}
	
	public void eliminarContacto(Long id) {
		if (repo.existsById(id)) {
			repo.deleteById(id);
		} else {
			throw new ContactoNoEncontrado();
		}
	}
	
}
