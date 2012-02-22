/**
 * 
 */
package ua.ivanchenko.eman.model;

import java.math.BigInteger;


public class Office implements IOffice {
    private BigInteger id = null;
    private String title = null;
    private String address = null;
    private BigInteger mgrid = null;
    public BigInteger getID() {
        return id;
    }
    public void setID(BigInteger id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public BigInteger getManagerID() {
        return mgrid;
    }
    public void setManagerID(BigInteger mgrid) {
        this.mgrid = mgrid;
    }
    @Override
	public int hashCode() {
		return id.intValue();
	}
}
