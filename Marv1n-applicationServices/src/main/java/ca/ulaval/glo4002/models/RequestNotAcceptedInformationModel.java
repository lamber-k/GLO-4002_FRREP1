package ca.ulaval.glo4002.models;

import ca.ulaval.glo4002.core.request.RequestStatus;

/**
 * Created by Rafaël on 20/04/2015.
 */
public class RequestNotAcceptedInformationModel {
    public RequestStatus statutDemande;
    public int nombrePersonne;
    public String courrielOrginsateur;


    public RequestNotAcceptedInformationModel(int nombrePersonne, String courrielOrginsateur, RequestStatus statutDemande) {
        this.nombrePersonne = nombrePersonne;
        this.courrielOrginsateur = courrielOrginsateur;
        this.statutDemande = statutDemande;
    }
}
