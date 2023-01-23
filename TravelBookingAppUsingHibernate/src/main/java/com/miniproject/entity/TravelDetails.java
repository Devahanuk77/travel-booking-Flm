package com.miniproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="travel_details")
public class TravelDetails  {
	@Id
	@Column(name="serial_no")
	private int number;
	private  String source;
	private  String destination;
	@Column(name="ticket_price")
	private  double ticketPrice;
	
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public  double getTicketPrice() {
		return ticketPrice;
	}
	public  void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public TravelDetails() {
	}
	@Override
	public String toString() {
		return "TravelDetails [number=" + number + ", source=" + source + ", destination=" + destination
				+ ", ticketPrice=" + ticketPrice + "]";
	}

}
