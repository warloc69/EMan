package ua.ivanchenko.eman.model;

import java.math.BigInteger;


public class Worker implements IWorker {
    private BigInteger id = null;
    private String fname = null;
    private String lname = null;
    private BigInteger mgrid = null;
    private BigInteger depid = null;
    private BigInteger jobid = null;
    private BigInteger officeid = null;
    private double salegrade = 0;
    public BigInteger getID() {
        return id;
    }
    public void setID(BigInteger id) {
        this.id = id;
    }
    public String getFirstName() {
        return fname;
    }
    public void setFirstName(String fname) {
        this.fname = fname;
    }
    public String getLastName() {
        return lname;
    }
    public void setLastName(String lname) {
        this.lname = lname;
    }
    public BigInteger getManagerID() {
        return mgrid;
    }
    public void setManagerID(BigInteger mgrid) {
        this.mgrid = mgrid;
    }
    public BigInteger getDepartmentID() {
        return depid;
    }
    public void setDepartmentID(BigInteger depid) {
        this.depid = depid;
    }
    public BigInteger getJobID() {
        return jobid;
    }
    public void setJobID(BigInteger jobid) {
        this.jobid = jobid;
    }
    public BigInteger getOfficeID() {
        return officeid;
    }
    public void setOfficeID(BigInteger officeid) {
        this.officeid = officeid;
    }
    public double getSalegrade() {
        return salegrade;
    }
    public void setSalegrade(double salegrade) {
        this.salegrade = salegrade;
    }
    @Override
	public int hashCode() {
		return id.intValue();
	}
}
