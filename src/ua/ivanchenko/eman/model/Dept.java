/**
 * 
 */
package ua.ivanchenko.eman.model;

import java.math.BigInteger;
/**
* Class implements IDept
*/
public class Dept implements IDept {
    private BigInteger id = null;
    private String title = null;
    private String desc = null;
    /**
     * Method returns department's identifier.
     */
    public BigInteger getID() {
        return id;
    }
    /**
     * method sets department's identifier
     * @param id inserting identifier.
     */
    public void setID(BigInteger id) {
        this.id = id;
    }
    /**
     * returns department's title.
     */
    public String getTitle() {
        return title;
    }
    /**
     * method sets department's title. 
     * @param title inserting title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * returns department's description.
     */
    public String getDescription() {
        return desc;
    }
    /**
     * method sets department's description. 
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
