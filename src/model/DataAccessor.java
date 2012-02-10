package model;

public class DataAccessor {
	private static IDataAccessor inst = null; 
	public static IDataAccessor getInstance() {
		if ( inst == null) {
			inst = new OracleDataAccessor();
		}
		return inst;
	}
}
