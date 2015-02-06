package org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "TRABAJADOR", indexes = { @Index(columnList = "id") })
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@NamedQueries(value = {
		@NamedQuery(name = TrabajadorEntity.findByUsuario, query = "SELECT t FROM TrabajadorEntity t WHERE t.usuario = :usuario"),
		@NamedQuery(name = TrabajadorEntity.findByTipoAndNumeroDocumento, query = "SELECT t FROM TrabajadorEntity t WHERE t.tipoDocumento = :tipoDocumento AND t.numeroDocumento = :numeroDocumento "),
		@NamedQuery(name = TrabajadorEntity.findByFilterText, query = "SELECT t FROM TrabajadorEntity t WHERE t.numeroDocumento LIKE :filterText") })
public class TrabajadorEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String base = "org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TrabajadorEntity.";
	public final static String findByUsuario = base + "findByUsuario";
	public final static String findByTipoAndNumeroDocumento = base + "findByTipoAndNumeroDocumento";
	public final static String findByFilterText = base + "findByFilterText";
	
	private Integer id;
	private String tipoDocumento;
	private String numeroDocumento;
	private String usuario;
	private boolean estado;
	private AgenciaEntity agencia;

	private Timestamp optlk;

	private Set<TrabajadorCajaEntity> trabajadorCajas = new HashSet<TrabajadorCajaEntity>();
	
	public TrabajadorEntity() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(generator = "SgGenericGenerator")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	@Size(min = 1, max = 20)
	@NotEmpty
	@NotBlank
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	@NotNull
	@Size(min = 1, max = 20)
	@NotEmpty
	@NotBlank
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	@Size(min = 0, max = 40)
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@NotNull
	@Type(type = "org.hibernate.type.TrueFalseType")
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public AgenciaEntity getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaEntity agencia) {
		this.agencia = agencia;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trabajador")
	public Set<TrabajadorCajaEntity> getTrabajadorCajas() {
		return trabajadorCajas;
	}

	public void setTrabajadorCajas(Set<TrabajadorCajaEntity> trabajadorCajas) {
		this.trabajadorCajas = trabajadorCajas;
	}
	
	@XmlTransient
	@Version
	public Timestamp getOptlk() {
		return optlk;
	}

	public void setOptlk(Timestamp optlk) {
		this.optlk = optlk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TrabajadorEntity))
			return false;
		TrabajadorEntity other = (TrabajadorEntity) obj;
		if (numeroDocumento == null) {
			if (other.numeroDocumento != null)
				return false;
		} else if (!numeroDocumento.equals(other.numeroDocumento))
			return false;
		if (tipoDocumento == null) {
			if (other.tipoDocumento != null)
				return false;
		} else if (!tipoDocumento.equals(other.tipoDocumento))
			return false;
		return true;
	}

}
