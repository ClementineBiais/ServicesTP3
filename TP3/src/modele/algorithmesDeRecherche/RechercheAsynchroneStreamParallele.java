package modele.algorithmesDeRecherche;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.Outils;
import modele.AlgorithmeRecherche;
import modele.Bibliotheque;
import modele.Livre;
import modele.NomAlgorithme;
import modele.RechercheAsynchroneAbstraite;
import modele.RechercheSynchroneAbstraite;

public class RechercheAsynchroneStreamParallele extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

	public RechercheAsynchroneStreamParallele(String nom) {
		super(nom);
	}

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {

		Stream<HyperLien<Bibliotheque>> stream = bibliotheques.parallelStream();
		Stream<Future<Optional<HyperLien<Livre>>>> futureList = stream.map(lien -> this.rechercheAsync(lien, l, client));
		Stream<Optional<HyperLien<Livre>>> optionalList = futureList.map(Outils::remplirPromesse);
		Stream<Optional<HyperLien<Livre>>> resultsFiltered = optionalList.filter(lien -> lien != null);
		return resultsFiltered.findAny().get();
	}

	@Override
	public NomAlgorithme nom() {
		return this.nom;
	}

}
