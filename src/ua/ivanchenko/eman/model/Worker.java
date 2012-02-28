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
    /**
     * method return worker id.
     */
    public BigInteger getID() {
        return id;
    }
    /**
     * method sets worker's identifier
     * @param id inserting identifier.
     */
    public void setID(BigInteger id) {
        this.id = id;
    }
    /**
     * method returns worker's first name.
     */
    public String getFirstName() {
        return fname;
    }
    /**
     * method inserts worker's first name.
     * @param name is worker's first name.
     */
    public void setFirstName(String fname) {
        this.fname = fname;
    }
    /**
     * method returns worker's last name.
     */
    public String getLastName() {
        return lname;
    }
    /**
     * method inserts worker's last name.
     * @param name is worker's last name.
     */
    public void setLastName(String lname) {
        this.lname = lname;
    }
    /**
     * Method returns manager's identifier.
     */
    public BigInteger getManagerID() {
        return mgrid;
    }
    /**
     * method sets manager's identifier 
     * @param id inserting id.
     */
    public void setManagerID(BigInteger mgrid) {
        this.mgrid = mgrid;
    }
    /**
     * Method returns department's identifier.
     */
    public BigInteger getDepartmentID() {
        return depid;
    }
    /**
     * method sets department's identifier
     * @param id inserting identifier.
     */
    public void setDepartmentID(BigInteger depid) {
        this.depid = depid;
    }
    /**
     * Method returns job's identifier.
     */
    public BigInteger getJobID() {
        return jobid;
    }
    /**
     * method sets job's identifier
     * @param id inserting identifier.
     */
    public void setJobID(BigInteger jobid) {
        this.jobid = jobid;
    }
    /**
     * Method returns office's identifier.
     */
    public BigInteger getOfficeID() {
        return officeid;
    }
    /**
     * method sets office's identifier
     * @param id inserting id.
     */
    public void setOfficeID(BigInteger officeid) {
        this.officeid = officeid;
    }
    /**
     * method returns worker's salegrade.
     */
    public double getSalegrade() {
        return salegrade;
    }
    /**
     * method sets worker's salegrade.
     * @param sale is worker's salegrade.
     */
    public void setSalegrade(double salegrade) {
        this.salegrade = salegrade;
    }
    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Worker other = (Worker) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    @Override
   	public int hashCode() {
   		return id.intValue();
   	}
    
}
