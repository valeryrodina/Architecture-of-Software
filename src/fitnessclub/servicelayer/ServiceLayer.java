package fitnessclub.servicelayer;

import fitnessclub.Util;
import fitnessclub.datalayer.ApplicationGateway;
import fitnessclub.entity.Client;
import fitnessclub.entity.Coach;
import fitnessclub.entity.Doctor;
import fitnessclub.entity.Manager;
import fitnessclub.logiclayer.ClientLogic;
import fitnessclub.logiclayer.CoachClientsLogic;
import fitnessclub.logiclayer.CoachLogic;
import fitnessclub.logiclayer.DoctorClientsLogic;
import fitnessclub.logiclayer.DoctorLogic;
import fitnessclub.logiclayer.ManagerLogic;
import fitnessclub.logiclayer.PersonLogic;
import java.sql.Date;
import java.time.LocalDate;
import java.util.StringTokenizer;
import javax.swing.DefaultListModel;

/**
 *
 * @author Lera Rodina
 */
public class ServiceLayer {
    public PersonLogic pl; 
    public ClientLogic cl;
    public DoctorLogic dl;
    public ManagerLogic ml;
    public CoachLogic col;
    public ApplicationGateway ag;
    public CoachClientsLogic ccl;
    public DoctorClientsLogic dcl;

    public ServiceLayer() {
        this.pl = new PersonLogic();
        this.cl = new ClientLogic();
        this.col = new CoachLogic();
        this.ml = new ManagerLogic();
        this.dl = new DoctorLogic();
        this.ag = new ApplicationGateway();
        this.ccl = new CoachClientsLogic();
        this.dcl = new DoctorClientsLogic();
    }
    
    public Object checkAuth(String login, String password){
        int personid = pl.authPerson(login, password);
        
        if((personid == -1) || (personid == -2) || (personid == -3)){
            return (Integer)personid;
        }
        
        pl.logOn(personid);
        
        if(cl.authClientByPersonId(personid) != null){
            return cl.authClientByPersonId(personid);
        }else if(col.authCoachByPersonId(personid) != null){
            return col.authCoachByPersonId(personid);
        }else if(dl.authDoctorByPersonId(personid) != null){
            return dl.authDoctorByPersonId(personid);
        }else if(ml.authManagerByPersonId(personid) != null){
            return ml.authManagerByPersonId(personid);
        }else{
            return (Integer)(-2); // no user in db
        }
    }
    
    public int createNewClient(String forname, String surname, String login, String password){
        if(forname.isEmpty() || surname.isEmpty() || login.isEmpty() || password.isEmpty()){
            return -1;
        }
        
        if(pl.getPersonIdByLogin(login) > 0){
            return -2;
        }
        
        pl.addPerson(forname, surname, login, password);
        
        int personid = pl.getPersonIdByLogin(login);
        if(personid == -1){
            return -1;
        }
        
        cl.addClient(personid, Date.valueOf(LocalDate.now()));
        int clientid = cl.getClientId(personid);
        ag.addApplication(clientid, 1, Date.valueOf(LocalDate.now()), "");
        
        return 0;
    }
    
    public int createNewCoach(String forname, String surname, String login, String password){
        if(forname.isEmpty() || surname.isEmpty() || login.isEmpty() || password.isEmpty()){
            return -1;
        }
        
        if(pl.getPersonIdByLogin(login) > 0){
            return -2;
        }
        
        pl.addPerson(forname, surname, login, password);
        
        int personid = pl.getPersonIdByLogin(login);
        
        if(personid == -1){
            return -1;
        }
        
        col.addCoach(personid);
        
        return 0;
    }
    
    public int createNewDoctor(String forname, String surname, String login, String password){
        if(forname.isEmpty() || surname.isEmpty() || login.isEmpty() || password.isEmpty()){
            return -1;
        }
        
        if(pl.getPersonIdByLogin(login) > 0){
            return -2;
        }
        
        pl.addPerson(forname, surname, login, password);
        
        int personid = pl.getPersonIdByLogin(login);
        
        if(personid == -1){
            return -1;
        }
        
        dl.addDoctor(personid);
        
        return 0;
    } 
    
