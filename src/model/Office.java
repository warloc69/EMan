/**
 * 
 */
package model;

import java.math.BigInteger;


public class Office implements IOffice {
	private BigInteger id = null;
	private String title = null;
	private String address = null;
	private BigInteger mgrid = null;
	/* (non-Javadoc)
	 * @see model.IOffice#getAddress()
	 */
	@Override
	public String getAddress() {
		return address;
	}

	/* (non-Javadoc)
	 * @see model.IOffice#getID()
	 */
	@Override
	public BigInteger getID() {
		return id;
	}

	/* (non-Javadoc)
	 * @see model.IOffice#getManagerID()
	 */
	@Override
	public BigInteger getManagerID() {
		return mgrid;
	}

	/* (non-Javadoc)
	 * @see model.IOffice#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/* (non-Javadoc)
	 * @see model.IOffice#setAddress(java.lang.String)
	 */
	@Override
	public void setAddress(String adr) {
		address = adr;
	}

	/* (non-Javadoc)
	 * @see model.IOffice#setID(java.math.BigInteger)
	 */
	@Override
	public void setID(BigInteger id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see model.IOffice#setManagerID(java.math.BigInteger)
	 */
	@Override
	public void setManagerID(BigInteger id) {
		this.mgrid = id;
	}

	/* (non-Javadoc)
	 * @see model.IOffice#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

}
