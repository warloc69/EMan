package processors;
import javax.servlet.http.*;
/**
* Interface presents access to the some objects that processes the request.
*/
public interface ActionProcessor {
    /**
	 * method processes the request from user and generate response
	 * @param req it's request
	 * @param resp it's response
	 */
	public void process(HttpServletRequest req, HttpServletResponse resp);
}