package controller;

import javafx.scene.input.KeyEvent;
import jettyDeneme.DroneData;
import jettyDeneme.DroneWebSocket;
import model.Cell;
import model.entity.Hospital;
import model.entity.drone.Drone;
import view.CellView;
import view.SelectableView;
import view.antenna.AntennaView;
import view.drone.DroneView;
import java.util.HashMap;
import java.util.Map;



public abstract class DroneController {
    protected static DroneController instance;

    protected static Map<String, DroneView> droneViewMap = new HashMap<>();
    protected static Map<String, Drone>  droneMap = new HashMap<>();

    protected static Map<String, DroneData> dataMap = new HashMap<>();

    public static DroneController getInstance(){
        return instance;
    }

    public boolean overrideGoDestinyAutomaticFlag = false;

    public boolean executeLanding = false;

    public boolean safeLanding = false;


    public static void init(String nameClass){

        if(instance == null || !instance.getClass().getSimpleName().equals(nameClass)){
            if(nameClass.equals(DroneKeyBoardController.class.getSimpleName())){
                instance = DroneKeyBoardController.getInstance();

            }

            if(nameClass.equals(DroneAutomaticController.class.getSimpleName())){
                instance = DroneAutomaticController.getInstance();
            }
        }

    }

    public static void consumeCleanEnvironment() {
        droneMap.clear();
        droneViewMap.clear();
        dataMap.clear();
        Drone.restartCount();
    }


    /*  abstract public void notifyChangeInModel();*/

    abstract public void consumeRunEnviroment();

    abstract public void consumeBadConnection(SelectableView selectableView);


    abstract void notifyStopEnviroment();

    public abstract void consumeNormalConnection(SelectableView selectableView);

    abstract public void consumeStrongWind();

    abstract public void consumeNormalWind();

    abstract public void consumeReset();

    abstract public void startUpdateBatteryPerSeconds();


    public abstract Drone createDrone(String uniqueID, String droneLabel,CellView currentCellView);

    public void deleteDrone(Drone drone){
        droneMap.remove(drone.getUniqueID());
        dataMap.remove(drone.getUniqueID());
        DroneView droneView = droneViewMap.remove(drone.getUniqueID());
        droneView.getCurrentCellView().getChildren().remove(droneView);
    }

    public Map<String, DroneView> getDroneViewMap() {
        return droneViewMap;
    }

    public void setDroneViewMap(Map<String, DroneView> droneViewMap) {
        this.droneViewMap = droneViewMap;
    }

    public Map<String, Drone> getDroneMap() {
        return droneMap;
    }

    public void setDroneMap(Map<String, Drone> droneMap) {
        this.droneMap = droneMap;
    }

    public Map<String, DroneData> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, DroneData> dataMap) {
        this.dataMap = dataMap;
    }

    public abstract DroneView getDroneViewFrom(String identifierDrone);
    public abstract Drone getDroneFrom(String identifierDrone);



    public void consumeClickEvent(SelectableView selectedEntityView) {
        if(selectedEntityView instanceof DroneView){
            Drone drone =  getDroneFrom(selectedEntityView.getUniqueID());
            drone.setSelected(true);
        }
    }
    public abstract void consumeOnKeyPressed(SelectableView selectedEntityView, KeyEvent keyEvent);

    public  void cleanSelections(){
        for(Drone drone : droneMap.values()){
            drone.setSelected(false);
        }
    }


    public void consumeSaveAttributesDrone(DroneView droneView) {

    }

    public void overrideGoDestinyAutomatic(Boolean override) {
        if (Boolean.TRUE.equals(override)) {
            this.overrideGoDestinyAutomaticFlag = true;
        } else {
            this.overrideGoDestinyAutomaticFlag = false;
        }
    }


    public void setExecuteLanding(Boolean l){
        if(Boolean.TRUE.equals(l)){
            this.executeLanding = true;
        }
        else{
            this.executeLanding = false;
        }
    }


    public void setSafeLanding(Boolean s){
        if(Boolean.TRUE.equals(s)){
            this.safeLanding = true;
        }
        else{
            this.safeLanding = false;
        }
    }


}
