package nic.ame.app.master.dto;

import java.util.Optional;

import nic.ame.app.master.medical.model.AmeAppointmentDetails;
import nic.ame.app.master.model.AmeDeclarationIndividualDetails;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.ForcePersonnel;

public class AmeDecleartionDto {

    AmeDeclarationIndividualDetails ameDeclarationIndividualDetails;
    AmeDeclarationIndividualModel ameDeclarationIndividualModel;
    AmeAppointmentDetails appointmentDetails;
    ForcePersonnel forcePersonal;

    private String amePlace;

    private String rank;

    private String unit;


    private String ameId;
    

    
    
    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public AmeDeclarationIndividualDetails getAmeDeclarationIndividualDetails() {
        return ameDeclarationIndividualDetails;
    }
    public void setAmeDeclarationIndividualDetails(AmeDeclarationIndividualDetails ameDeclarationIndividualDetails) {
        this.ameDeclarationIndividualDetails = ameDeclarationIndividualDetails;
    }
    public AmeDeclarationIndividualModel getAmeDeclarationIndividualModel() {
        return ameDeclarationIndividualModel;
    }
    public void setAmeDeclarationIndividualModel(AmeDeclarationIndividualModel ameDeclarationIndividualModel) {
        this.ameDeclarationIndividualModel = ameDeclarationIndividualModel;
    }
    public AmeAppointmentDetails getAppointmentDetails() {
        return appointmentDetails;
    }
    public void setAppointmentDetails(AmeAppointmentDetails appointmentDetails) {
        this.appointmentDetails = appointmentDetails;
    }
    public ForcePersonnel getForcePersonal() {
        return forcePersonal;
    }
    public void setForcePersonal(ForcePersonnel forcePersonal) {
        this.forcePersonal = forcePersonal;
    }
    public String getAmePlace() {
        return amePlace;
    }
    public void setAmePlace(String amePlace) {
        this.amePlace = amePlace;
    }
    public String getAmeId() {
        return ameId;
    }
    public void setAmeId(String ameId) {
        this.ameId = ameId;
    }


    
}
