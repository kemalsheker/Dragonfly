package jettyDeneme;

import com.google.gson.Gson;
import model.entity.drone.Drone;
import model.entity.drone.DroneBusinessObject;

import static sun.plugin2.util.PojoUtil.toJson;

public class DroneData implements Drone.Listener{

    private Boolean isAutomatic;
    private Boolean goodConnection;
    private Boolean badConnection;
    private Boolean isOnWater;
    private Boolean isStrongWind;
    private String currentPosition;
    private Double distanceToSource;
    private Double distanceToTarget;
    private Double initialBattery;
    private Double currentBattery;
    private Double consumptionPerSecond;
    private Double consumptionPerBlock;

    private String id;


    public DroneData(Drone drone) {

        this.id = drone.getUniqueID();

        this.isAutomatic = drone.isAutomatic();

        if (drone.isBadConnection()) {
            this.goodConnection = false;
            this.badConnection = true;
        } else {
            this.goodConnection = true;
            this.badConnection = false;
        }

        this.isOnWater = drone.isOnWater();
        this.isStrongWind = drone.isStrongWind();
        this.currentPosition = drone.getCurrentPositionI() + "," + drone.getCurrentPositionJ();
        this.distanceToSource = drone.getDistanceSource();
        this.distanceToTarget = drone.getDistanceDestiny();
        this.initialBattery = drone.getInitialBattery();
        this.currentBattery = drone.getCurrentBattery();
        this.consumptionPerSecond = drone.getBatteryPerSecond();
        this.consumptionPerBlock = drone.getBatteryPerBlock();
    }

    public void setUniqueID(String uniqueID) {
        this.id = uniqueID;
    }

    public String getUniqueID(){
        return id;
    }

    public Boolean getAutomatic() {
        return isAutomatic;
    }

    public void setAutomatic(Boolean automatic) {
        isAutomatic = automatic;
    }

    public Boolean getGoodConnection() {
        return goodConnection;
    }

    public void setGoodConnection(Boolean goodConnection) {
        this.goodConnection = goodConnection;
    }

    public Boolean getBadConnection() {
        return badConnection;
    }

    public void setBadConnection(Boolean badConnection) {
        this.badConnection = badConnection;
    }

    public Boolean getOnWater() {
        return isOnWater;
    }

    public void setOnWater(Boolean onWater) {
        isOnWater = onWater;
    }

    public Boolean getStrongWind() {
        return isStrongWind;
    }

    public void setStrongWind(Boolean strongWind) {
        this.isStrongWind = strongWind;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Double getDistanceToSource() {
        return distanceToSource;
    }

    public void setDistanceToSource(Double distanceToSource) {
        this.distanceToSource = distanceToSource;
    }

    public Double getDistanceToTarget() {
        return distanceToTarget;
    }

    public void setDistanceToTarget(Double distanceToTarget) {
        this.distanceToTarget = distanceToTarget;
    }

    public Double getInitialBattery() {
        return initialBattery;
    }

    public void setInitialBattery(Double initialBattery) {
        this.initialBattery = initialBattery;
    }

    public Double getCurrentBattery() {
        return currentBattery;
    }

    public void setCurrentBattery(Double currentBattery) {
        this.currentBattery = currentBattery;
    }

    public Double getConsumptionPerSecond() {
        return consumptionPerSecond;
    }

    public void setConsumptionPerSecond(Double consumptionPerSecond) {
        this.consumptionPerSecond = consumptionPerSecond;
    }

    public Double getConsumptionPerBlock() {
        return consumptionPerBlock;
    }

    public void setConsumptionPerBlock(Double consumptionPerBlock) {
        this.consumptionPerBlock = consumptionPerBlock;
    }



    // Implement the Drone.Listener interface
    @Override
    public void onChange(Drone drone, String methodName, Object oldValue, Object newValue) {
        switch (methodName) {
            case "setIsAutomatic":
                setAutomatic((Boolean) newValue);
                System.out.println("Is change happens" + "Old: " + oldValue + "New:" + newValue);
                break;
            case "setBadConnection":
                setBadConnection((Boolean) newValue);
                setGoodConnection(!(Boolean) newValue);
                System.out.println("Is change happens" + "Old: " + oldValue + "New:" + newValue);
                break;
            case "setCurrentPositionI":
            case "setCurrentPositionJ":
                setCurrentPosition(drone.getCurrentPositionI() + "," + drone.getCurrentPositionJ());
                DroneWebSocket.broadcastDroneData(this);
                DroneBusinessObject.updateItIsOver(drone);
                System.out.println("Is change happens" + "Old: " + oldValue + "New:" + newValue);
                break;
            case "setDistanceSource":
                setDistanceToSource((Double) newValue);
                System.out.println("Is change happens" + "Old: " + oldValue + "New:" + newValue);
                break;
            case "setDistanceDestiny":
                setDistanceToTarget((Double) newValue);
                System.out.println("Is change happens" + "Old: " + oldValue + "New:" + newValue);
                break;
            case "setInitialBattery":
                setInitialBattery((Double) newValue);
                System.out.println("Is change happens" + "Old: " + oldValue + "New:" + newValue);
                break;
            case "setCurrentBattery":
                setCurrentBattery((Double) newValue);
                System.out.println("Is change happens" + "Old: " + oldValue + "New:" + newValue);
                break;
            case "setBatteryPerSecond":
                setConsumptionPerSecond((Double) newValue);
                System.out.println("Is change happens" + "Old: " + oldValue + "New:" + newValue);
                break;
            case "setBatteryPerBlock":
                setConsumptionPerBlock((Double) newValue);
                break;
            case "isOnWater":
                setOnWater((Boolean) newValue);
                System.out.println("Is change happens" + "Old: " + oldValue + "New:" + newValue);
                break;
            case "setStrongWind":
                setStrongWind(drone.isStrongWind());
                break;
            default:
                // Handle other changes if needed
                break;
        }

        //DroneWebSocket.broadcastDroneData(this);
    }





}
