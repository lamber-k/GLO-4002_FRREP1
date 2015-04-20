package ca.ulaval.glo4002.models;

import ca.ulaval.glo4002.core.request.RequestStatus;

public class RequestInformationModel {

    public int nombrePersonne;
    public String courrielOrginsateur;
    public RequestStatus statutDemande;
    public String salleAssigne;

    public RequestInformationModel(int nombrePersonne, String courrielOrginsateur, RequestStatus statutDemande, String salleAssigne) {
        this.nombrePersonne = nombrePersonne;
        this.courrielOrginsateur = courrielOrginsateur;
        this.statutDemande = statutDemande;
        this.salleAssigne = salleAssigne;
    }
}
