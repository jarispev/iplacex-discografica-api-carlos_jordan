package org.iplacex.discografia.api.discos;

import org.iplacex.discografia.api.artistas.IArtistaRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepo;

    @Autowired
    private IArtistaRepository artistaRepo;

    // Registra un nuevo disco asociado a un artista existente.
    // Retorna HTTP 404 si el artista no existe o HTTP 201 si se crea el disco.
    @PostMapping(value = "/disco", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Disco> HandleInsertDiscoRequest(@RequestBody Disco disco) {

        if (!artistaRepo.existsById(disco.idArtista)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Disco temp = discoRepo.insert(disco);

        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    // Obtiene todos los discos registrados en la base de datos.
    // Retorna la lista de discos o HTTP 404 si no existen registros.
    @GetMapping(value = "/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {

        List<Disco> discos = discoRepo.findAll();

        if (discos.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(discos, HttpStatus.OK);
    }

    // Busca un disco en la base de datos mediante su ID.
    // Retorna el disco encontrado o HTTP 404 si no existe.
    @GetMapping(value = "/disco/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Disco> HandleGetDiscoByIdRequest(@PathVariable String id) {

        Optional<Disco> disco = discoRepo.findById(id);

        if (disco.isPresent()) {
            return new ResponseEntity<>(disco.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // Obtiene todos los discos asociados a un artista mediante su ID.
    // Retorna la lista de discos o HTTP 404 si el artista no existe.
    @GetMapping(value = "/artista/{id}/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) {

        if (!artistaRepo.existsById(id)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        List<Disco> discos = discoRepo.findDiscosByIdArtista(id);

        return new ResponseEntity<>(discos, HttpStatus.OK);
    }

}