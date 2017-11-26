package fitnessclub.entity;

/**
 *
 * @author Lera Rodina
 */
public class Manager extends Person{
    private int manager_id;
        
    public Manager(String forname, String surname, String login, String password) {
        super(forname, surname, login, password);
    }
    
    public Manager() {
        super("", "", "", "");
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }
    
}
