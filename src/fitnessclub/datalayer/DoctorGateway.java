package fitnessclub.datalayer;

/**
 *
 * @author Lera Rodina
 */
public class DoctorGateway {
    public String getDoctorByPersonId(int personid){
        if(personid < 1){
            return "";
        }
        
        return DataGateway.request("select * from doctor join person on person.id = doctor.person_id and person.id = " + personid);
    }
    
    public int getDoctorIdByPersonId(int personid){
        if(personid < 1){
            return -1;
        }
        
        String tmp = DataGateway.request("select id from doctor where person_id = " + personid);
        
        if(tmp.isEmpty()){
            return -2;
        }
        
        tmp = tmp.substring(0, tmp.length() - 1);
        
        int result = Integer.parseInt(tmp);
        
        return result;
    }
    
    public String getAllDoctors(){
        return DataGateway.request("select * from doctor join person on person.id = doctor.person_id");
    }
    
    public void addDoctor(int person_id){
        if(person_id < 1){
            return;
        }
        
        DataGateway.update("insert into doctor (person_id) values (" + person_id + ")");
    }
}
