package com.example.QuanLyPhongTro.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Invoices")
public class Invoices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double total;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

	@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_room")
    private Rooms room;

	@JsonProperty("id_room")
	public Integer getIdUser() {
		return room != null ? room.getId() : null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Rooms getRoom() {
		return room;
	}

	public void setRoom(Rooms room) {
		this.room = room;
	}

    // Getters and Setters
    
}
