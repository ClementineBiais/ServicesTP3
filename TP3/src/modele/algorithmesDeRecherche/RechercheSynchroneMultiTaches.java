package modele.algorithmesDeRecherche;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import modele.AlgorithmeRecherche;
import modele.Bibliotheque;
import modele.Livre;
import modele.NomAlgorithme;
import modele.RechercheSynchroneAbstraite;

public class RechercheSynchroneMultiTaches extends RechercheSynchroneAbstraite implements AlgorithmeRecherche {

	public RechercheSynchroneMultiTaches(String nom) {
		super(nom);
	}

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		CountDownLatch barriere = new CountDownLatch(bibliotheques.size());
		AtomicReference<Optional<HyperLien<Livre>>> atomicR = new AtomicReference();
		atomicR.set(Optional.empty());
		ExecutorService service = Executors.newCachedThreadPool();
		try {
			for (HyperLien<Bibliotheque> hyperLien : bibliotheques) {
				service.submit(() -> {
					Optional<HyperLien<Livre>> currentLivreLien = this.rechercheSync(hyperLien, l, client);
					if (currentLivreLien.isPresent()) {
						atomicR.set(currentLivreLien);
						this.liberer(barriere);
					}
					barriere.countDown();
				});
			}

			barriere.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return atomicR.get();
	}

	private void liberer(CountDownLatch countDownLatch) {
		while (countDownLatch.getCount() > 0) {
			countDownLatch.countDown();
		}
	}

	@Override
	public NomAlgorithme nom() {
		return this.nom;
	}

}
