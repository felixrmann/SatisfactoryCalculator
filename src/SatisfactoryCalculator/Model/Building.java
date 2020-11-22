package SatisfactoryCalculator.Model;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-07
 */

public class Building {

    private String buildingUUID;
    private String buildingName;
    private Integer clockSpeedInPercent;

    public Building(){

    }

    public Building(String buildingUUID, String buildingName){
        this.buildingUUID = buildingUUID;
        this.buildingName = buildingName;
        clockSpeedInPercent = 100;
    }

    public String getBuildingUUID() {
        return buildingUUID;
    }

    public void setBuildingUUID(String buildingUUID) {
        this.buildingUUID = buildingUUID;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Integer getClockSpeedInPercent() {
        return clockSpeedInPercent;
    }

    public void setClockSpeedInPercent(Integer clockSpeedInPercent) {
        this.clockSpeedInPercent = clockSpeedInPercent;
    }

    public String toString(){
        return "Building: \n" +
                "UUID: " + buildingUUID + "\n" +
                "Name: " + buildingName + "\n" +
                "Clock Speed: " + clockSpeedInPercent;
    }
}
