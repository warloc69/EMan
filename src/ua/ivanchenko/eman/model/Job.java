
package ua.ivanchenko.eman.model;

import java.math.BigInteger;
public class Job implements IJob {
    private BigInteger id = null;
    private String title = null;
    private String desc = null;
    /**
     * Method returns job's identifier.
     */
    public BigInteger getID() {
        return id;
    }
    /**
     * method sets job's identifier
     * @param id inserting identifier.
     */
    public void setID(BigInteger id) {
        this.id = id;
    }
    /**
     * returns job's title.
     */
    public String getTitle() {
        return title;
    }
    /**
     * method sets job's title. 
     * @param title inserting title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * returns job's description.
     */
    public String getDescription() {
        return desc;
    }
    /**
     * method sets job's description. 
     * @param desc inserting description
     */
    public void setDescription(String desc) {
        this.desc = desc;
    }
    @Override
	public int hashCode() {
		return id.intValue();
	}

}
