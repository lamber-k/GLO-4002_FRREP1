package ca.ulaval.glo4002.applicationServices.models;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestStatus;

public class RequestInformationModel {

    private static final String AUCUNE_SALLE_ASSIGNEE = "Aucune salle n'est assignée.";
    private int nombrePersonne;
    private String courrielOrginsateur;
    private RequestStatus statutDemande;
    private String salleAssigne;

    public RequestInformationModel(int nombrePersonne, String courrielOrginsateur, RequestStatus statutDemande, String salleAssigne) {
        this.nombrePersonne = nombrePersonne;
        this.courrielOrginsateur = courrielOrginsateur;
        this.statutDemande = statutDemande;
        if(statutDemande == RequestStatus.ACCEPTED) {
            this.salleAssigne = salleAssigne;
        }
        else{
            this.salleAssigne = AUCUNE_SALLE_ASSIGNEE;
        }
    }

    public RequestInformationModel(Request request) {
        this.nombrePersonne = request.getNumberOfSeatsNeeded();
        this.courrielOrginsateur = request.getResponsible().getMailAddress();
        this.statutDemande = request.getRequestStatus();
        if(statutDemande == RequestStatus.ACCEPTED) {
            this.salleAssigne = request.getReservedRoom().getName();
        }
        else{
            this.salleAssigne = AUCUNE_SALLE_ASSIGNEE;
        }
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
