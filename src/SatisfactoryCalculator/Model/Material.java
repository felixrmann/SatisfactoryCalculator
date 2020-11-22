package SatisfactoryCalculator.Model;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-07
 */

public class Material {

    private String materialUUID;
    private String materialName;

    public Material(){}

    public Material(String materialUUID, String materialName){
        this.materialUUID = materialUUID;
        this.materialName = materialName;
    }

    public String getMaterialUUID() {
        return materialUUID;
    }

    public void setMaterialUUID(String materialUUID) {
        this.materialUUID = materialUUID;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String toString(){
        return "Material: \n" +
                "UUID: " + materialUUID + "\n" +
                "Name: " + materialName;
    }
}
