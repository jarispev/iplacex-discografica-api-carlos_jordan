package org.iplacex.discografia.api.artistas;

import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepo;

    // Registra un nuevo artista en la base de datos MongoDB.
    // Retorna el artista creado con el estado HTTP 201 (Created).
    @PostMapping(value = "/artista", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista temp = artistaRepo.insert(artista);
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    // Obtiene todos los artistas registrados en la base de datos.
    // Retorna la lista de artistas o HTTP 404 si no existen registros.
    @GetMapping(value = "/artistas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {
        List<Artista> artistas = artistaRepo.findAll();

        if (artistas.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(artistas, HttpStatus.OK);
    }

    // Busca un artista en la base de datos mediante su ID.
    // Retorna el artista encontrado o HTTP 404 si no existe.
    @GetMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleGetArtistaByIdRequest(@PathVariable String id) {
        Optional<Artista> artista = artistaRepo.findById(id);

        if (artista.isPresent()) {
            return new ResponseEntity<>(artista.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // Actualiza los datos de un artista existente mediante su ID.
    // Retorna el artista actualizado o HTTP 404 si no existe.
    @PutMapping(value = "/artista/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleUpdateArtistaRequest(
            @PathVariable String id,
            @RequestBody Artista artista) {

        if (!artistaRepo.existsById(id)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        artista._id = id;
        Artista temp = artistaRepo.save(artista);

        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    // Elimina un artista de la base de datos mediante su ID.
    // Retorna HTTP 200 si se elimina o HTTP 404 si no existe.
    @DeleteMapping(value = "/artista/{id}")
    public ResponseEntity<Void> HandleDeleteArtistaRequest(@PathVariable String id) {

        if (!artistaRepo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        artistaRepo.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}