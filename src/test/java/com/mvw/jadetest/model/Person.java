package com.mvw.jadetest.model;

public class Person {

	private Integer id;

	private String name;
	
	private Integer bb;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBb() {
		return bb;
	}

	public void setBb(Integer bb) {
		this.bb = bb;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", bb=" + bb + "]";
	}
}
