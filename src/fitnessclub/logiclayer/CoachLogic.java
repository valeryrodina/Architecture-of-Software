package fitnessclub.logiclayer;

import fitnessclub.datalayer.ApplicationGateway;
import fitnessclub.datalayer.CoachGateway;
import fitnessclub.entity.Coach;

/**
 *
 * @author Lera Rodina
 */
public class CoachLogic {
    ClientLogic cl = new ClientLogic();
    ApplicationGateway ag = new ApplicationGateway();
    CoachGateway cg = new CoachGateway();
    
    public Coach authCoachByPersonId(int personid){
        if(personid < 1){
            return null;
        }

        Coach c = new Coach();
        String tmp = cg.getCoachByPersonId(personid);
        
        if(tmp.isEmpty()){
            return null;
        }
        
        String[] tmpfields = tmp.split("\n");
        c.setCoach_id(Integer.parseInt(tmpfields[0]));
        c.setId(Integer.parseInt(tmpfields[2]));
        c.setForname(tmpfields[3]);
        c.setSurname(tmpfields[4]);
        
        return c;
    }
    
    public void acceptClientRequest(int personid){
        if(personid < 1){
            return;
        }
        
        int client_id = cl.getClientId(personid);
        
        ag.setAppStateByClient(client_id, 9);
    }
    
    public String getAllCoaches(){
        return cg.getAllCoaches();
    }
    
    public void addCoach(int personid){
        if(personid < 1){
            return;
        }
        
        cg.addCoach(personid);
    }
    
    public void createClientProgram(String programText){
        
    }
    
    public int getCoachId(int personid){
        if(personid < 1){
            return -1;
        }
        
        return cg.getCoachIdByPersonId(personid);
    }
}
