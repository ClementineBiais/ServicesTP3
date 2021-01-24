package modele.algorithmesDeRecherche;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import modele.AlgorithmeRecherche;
import modele.Bibliotheque;
import modele.Livre;
import modele.NomAlgorithme;
import modele.RechercheSynchroneAbstraite;

public class RechercheSynchroneStreamParallele extends RechercheSynchroneAbstraite implements AlgorithmeRecherche {

	public RechercheSynchroneStreamParallele(String nom) {
		super(nom);
	}

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {

		Stream<HyperLien<Bibliotheque>> stream = bibliotheques.parallelStream();
		Stream<Optional<HyperLien<Livre>>> results = stream.map(lien -> this.rechercheSync(lien, l, client));
		Stream<Optional<HyperLien<Livre>>> resultsFiltered = results.filter(lien -> lien != null);
		return resultsFiltered.findAny().get();
	}

	@Override
	public NomAlgorithme nom() {
		return this.nom;
	}

}
