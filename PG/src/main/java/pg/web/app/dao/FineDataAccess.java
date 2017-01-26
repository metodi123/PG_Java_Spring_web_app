package pg.web.app.dao;

import java.util.List;

import pg.web.app.model.Fine;

public interface FineDataAccess {
	public Fine getFine(int id) throws Exception;
	
	public List<Fine> getAllFines() throws Exception;

	public void newFine(Fine fine) throws Exception;
	
	public void payFine(Fine fine) throws Exception;
}
