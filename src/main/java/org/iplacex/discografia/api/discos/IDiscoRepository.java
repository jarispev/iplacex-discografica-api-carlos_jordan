package org.iplacex.discografia.api.discos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface IDiscoRepository extends MongoRepository<Disco, String> {

    // Obtiene todos los discos asociados a un artista mediante su ID.
    @Query("{ 'idArtista': ?0 }")
    List<Disco> findDiscosByIdArtista(String idArtista);

}