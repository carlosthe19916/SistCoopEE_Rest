package org.softgreen.sistcoop.socio.ejb.models.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.softgreen.sistcoop.socio.ejb.models.jpa.entities.enums.TipoCuentaBancaria;

@Entity
@Table(name = "PRODUCTO", indexes = { @Index(columnList = "id") })
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ProductoEntity {

	private Integer id;
	private TipoCuentaBancaria tipoCuentaBancaria;

	@Id
	@GeneratedValue(generator = "SgGenericGenerator")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
