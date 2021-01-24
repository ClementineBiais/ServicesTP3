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
import modele.RechercheSynchroneAbstraite;

public class RechercheSynchroneStreamRx extends RechercheSynchroneAbstraite implements AlgorithmeRecherche {

	public RechercheSynchroneStreamRx(String nom) {
		super(nom);
	}

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		return Observable.fromIterable(bibliotheques)
				.flatMap(h -> Observable.fromCallable(() -> rechercheSync(h, l, client)))
				.subscribeOn(Schedulers.io())
				.filter(value -> !value.equals(Optional.empty()))
				.blockingFirst(Optional.empty());
	}

	@Override
	public NomAlgorithme nom() {
		return this.nom;
	}

}