package pg.web.app.model;

public enum UserType {

	Employee, Admin;
	
	@Override
	public String toString(){
        switch(this){
        case Employee :
            return "employee";
        case Admin :
            return "admin";
		}
        return null;
    }
}