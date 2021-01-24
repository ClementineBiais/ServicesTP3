package modele.algorithmesDeRecherche;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import javax.management.RuntimeErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.InvocationCallback;

import infrastructure.jaxrs.HyperLien;
import modele.AlgorithmeRecherche;
import modele.Bibliotheque;
import modele.Livre;
import modele.NomAlgorithme;
import modele.RechercheAsynchroneAbstraite;
import modele.RechercheSynchroneAbstraite;

public class RechercheAsynchroneMultiTaches extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

	public RechercheAsynchroneMultiTaches(String nom) {
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
					InvocationCallback<Optional<HyperLien<Livre>>> callback = new InvocationCallback<Optional<HyperLien<Livre>>>() {

						@Override
						public void completed(Optional<HyperLien<Livre>> response) {
							// TODO Auto-generated method stub
							barriere.countDown();
							if(response.isPresent()) {
								atomicR.set(response);
								liberer(barriere);
							}
						}
	
						@Override
						public void failed(Throwable throwable) {
							barriere.countDown();
							throw new RuntimeException("Error while calling callback function");
						}
					};
							
					this.rechercheAsyncAvecRappel(hyperLien, l, client, callback);
				});
			}
			barriere.await();
		} catch (InterruptedException e) {
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
