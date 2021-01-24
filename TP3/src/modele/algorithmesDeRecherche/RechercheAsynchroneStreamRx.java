package modele.algorithmesDeRecherche;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import modele.AlgorithmeRecherche;
import modele.Bibliotheque;
import modele.Livre;
import modele.NomAlgorithme;
import modele.RechercheAsynchroneAbstraite;
import modele.RechercheSynchroneAbstraite;

public class RechercheAsynchroneStreamRx extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

	public RechercheAsynchroneStreamRx(String nom) {
		super(nom);
	}

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		
		return Observable.fromIterable(bibliotheques)
				.flatMap(h -> Observable.fromFuture(this.rechercheAsync(h, l, client)))
				.subscribeOn(Schedulers.io())
				.filter(value -> !value.equals(Optional.empty()))
				.blockingFirst(Optional.empty());
	}

	@Override
	public NomAlgorithme nom() {
		return this.nom;
	}

}