import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;

public class AppliCliente {

	private String[] algorithmeListe = { "recherche sync seq", "recherche sync multi", "recherche sync stream 8",
			"recherche sync stream rx", "recherche async seq", "recherche async multi", "recherche async stream 8",
			"recherche async stream rx" };

	private String apiUrl = "http://localhost:8081/PortailServeur2";
	private String adminUrl = "/admin/recherche";
	private String portailUrl = "/portail/";
	private String algoXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><algo nom=\"%s\"/>";
	private String livreXML = "<livre>\n" + "  <titre>Services5.6</titre>\n" + "</livre>";

	public static Client clientJAXRS() {
		ClientConfig config = new ClientConfig();
		return ClientBuilder.newClient(config);
	}

	public void setAlgorithm(String nomAlgorithme) {
		clientJAXRS()
			.target(this.apiUrl)
			.path(this.adminUrl)
			.request(MediaType.APPLICATION_XML)
			.put(Entity.entity(String.format(this.algoXML, nomAlgorithme), MediaType.APPLICATION_XML));
	}

	public void getLivre() {
		clientJAXRS()
			.target(this.apiUrl)
			.path(this.portailUrl)
			.request(MediaType.APPLICATION_XML)
			.put(Entity.entity(String.format(this.algoXML, this.livreXML), MediaType.APPLICATION_XML));
	}

	public static void main(String[] args) {
		System.out.println("*****BEGINING*****");

		AppliCliente appliCliente = new AppliCliente();
		for (String algorithme : appliCliente.algorithmeListe) {
			
			System.out.println("*************");
			System.out.printf("Algorithme %s \n", algorithme);
			appliCliente.setAlgorithm(algorithme);
			
			long begin = System.nanoTime();
			appliCliente.getLivre();
			long end = System.nanoTime();
			long duration = end - begin;
			System.out.printf("Duration : %d \n", duration/1000000);
						
			System.out.println("************* \n");
		}

		System.out.println("*****END******");
	}
}
