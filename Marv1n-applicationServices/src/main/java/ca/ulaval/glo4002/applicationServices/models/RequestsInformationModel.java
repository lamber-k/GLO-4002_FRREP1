package ca.ulaval.glo4002.applicationServices.models;

import java.util.List;

public class RequestsInformationModel {

    private List<RequestInformationModel> acceptees;
    private List<RequestInformationModel> autres;

    public RequestsInformationModel(List<RequestInformationModel> acceptees, List<RequestInformationModel> autres) {
        this.acceptees = acceptees;
        this.autres = autres;
    }

    public List<RequestInformationModel> getAcceptees() {
        return acceptees;
    }

    public List<RequestInformationModel> getAutres() {
        return autres;
    }
}
