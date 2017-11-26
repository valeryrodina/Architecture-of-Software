package fitnessclub.datalayer;

/**
 *
 * @author Lera Rodina
 */
public class ManagerGateway {
    public void addManager(int person_id){
        DataGateway.update("insert into manager (person_id) values (" + person_id + ")");
    }
    
    public String getManagerByPersonId(int personid){
        if(personid < 1){
            return "";
        }
        
        return DataGateway.request("select * from manager join person on person.id = manager.person_id and person.id = " + personid);
    }
    
    public String getAllManagers(){
        return DataGateway.request("select * from manager join person on person.id = manager.person_id");
    }
}