    public int createNewManager(String forname, String surname, String login, String password){
        if(forname.isEmpty() || surname.isEmpty() || login.isEmpty() || password.isEmpty()){
            return -1;
        }
        
        if(pl.getPersonIdByLogin(login) > 0){
            return -2;
        }
        
        pl.addPerson(forname, surname, login, password);
        
        int personid = pl.getPersonIdByLogin(login);
        
        if(personid == -1){
            return -1;
        }
        
        ml.addManager(personid);
        
        return 0;
    }
    
    public DefaultListModel<String> parseAllStaff(){
        DefaultListModel<String> dlm = new DefaultListModel<>();
        String tmp;

        String str = col.getAllCoaches();
        StringTokenizer stkn = new StringTokenizer(str, "\n");

        while (stkn.hasMoreTokens()) {
            stkn.nextToken();
            tmp = "Coach: " + stkn.nextToken() + " ";
            stkn.nextToken();
            tmp = tmp + stkn.nextToken() + " " + stkn.nextToken();
            dlm.addElement(tmp);
            stkn.nextToken();
            stkn.nextToken();
            stkn.nextToken();
        }

        str = dl.getAllDoctors();
        stkn = new StringTokenizer(str, "\n");

        while (stkn.hasMoreTokens()) {
            tmp = "Doctor: " + stkn.nextToken() + " ";
            stkn.nextToken();
            stkn.nextToken();
            tmp = tmp + stkn.nextToken() + " " + stkn.nextToken();
            dlm.addElement(tmp);
            stkn.nextToken();
            stkn.nextToken();
            stkn.nextToken();
        }
        
        str = ml.getAllManagers();
        stkn = new StringTokenizer(str, "\n");

        while (stkn.hasMoreTokens()) {
            tmp = "Manager: " + stkn.nextToken() + " ";
            stkn.nextToken();
            stkn.nextToken();
            tmp = tmp + stkn.nextToken() + " " + stkn.nextToken();
            dlm.addElement(tmp);
            stkn.nextToken();
            stkn.nextToken();
            stkn.nextToken();
        }
        
        return dlm;
    }
    
    public DefaultListModel<String> parseAllCoaches(){
        DefaultListModel<String> dlm = new DefaultListModel<>();
        String tmp;

        String str = col.getAllCoaches();
        StringTokenizer stkn = new StringTokenizer(str, "\n");

        while (stkn.hasMoreTokens()) {
            stkn.nextToken();
            tmp = stkn.nextToken() + " ";
            stkn.nextToken();
            tmp = tmp + stkn.nextToken() + " " + stkn.nextToken();
            dlm.addElement(tmp);
            stkn.nextToken();
            stkn.nextToken();
            stkn.nextToken();
        }
        
        return dlm;
    }
    
    public DefaultListModel<String> parseAllDoctors(){
        DefaultListModel<String> dlm = new DefaultListModel<>();
        String tmp;

        String str = dl.getAllDoctors();
        StringTokenizer stkn = new StringTokenizer(str, "\n");

        while (stkn.hasMoreTokens()) {
            stkn.nextToken();
            tmp = stkn.nextToken() + " ";
            stkn.nextToken();
            tmp = tmp + stkn.nextToken() + " " + stkn.nextToken();
            dlm.addElement(tmp);
            stkn.nextToken();
            stkn.nextToken();
            stkn.nextToken();
        }
        
        return dlm;
    }
    
    public DefaultListModel<String> getCoachClients(int coach_id){
        DefaultListModel<String> dlm = new DefaultListModel<>();
        String tmp;

        String str = ccl.getCoachClients(coach_id);
        StringTokenizer stkn = new StringTokenizer(str, "\n");

        while (stkn.hasMoreTokens()) {
            tmp = stkn.nextToken(); //person_id to string
            
            int tmppersonid = Integer.parseInt(tmp);
            int tmpclientid = cl.getClientId(tmppersonid);
            String tmpstr = ag.getAppStateByClientId(tmpclientid);
            tmpstr = tmpstr.substring(0, tmpstr.length() - 1);
            int tmpappstate = Integer.parseInt(tmpstr);
            if(tmpappstate == 8){
                tmp = "NEW " + tmp;
            }
            
            tmp = tmp + " " + stkn.nextToken() + " " + stkn.nextToken(); // + name + surname to string
            dlm.addElement(tmp);
        }
        
        return dlm;
    }
    
