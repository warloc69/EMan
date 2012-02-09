/**
 * 
 */
package model;

import java.math.BigInteger;
public class Job implements IJob {
	private BigInteger id = null;
	private String title = null;
	private String desc = null;

	/* (non-Javadoc)
	 * @see model.IJob#getID()
	 */
	@Override
	public BigInteger getID() {
		return id;
	}

	/* (non-Javadoc)
	 * @see model.IJob#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/* (non-Javadoc)
	 * @see model.IJob#getDescription()
	 */
	@Override
	public String getDescription() {
		return desc;
	}

	/* (non-Javadoc)
	 * @see model.IJob#setID(java.math.BigInteger)
	 */
	@Override
	public void setID(BigInteger id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see model.IJob#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title){
		this.title = title;
	}

	/* (non-Javadoc)
	 * @see model.IJob#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String desc) {
		this.desc = desc;
	}

}
