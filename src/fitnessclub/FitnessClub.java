package fitnessclub;

import fitnessclub.datalayer.DataGateway;
import fitnessclub.interfacelayer.AuthFrame;

/**
 *
 * @author Lera Rodina
 */
public class FitnessClub {

    public static void main(String[] args) {
        DataGateway.initdb();
        DataGateway.connect();
        
        
        AuthFrame af = new AuthFrame();
        af.setVisible(true);
    }
    
}