    public DefaultListModel<String> getDoctorClients(int doctor_id){
        DefaultListModel<String> dlm = new DefaultListModel<>();
        String tmp;

        String str = dcl.getDoctorClients(doctor_id);
        StringTokenizer stkn = new StringTokenizer(str, "\n");

        while (stkn.hasMoreTokens()) {
            tmp = stkn.nextToken() + " " + stkn.nextToken() + " " + stkn.nextToken();
            dlm.addElement(tmp);
        }
        
        return dlm;
    }
    
    public int checkAppState(int person_id){
        if(person_id < 1){
            return -1;
        }
        
        int tmpclientid = cl.getClientId(person_id);
        
        String tmpstr = ag.getAppStateByClientId(tmpclientid);
        tmpstr = tmpstr.substring(0, tmpstr.length() - 1);
        int tmpappstate = Integer.parseInt(tmpstr);
        
        return tmpappstate;
    }
    
    public void doctorCheckedClient(int person_id){
        dl.checkClient(person_id);
    }
    
    public DefaultListModel<String> parseAllClients() {
        DefaultListModel<String> dlm = new DefaultListModel<>();
        String tmp;

        String str = cl.getAllClients();
        StringTokenizer stkn = new StringTokenizer(str, "\n");

        while (stkn.hasMoreTokens()) {
            stkn.nextToken();
            tmp = stkn.nextToken() + " ";
            stkn.nextToken();
            stkn.nextToken();
            tmp = tmp + stkn.nextToken() + " " + stkn.nextToken();
            dlm.addElement(tmp);
            stkn.nextToken();
            stkn.nextToken();
            stkn.nextToken();
        }

        return dlm;
    }
    
    public void assignClientToCoach(int client_id, int coach_id){
        if((client_id < 1) || (coach_id < 1)){
            return;
        }
        
        ccl.assignClientToCoach(client_id, coach_id);
    }
    
    public void coachAcceptsClientRequest(int person_id){
        col.acceptClientRequest(person_id);
        
        int client_id = cl.getClientId(person_id);
        ag.setAppStateByClient(client_id, 9);
    }
    
    public void coachSendsProgram(String text, int personid){
        if(text.isEmpty() || (personid < 1)){
            return;
        }
        
        int tmpclientid = cl.getClientId(personid);
        
        ag.setAppStateByClient(tmpclientid, 11);
        ag.changeAppText(tmpclientid, text);
    }
    
    public Coach getClientsCoach(int client_id){
        if(client_id < 1){
            return null;
        }
        
        String tmp = ccl.getClientsCoach(client_id);
        
        Coach c = new Coach();
        if(!tmp.isEmpty()){
            String[] tmpsplit = tmp.split("\n");
            
            c.setId(Integer.parseInt(tmpsplit[0]));
            c.setForname(tmpsplit[1]);
            c.setSurname(tmpsplit[2]);
        }else{
            c.setForname("No");
            c.setSurname("Coach");
        }
        
        return c;
    }
    
    public void clientAcceptsContract(int person_id){
        cl.acceptContract(cl.getClientId(person_id));
    }
    
    public void clientFillsForm(String formText){
        int client_id = cl.getClientId(Util.currPersonOnline);
        
        ag.changeAppText(client_id, formText);
        ag.setAppStateByClient(client_id, 10);
    }
    
    public void offerContractToClient(int client_id){
        if(client_id < 1)
        {
            return;
        }
        
        ag.setAppStateByClient(client_id, 5);
    }
    
    public void approveClientContract(int client_id){
        if(client_id < 1)
        {
            return;
        }
        
        ag.setAppStateByClient(client_id, 7);
    }
    
    public void addClientToDoctor(int doctor_id, int client_id){
        if((doctor_id < 1) || (client_id < 1)){
            return;
        }
        
        dcl.addClientToDoctor(doctor_id, client_id);
        
        ag.setAppStateByClient(client_id, 3);
    }
    
    public String getProgramText(int client_id){
        if(client_id < 1){
            return "";
        }
        
        return ag.getAppTextByClient(client_id);
    }
    
    public void clientChangesCoach(int client_id, int coach_id){
        if((coach_id < 1) || (client_id < 1)){
            return;
        }
        
        ccl.deleteClientFromCoach(client_id);
        ccl.assignClientToCoach(client_id, coach_id);
        ag.setAppStateByClient(client_id, 8);
    }
}
