package ca.ulaval.glo4002.models;

import java.util.List;

public class RequestsInformationModel {
    public List<RequestInformationModel> acceptees;
    public List<RequestNotAcceptedInformationModel> autres;

    public RequestsInformationModel(List<RequestInformationModel> acceptees, List<RequestNotAcceptedInformationModel> autres) {
        this.acceptees = acceptees;
        this.autres = autres;
    }
}
