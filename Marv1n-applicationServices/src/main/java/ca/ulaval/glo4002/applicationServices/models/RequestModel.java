package ca.ulaval.glo4002.applicationServices.models;

import java.util.List;

public class RequestModel {

    private int nombrePersonne;
    private String courrielOrganisateur;
    private int priorite;
    private List<String> participantsCourriels;

    public RequestModel(int nombrePersonne, String courrielOrganisateur, int priorite, List<String> participantsCourriels) {
        this.nombrePersonne = nombrePersonne;
        this.courrielOrganisateur = courrielOrganisateur;
        this.priorite = priorite;
        this.participantsCourriels = participantsCourriels;
    }

    public int getNombrePersonne() {
        return nombrePersonne;
    }

    public String getCourrielOrganisateur() {
        return courrielOrganisateur;
    }

    public int getPriorite() {
        return priorite;
    }

    public List<String> getParticipantsCourriels() {
        return participantsCourriels;
    }
}
