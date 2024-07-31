package model.entity.drone;



import controller.CellController;
import controller.DroneController;
import javafx.scene.input.KeyCode;
import model.Cell;
import model.entity.Entity;
import view.SelectableView;
import view.drone.DroneView;
import view.river.RiverView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Drone extends Entity {

    private Cell sourceCell = CellController.getInstance().getCellFrom(0,0);
    private Cell destinyCell = CellController.getInstance().getCellFrom(0,0);

    private String uniqueID;
    private Integer initialPosistionI, initialPositionJ;
    private Double initialBattery = 100.D;
    private Double batteryPerBlock = 1.D;
    private Double batteryPerSecond = 1.D;

    // Do changes in Runtime
    private Integer currentPositionI, currentPositionJ;
    private Double currentBattery = 100.D;
    private Boolean isStarted = false;

    private Boolean isTookOff = false;

    private Boolean landing = false;

    private Boolean isBadConnection = false;


    private Boolean isEconomyMode = false;

    private Boolean isSafeland = false;

    private Double distanceSource;
    private Double distanceDestiny;
    private Boolean isStrongWind =false;
    private Boolean isAutomatic = false;
    private Boolean isManual = true;
    private Boolean isGoingManualToDestiny = true;
    private Boolean isGoingAutomaticToDestiny = false;
    private Boolean isReturningToHome = false;

    private Boolean isLost = false;

    private Boolean selected = false;

    private Boolean returnBase = false;

    private Boolean seekConnection = false;

    private Boolean emergencyLanding = false;


    private boolean overrideGoDestinyAutomaticFlag = false;
    private boolean executeLanding = false;
    private boolean safeLanding = false;
    private boolean emergency = false;



    private List<SelectableView> onTopOfList = new ArrayList<>();
    private List<Listener> listeners = new ArrayList<>();
    private KeyCode directionCommand;

    public static int COUNT_DRONE = 1;

    private int wrapperId = 0;
    private String label = "";




    public Drone(String uniqueID, String label, Cell sourceCell) {
        this.uniqueID = uniqueID;
        this.currentPositionI = sourceCell.getRowPosition();
        this.currentPositionJ = sourceCell.getColumnPosition();

        this.initialPosistionI = sourceCell.getRowPosition();
        this.initialPositionJ = sourceCell.getColumnPosition();
        this.sourceCell = sourceCell;

        this.label = label;

        COUNT_DRONE++;
    }

    public static void restartCount() {
        COUNT_DRONE = 1;
    }

    public boolean isShutDown(){
        return !isStarted();
    }

    public boolean isNormalMode(){
        return !isEconomyMode();
    }

    public boolean isNormalConnection(){
        return !isBadConnection();
    }


/*
    public void setAspect(boolean isAspect) {

        boolean oldValue = this.getWrapperId;
        boolean newValue = isAspect;

        if(oldValue == newValue){
            return;
        }

        this.getWrapperId = isAspect;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);

    }
*/

    public Boolean isReturnBase() {
        return returnBase;
    }

    public void setReturnBase(Boolean returnBase) {
        Boolean oldValue = this.returnBase;
        Boolean newValue = returnBase;

        if (oldValue == newValue) {
            return;
        }

        this.returnBase = returnBase;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(), oldValue, newValue);
    }


    public Boolean isSeekConnection() {
        return seekConnection;
    }

    public void setSeekConnection(Boolean seekConnection) {
        Boolean oldValue = this.seekConnection;
        Boolean newValue = seekConnection;

        if (oldValue == newValue) {
            return;
        }

        this.seekConnection = seekConnection;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(), oldValue, newValue);
    }

    public Boolean getSeekConnection() {return this.seekConnection;}

    public Boolean getLanding() {
        return landing;
    }

    public void setLanding(Boolean landing) {
        boolean oldValue = this.landing;
        boolean newValue = landing;

        if(oldValue == newValue){
            return;
        }

        this.landing = landing;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public Boolean getLanded() {
        return !isTookOff;
    }


    public int getWrapperId() {
        return wrapperId;
    }

    public void setWrapperId(int wrapperId) {
        int oldValue = this.wrapperId;
        int newValue = wrapperId;

        if(oldValue == newValue){
            return;
        }

        this.wrapperId = wrapperId;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public void setIsSafeland(boolean isSafeland) {
        boolean oldValue = this.isSafeland;
        boolean newValue = isSafeland;

        if(oldValue == newValue){
            return;
        }

        this.isSafeland = isSafeland;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public void setIsManual(boolean isManual) {
        boolean oldValue = this.isManual;
        boolean newValue = isManual;

        if(oldValue == newValue){
            return;
        }

        this.isManual = isManual;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public void setReturningToHome(boolean isReturningToHome) {
        boolean oldValue = this.isReturningToHome;
        boolean newValue = isReturningToHome;

        if(oldValue == newValue){
            return;
        }

        this.isReturningToHome = isReturningToHome;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }



    public boolean isManual() {
        return isManual;
    }



    public void setGoingAutomaticToDestiny(boolean isGoingAutomaticToDestiny) {
        boolean oldValue = this.isGoingAutomaticToDestiny;
        boolean newValue = isGoingAutomaticToDestiny;

        if(oldValue == newValue){
            return;
        }

        this.isGoingAutomaticToDestiny = isGoingAutomaticToDestiny;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public Integer getInitialPosistionI() {
        return initialPosistionI;
    }

    public void setInitialPosistionI(Integer initialPosistionI) {
        Integer oldValue = this.initialPosistionI;
        Integer newValue = initialPosistionI;

        if(oldValue == newValue){
            return;
        }

        this.initialPosistionI = initialPosistionI;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public Integer getInitialPositionJ() {
        return initialPositionJ;
    }

     public void setInitialPositionJ(Integer initialPositionJ) {
         Integer oldValue = this.initialPositionJ;
         Integer newValue = initialPositionJ;

         if(oldValue == newValue){
             return;
         }

        this.initialPositionJ = initialPositionJ;

         notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public Double getInitialBattery() {
        return initialBattery;
    }

    public void setInitialBattery(Double initialBattery) {
        Double oldValue = this.initialBattery;
        Double newValue = initialBattery;

        if(oldValue == newValue){
            return;
        }

        this.initialBattery = initialBattery;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public boolean isStrongWind() {
        return isStrongWind;
    }

    public boolean isAutomatic() {
        return isAutomatic;
    }


    public Double getDistanceDestiny() {
        return distanceDestiny;
    }

    public void setDistanceDestiny(Double distanceDestiny) {
        Double oldValue = this.distanceDestiny;
        Double newValue = distanceDestiny;
        this.distanceDestiny = distanceDestiny;

        if(oldValue == newValue){
            return;
        }

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public void setDistanceSource(Double distanceSource) {
        Double oldValue = this.distanceSource;
        Double newValue = distanceSource;

        if(oldValue == newValue){
            return;
        }

        this.distanceSource = distanceSource;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public Double getDistanceSource() {
        return distanceSource;
    }

    public void setBatteryPerBlock(Double batteryPerBlock) {
        Double oldValue = this.batteryPerBlock;
        Double newValue = batteryPerBlock;

        this.batteryPerBlock = batteryPerBlock;

        if(oldValue == newValue){
            return;
        }

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public void setBatteryPerSecond(Double batteryPerSecond) {
        Double oldValue = this.batteryPerSecond;
        Double newValue = batteryPerSecond;

        if(oldValue == newValue){
            return;
        }

        this.batteryPerSecond = batteryPerSecond;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }


    public void setStrongWind(boolean isStrongWind) {
        boolean oldValue = this.isStrongWind;
        boolean newValue = isStrongWind;

        if(oldValue == newValue){
            return;
        }

        this.isStrongWind = isStrongWind;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public void setIsAutomatic(boolean isAutomatic) {
        boolean oldValue = this.isAutomatic;
        boolean newValue = isAutomatic;

        if(oldValue == newValue){
            return;
        }

        this.isAutomatic = isAutomatic;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }


    public boolean isGoingAutomaticToDestiny() {
        return isGoingAutomaticToDestiny;
    }

    public boolean isReturningToHome() {
        return isReturningToHome;
    }

    public boolean isSafeLand() {
        return isSafeland;
    }

    public Integer getCurrentPositionI() {
        return currentPositionI;
    }

    public void setCurrentPositionI(Integer currentPositionI) {
        int oldValue = this.currentPositionI;
        int newValue = currentPositionI;

        if(oldValue == newValue){
            return;
        }
        this.currentPositionI = currentPositionI;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
        updateItIsOver();
        checkAndNotifyOnWaterState();
    }

    public Integer getCurrentPositionJ() {
        return currentPositionJ;
    }

    public void setCurrentPositionJ(Integer currentPositionJ) {
        int oldValue = this.currentPositionJ;
        int newValue = currentPositionJ;

        if(oldValue == newValue){
            return;
        }

        this.currentPositionJ = currentPositionJ;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
        updateItIsOver();
        checkAndNotifyOnWaterState();
    }

    public List<SelectableView> getOnTopOfList() {
        return onTopOfList;
    }


    public void addOnTopOfDroneList(SelectableView onTopOf) {
        this.onTopOfList.add(onTopOf);
    }

    public synchronized void updateItIsOver() {
        DroneView droneView = DroneController.getInstance().getDroneViewFrom(this.getUniqueID());
        List<SelectableView> selectableViewList = CellController.getInstance().getOverSelectableView(droneView);
        this.setOnTopOfList(selectableViewList);
        checkAndNotifyOnWaterState(); // Check and notify water state
    }

    public Double getCurrentBattery() {
        return currentBattery;
    }

     public void setCurrentBattery(Double currentBattery) {
        double oldValue = this.currentBattery;
        double newValue = currentBattery;

         if(oldValue == newValue){
             return;
         }

        this.currentBattery = currentBattery;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);

    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean isStarted) {
        boolean oldValue = this.isStarted;
        boolean newValue = isStarted;

        if(oldValue == newValue){
            return;
        }

        this.isStarted = isStarted;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public boolean isTookOff() {
        return isTookOff;
    }

    public void setIsTookOff(boolean isTookOff) {
        boolean oldValue = this.isTookOff;
        boolean newValue = isTookOff;

        if(oldValue == newValue){
            return;
        }

        this.isTookOff = isTookOff;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        String oldValue = this.uniqueID;
        String newValue = uniqueID;

        if(oldValue == newValue){
            return;
        }

        this.uniqueID = uniqueID;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public Double getBatteryPerBlock() {
        return batteryPerBlock;
    }

    public Double getBatteryPerSecond() {
        return batteryPerSecond;
    }

    public boolean isEconomyMode() {
        return isEconomyMode;
    }

    public void setEconomyMode(boolean isEconomyMode) {
        boolean oldValue = this.isEconomyMode;
        boolean newValue = isEconomyMode;

        if(oldValue == newValue){
            return;
        }
        this.isEconomyMode = isEconomyMode;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);


    }

    public void setOnTopOfList(List<SelectableView> onTopOfList) {
        this.onTopOfList = onTopOfList;
    }

    public Boolean isBadConnection() {
        return isBadConnection;
    }

    public void setBadConnection(boolean isBadConnection) {
        boolean oldValue = this.isBadConnection;
        boolean newValue = isBadConnection;

        if(oldValue == newValue){
            return;
        }

        this.isBadConnection = isBadConnection;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public Cell getSourceCell() {
        return sourceCell;
    }

    public void setSourceCell(Cell sourceCell) {
        Cell oldValue = this.sourceCell;
        Cell newValue = sourceCell;

        this.sourceCell = sourceCell;

        if(oldValue == newValue){
            return;
        }

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public Cell getDestinyCell() {
        return destinyCell;
    }

    public void setDestinyCell(Cell destinyCell) {
        Cell oldValue = this.destinyCell;
        Cell newValue = destinyCell;

        this.destinyCell = destinyCell;

        if(oldValue == newValue){
            return;
        }

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }



    public boolean isGoingManualToDestiny() {
        return isGoingManualToDestiny;
    }

    public void setGoingManualToDestiny(boolean isGoingManualToDestiny) {
        boolean oldValue = this.isGoingManualToDestiny;
        boolean newValue = isGoingManualToDestiny;

        if(oldValue == newValue){
            return;
        }

        this.isGoingManualToDestiny = isGoingManualToDestiny;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }



    public boolean isOnWater(){

        if(onTopOfList.isEmpty()){
            return false;
        }
        for(Object object :onTopOfList){
            if(object instanceof RiverView){
                return true;
            }
        }

        return false;
    }

    public void checkAndNotifyOnWaterState() {
        boolean onWater = isOnWater();
        System.out.println("Old value: "+ !onWater + " New Value " + onWater);
        notifiesListeners("isOnWater", !onWater, onWater);
        //notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),!onWater, onWater);
    }

    public Boolean isLost() {
        return isLost;
    }

    public void setLost(Boolean isLost) {
        boolean oldValue = this.isLost;
        boolean newValue = isLost;

        if(oldValue == newValue){
            return;
        }

        this.isLost = isLost;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public KeyCode getFlyDirectionCommand() {
        return this.directionCommand;
    }

    public void setFlyDirectionCommand(KeyCode directionCommand){

        KeyCode oldValue = this.directionCommand;
        KeyCode newValue = directionCommand;


        this.directionCommand = directionCommand;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected) {
        boolean oldValue = this.selected;
        boolean newValue = selected;

        if(oldValue == newValue){
            return;
        }

        this.selected = selected;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public String getLabel() {
        return label;
    }

    public void setOverrideGoDestinyAutomaticFlag(boolean flag) {
        boolean oldValue = this.overrideGoDestinyAutomaticFlag;
        boolean newValue = flag;

        if(oldValue == newValue){
            return;
        }

        this.overrideGoDestinyAutomaticFlag = flag;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);

    }

    public Boolean getOverrideGoDestinyAutomaticFlag(){return this.overrideGoDestinyAutomaticFlag; }

    public void setExecuteLanding(boolean flag) {
        boolean oldValue = this.executeLanding;
        boolean newValue = flag;

        if(oldValue == newValue){
            return;
        }

        this.executeLanding = flag;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public Boolean getExecuteLanding() {return this.executeLanding;}

    public void setSafeLanding(boolean flag) {
        boolean oldValue = this.safeLanding;
        boolean newValue = flag;

        if(oldValue == newValue){
            return;
        }

        this.safeLanding = flag;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public Boolean getSafeLanding() {return this.safeLanding;}

    public void setEmergency(boolean flag) {
        boolean oldValue = this.emergency;
        boolean newValue = flag;

        if(oldValue == newValue){
            return;
        }

        this.emergency = flag;

        notifiesListeners(Thread.currentThread().getStackTrace()[1].getMethodName(),oldValue, newValue);
    }

    public Boolean getEmergency() {return this.emergency;}


    public interface Listener{
        public void onChange(Drone drone, String methodName, Object oldValue, Object newValue);
    }

    public List<Listener> getListeners() {
        return listeners;
    }

    public void setListeners(List<Listener> listeners) {
        this.listeners = listeners;
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    private void notifiesListeners(String attributeName, Object oldValue, Object newValue){

        synchronized (this){
            for (Listener listener : listeners){
                listener.onChange(this, attributeName, oldValue, newValue);
            }
        }

    }

   /*  private String getCurrentNameMethed(){
         String name = new Object(){}.getClass().getEnclosingMethod().getName();
         return name;
     }*/

    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }


}