package ca.ulaval.glo4002.applicationServices.models;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestStatus;

public class RequestNotAcceptedInformationModel {
    private int nombrePersonne;
    private String courrielOrginsateur;
    private RequestStatus statutDemande;

    public RequestNotAcceptedInformationModel(int nombrePersonne, String courrielOrginsateur, RequestStatus statutDemande) {
        this.nombrePersonne = nombrePersonne;
        this.courrielOrginsateur = courrielOrginsateur;
        this.statutDemande = statutDemande;
    }

    public RequestNotAcceptedInformationModel(Request request) {
        this.nombrePersonne = request.getNumberOfSeatsNeeded();
        this.courrielOrginsateur = request.getResponsible().getMailAddress();
        this.statutDemande = request.getRequestStatus();
    }

    public int getNombrePersonne() {
        return nombrePersonne;
    }

    public String getCourrielOrginsateur() {
        return courrielOrginsateur;
    }

    public RequestStatus getStatutDemande() {
        return statutDemande;
    }
}
