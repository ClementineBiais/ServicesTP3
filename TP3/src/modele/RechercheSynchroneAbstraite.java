package modele;

import java.util.Optional;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.LienVersRessource;

public abstract class RechercheSynchroneAbstraite implements AlgorithmeRecherche {
	
	protected NomAlgorithme nom;
	
	protected RechercheSynchroneAbstraite(String nom) {
		this.nom = new ImplemNomAlgorithme(nom);
	}

	 protected Optional<HyperLien<Livre>> rechercheSync(HyperLien<Bibliotheque> h, Livre l, Client client) {
	       Bibliotheque proxy = LienVersRessource.proxy(client, h, Bibliotheque.class);
	       return proxy.chercher(l);
	 }

}
