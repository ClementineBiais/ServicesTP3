package modele.algorithmesDeRecherche;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import modele.AlgorithmeRecherche;
import modele.Bibliotheque;
import modele.Livre;
import modele.NomAlgorithme;
import modele.RechercheSynchroneAbstraite;

public class RechercheSynchroneSequentielle extends RechercheSynchroneAbstraite implements AlgorithmeRecherche {

	public RechercheSynchroneSequentielle(String nom) {
		super(nom);
	}

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		Optional<HyperLien<Livre>> livreLien = Optional.empty();
		for (HyperLien<Bibliotheque> hyperLien : bibliotheques) {
			livreLien = this.rechercheSync(hyperLien, l, client);
			if (livreLien.isPresent()) return livreLien;
		}
		
		return livreLien;
	}

	@Override
	public NomAlgorithme nom() {
		return this.nom;
	}

}
