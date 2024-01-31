package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.Base;
import org.allivia.api.alliviaapi.services.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class BaseControllerImpl<E extends Base, S extends BaseServiceImpl<E, Long>> implements IBaseController<E, Long> {
    @Autowired
    protected S servicio;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findAll());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Error\": \""+getStackTrace(e)+"\"}");
        }
    }

    @GetMapping("/paged")
    public ResponseEntity<?> getAll(Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findAll(pageable));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Error\": \""+getStackTrace(e)+"\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findById(id));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"Error\": \""+getStackTrace(e)+"\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody E entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.save(entity));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"Error\": \""+getStackTrace(e)+"\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody E entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.update(id, entity));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \""+getStackTrace(e)+"\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(servicio.delete(id));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \""+e.toString()+"\"}");
        }
    }

    private static String getStackTrace(Exception ex) {
        StringBuffer sb = new StringBuffer(500);
        StackTraceElement[] st = ex.getStackTrace();
        sb.append(ex.toString() + " - " + ex.getClass().getName() + ": " + ex.getMessage() + "\n");
        for (int i = 0; i < st.length; i++) {
            sb.append("\t at " + st[i].toString() + "\n");
        }
        return sb.toString();
    }
}
