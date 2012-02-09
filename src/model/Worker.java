package model;

import java.math.BigInteger;

import model.IWorker;

public class Worker implements IWorker {
	private BigInteger id = null;
	private String fname = null;
	private String lname = null;
	private BigInteger mgrid = null;
	private BigInteger depid = null;
	private BigInteger jobid = null;
	private BigInteger officeid = null;
	private int salegrade = 0;
	/**
	 * method return worker id.
	 */ 
	@Override
	public BigInteger getID() {
		return id;
	}
	
	@Override
	public BigInteger getDepartmentID() {
		return depid;
	}

	@Override
	public String getFirstName() {
		return fname;
	}

	@Override
	public BigInteger getJobID() {
		return jobid;
	}

	@Override
	public String getLastName() {
		return lname;
	}

	@Override
	public BigInteger getManagerID() {
		return mgrid;
	}

	@Override
	public BigInteger getOfficeID() {
		return officeid;
	}

	@Override
	public int getSalegrade() {
		return salegrade;
	}

	@Override
	public void setDepartmentID(BigInteger id) {
		depid = id;
	}

	@Override
	public void setFirstName(String name) {
		this.fname = name;
	}

	@Override
	public void setID(BigInteger id) {
		this.id = id;
	}

	@Override
	public void setJobID(BigInteger id) {
		this.jobid = id;
	}

	@Override
	public void setLastName(String name) {
		lname = name;
	}

	@Override
	public void setManagerID(BigInteger id) {
		this.mgrid = id;
	}

	@Override
	public void setOfficeID(BigInteger id) {
		this.officeid = id;
	}

	@Override
	public void setSalegrade(int sale) {
		this.salegrade = sale;
	}
}
