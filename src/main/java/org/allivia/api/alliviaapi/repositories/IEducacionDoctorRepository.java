package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppDoctoreducacionEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IEducacionDoctorRepository extends CrudRepository<AppDoctoreducacionEntity,Long> {

        List<AppDoctoreducacionEntity> findByIdDoctor(long idDoctor);
        void deleteByIdDoctor(long idDoctor);
}
