/**
 * 
 */
package model;

import java.math.BigInteger;

public class Dept implements IDept {
	private BigInteger id = null;
	private String title = null;
	private String desc = null;
	/* (non-Javadoc)
	 * @see model.IDept#getID()
	 */
	@Override
	public BigInteger getID() {
		return id;
	}

	/* (non-Javadoc)
	 * @see model.IDept#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/* (non-Javadoc)
	 * @see model.IDept#getDescription()
	 */
	@Override
	public String getDescription() {
		return desc;
	}

	/* (non-Javadoc)
	 * @see model.IDept#setID(java.math.BigInteger)
	 */
	@Override
	public void setID(BigInteger id){
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see model.IDept#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/* (non-Javadoc)
	 * @see model.IDept#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String desc){
		this.desc = desc;
	}

}
