package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.Base;
import org.allivia.api.alliviaapi.entities.BaseSimple;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.IBaseSimpleRepository;
import org.allivia.api.alliviaapi.services.IBaseSimpleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class BaseSimpleServiceImpl<E extends BaseSimple, ID extends Serializable> implements IBaseSimpleService<E, ID> {
    protected IBaseSimpleRepository<E, ID> baseRepository;

    public BaseSimpleServiceImpl(IBaseSimpleRepository<E, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    @Transactional
    public List<E> findAll() throws Exception {
        try {
            List<E> entities = baseRepository.findAll();
            return entities;
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Page<E> findAll(Pageable pageable) throws Exception {
        try {
            Page<E> entities = baseRepository.findAll(pageable);
            return entities;
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E findById(ID id) throws Exception {
        try {
            Optional<E> entityOptional = baseRepository.findById(id);
            return entityOptional.get();
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E save(E entity) throws Exception {
        try {
            System.out.println(entity.toString());
            entity = baseRepository.save(entity);
            return entity;
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E update(ID id, E entity) throws Exception {
        try {
            Optional<E> entityOptional = baseRepository.findById(id);
            E entityUpdate = entityOptional.get();
            entityUpdate = baseRepository.save(entity);
            return entityUpdate;
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(ID id) throws Exception {
        try {
            if (baseRepository.existsById(id)) {
                baseRepository.deleteById(id);
                return true;
            } else {
                throw new Exception();
            }
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
