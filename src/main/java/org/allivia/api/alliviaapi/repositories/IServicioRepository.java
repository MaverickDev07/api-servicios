package org.allivia.api.alliviaapi.repositories;




import org.allivia.api.alliviaapi.entities.AppServicioEntity;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface IServicioRepository extends CrudRepository<AppServicioEntity,Long> {


}
