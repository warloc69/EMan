/**
 * 
 */
package ua.ivanchenko.eman.model;

import java.math.BigInteger;
public class Job implements IJob {
    private BigInteger id = null;
    private String title = null;
    private String desc = null;
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
    public String getDescription() {
        return desc;
    }
    public void setDescription(String desc) {
        this.desc = desc;
    }
    @Override
	public int hashCode() {
		return id.intValue();
	}

}
