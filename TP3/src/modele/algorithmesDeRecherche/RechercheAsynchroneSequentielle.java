package modele.algorithmesDeRecherche;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import modele.AlgorithmeRecherche;
import modele.Bibliotheque;
import modele.Livre;
import modele.NomAlgorithme;
import modele.RechercheAsynchroneAbstraite;
import modele.RechercheSynchroneAbstraite;

public class RechercheAsynchroneSequentielle extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

	public RechercheAsynchroneSequentielle(String nom) {
		super(nom);
	}

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		Optional<HyperLien<Livre>> livreLien = Optional.empty();
		List<Future<Optional<HyperLien<Livre>>>> promesseLivreLienList = new ArrayList<Future<Optional<HyperLien<Livre>>>>();
		for (HyperLien<Bibliotheque> hyperLien : bibliotheques) {
			promesseLivreLienList.add(this.rechercheAsync(hyperLien, l, client));
		}
		for (Future<Optional<HyperLien<Livre>>> future : promesseLivreLienList) {
			try {
				Optional<HyperLien<Livre>> lien = future.get();
				if (lien.isPresent()) livreLien = lien;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return livreLien;
	}

	@Override
	public NomAlgorithme nom() {
		return this.nom;
	}

}
