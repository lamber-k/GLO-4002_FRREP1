package ca.ulaval.glo4002.applicationServices.models;

import java.util.List;

public class RequestsInformationModel {

    private List<RequestInformationModel> acceptees;
    private List<RequestNotAcceptedInformationModel> autres;

    public RequestsInformationModel(List<RequestInformationModel> acceptees, List<RequestNotAcceptedInformationModel> autres) {
        this.acceptees = acceptees;
        this.autres = autres;
    }

    public List<RequestInformationModel> getAcceptees() {
        return acceptees;
    }

    public List<RequestNotAcceptedInformationModel> getAutres() {
        return autres;
    }
}
