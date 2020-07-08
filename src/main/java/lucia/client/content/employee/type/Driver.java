package main.java.lucia.client.content.employee.type;

/**
 * Represents a driver employee.
 */
public class Driver extends CashoutHolder{


    //TODO driver-specific functions
    public Driver(String name, int employeeID, String password, long pay, String displayName) {
        super(name, employeeID, password, pay, displayName);
    }

    /**
     * This is called when the employee's cashout is being created.
     * In this method, the cashout should be modified to fit the given
     * employee's specifications
     */
    @Override
    public void onCashoutCalc() {
        getCashout().setCollectDriverFees();
        //TODO dynamic driver tipout
        if(getCashout().getTotalTips() >= 500){
            getCashout().setTipoutAmount(500);
        }else{
            getCashout().setTipoutAmount(getCashout().getTotalTips());
        }
    }
}
