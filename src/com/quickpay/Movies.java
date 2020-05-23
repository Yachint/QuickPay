package com.quickpay;

public class Movies {
	private int Id;
	private String Name;
	private String Description;
	private String Date;
	private String Image;
	private int Tickets;
	private int Cost;
	
	public Movies(int id, String name, String description, String date, String image, int tickets, int cost) {
		super();
		Id = id;
		Name = name;
		Description = description;
		Date = date;
		Image = image;
		Tickets = tickets;
		Cost = cost;
	}
		
	public int getCost() {
		return Cost;
	}

	public void setCost(int cost) {
		Cost = cost;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public int getTickets() {
		return Tickets;
	}

	public void setTickets(int tickets) {
		Tickets = tickets;
	}
	
	
}
