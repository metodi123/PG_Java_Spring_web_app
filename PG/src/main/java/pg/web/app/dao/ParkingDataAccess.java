package pg.web.app.dao;

import java.util.List;

import pg.web.app.model.Parking;

public interface ParkingDataAccess {
	public Parking getParking(int number) throws Exception;
	
	public List<Parking> getAllParkings() throws Exception;

	public void createParking(Parking parking) throws Exception;
	
	public void updateParking(Parking parking) throws Exception;
	
	public void deleteParking(int number) throws Exception;
}
