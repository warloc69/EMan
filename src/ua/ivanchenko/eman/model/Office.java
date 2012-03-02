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
    /**
     * Method returns office's identifier.
     */
    public BigInteger getID() {
        return id;
    }
    /**
     * method sets office's identifier
     * @param id inserting id.
     */
    public void setID(BigInteger id) {
        this.id = id;
    }
    /**
     * returns office's title.
     */
    public String getTitle() {
        return title;
    }
    /**
     * method sets office's title. 
     * @param title inserting title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Method returns address.
     */
    public String getAddress() {
        return address;
    }
    /**
     * method inserts into the object the address.
     * @param adr inserting address. 
     */
    public void setAddress(String address) {
        this.address = address;
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
    @Override
   	public int hashCode() {
   		return id.intValue();
   	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Office other = (Office) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mgrid == null) {
			if (other.mgrid != null)
				return false;
		} else if (!mgrid.equals(other.mgrid))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
   
}
