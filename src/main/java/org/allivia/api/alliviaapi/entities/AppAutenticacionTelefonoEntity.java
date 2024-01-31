package org.allivia.api.alliviaapi.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "app_autenticaciontelefono", schema = "public")
public class AppAutenticacionTelefonoEntity {

	@Id
	@SequenceGenerator(name = "APP_AUTENTICACIONTELEFONO_GENERATOR", sequenceName = "APP_AUTENTICACIONTELEFONO_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_AUTENTICACIONTELEFONO_GENERATOR")
	@Column(name = "id")
	private Long id;
	@Column(name = "nro_telefono", unique = true)
	private String nroTelefono;
	@Column(length = 4)
	private String codigo;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	@Column(name = "fecha_modificacion")
	private Timestamp fechaModificacion;

	public static AppAutenticacionTelefonoEntity getEntity(Long id, String nroTelefono, String codigo) {
		AppAutenticacionTelefonoEntity authTelf = new AppAutenticacionTelefonoEntity();
		authTelf.setCodigo(codigo);
		authTelf.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
		authTelf.setNroTelefono(nroTelefono);
		return authTelf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNroTelefono() {
		return nroTelefono;
	}

	public void setNroTelefono(String nroTelefono) {
		this.nroTelefono = nroTelefono;
	}

	public Timestamp getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
