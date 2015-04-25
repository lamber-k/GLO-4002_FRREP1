package ca.ulaval.glo4002.applicationServices.models;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestStatus;

public class RequestInformationModel {

    private int nombrePersonne;
    private String courrielOrginsateur;
    private RequestStatus statutDemande;
    private String salleAssigne;

    public RequestInformationModel(int nombrePersonne, String courrielOrginsateur, RequestStatus statutDemande, String salleAssigne) {
        this.nombrePersonne = nombrePersonne;
        this.courrielOrginsateur = courrielOrginsateur;
        this.statutDemande = statutDemande;
        this.salleAssigne = salleAssigne;
    }

    public RequestInformationModel(Request request) {
        this.nombrePersonne = request.getNumberOfSeatsNeeded();
        this.courrielOrginsateur = request.getResponsible().getMailAddress();
        this.statutDemande = request.getRequestStatus();
        this.salleAssigne = request.getReservedRoom().getName();
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

    public String getSalleAssigne() {
        return salleAssigne;
    }
}
