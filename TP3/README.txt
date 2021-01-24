- Repertoire {

	@PUT
	@Produces(TYPE_MEDIA)
	@Consumes(TYPE_MEDIA)
	@ReponsesPUTOption
	// Requête (méthode http + url) : PUT http://localhost:8081/PortailServeur2/portail
	// Corps : <livre> <titre>Services5.6</titre> </livre>
	// Réponses (à spécifier par code) : <?xml version="1.0" encoding="UTF-8" standalone="yes"?> <hyperlien uri="http://localhost:8095/bib5/bibliotheque/6"/>
	// - code : 
	Optional<HyperLien<Livre>> chercher(Livre l);


	@PUT
	@ReponsesPUTOption
	@Path(JAXRS.SOUSCHEMIN_ASYNC)
	@Consumes(JAXRS.TYPE_MEDIA)
	@Produces(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : PUT http://localhost:8081/PortailServeur2/portail/async
	// Corps : <livre> <titre>Services5.6</titre> </livre>
	// Réponses (à spécifier par code) : <?xml version="1.0" encoding="UTF-8" standalone="yes"?> <hyperlien uri="http://localhost:8095/bib5/bibliotheque/6"/>
	// - code : 
	Future<Optional<HyperLien<Livre>>> chercherAsynchrone(Livre l, @Suspended final AsyncResponse ar);

	@GET
	@Path(SOUSCHEMIN_CATALOGUE)
	@Produces(TYPE_MEDIA)
	// Requête (méthode http + url) : GET http://localhost:8081/PortailServeur2/portail/catalogue
	// Corps : x
	// Réponses (à spécifier par code) :
  //  <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
  // <liste>
  //  <hyperlien uri="http://localhost:8090/bib0/bibliotheque/0"/>
  //  <hyperlien uri="http://localhost:8090/bib0/bibliotheque/1"/>
  //  <hyperlien uri="http://localhost:8090/bib0/bibliotheque/2"/>
  //  <hyperlien uri="http://localhost:8090/bib0/bibliotheque/3"/>
  //  <hyperlien uri="http://localhost:8090/bib0/bibliotheque/4"/>
  //  <hyperlien uri="http://localhost:8090/bib0/bibliotheque/5"/>
  //  <hyperlien uri="http://localhost:8090/bib0/bibliotheque/6"/>
  //  <hyperlien uri="http://localhost:8090/bib0/bibliotheque/7"/>
  //  <hyperlien uri="http://localhost:8090/bib0/bibliotheque/8"/>
  //  <hyperlien uri="http://localhost:8090/bib0/bibliotheque/9"/>
  //  <hyperlien uri="http://localhost:8091/bib1/bibliotheque/0"/>
  //  <hyperlien uri="http://localhost:8091/bib1/bibliotheque/1"/>
  //  <hyperlien uri="http://localhost:8091/bib1/bibliotheque/2"/>
  //  <hyperlien uri="http://localhost:8091/bib1/bibliotheque/3"/>
  //  <hyperlien uri="http://localhost:8091/bib1/bibliotheque/4"/>
  //  <hyperlien uri="http://localhost:8091/bib1/bibliotheque/5"/>
  // .
  // .
  // .
  //  <hyperlien uri="http://localhost:8099/bib9/bibliotheque/7"/>
  //  <hyperlien uri="http://localhost:8099/bib9/bibliotheque/8"/>
  //  <hyperlien uri="http://localhost:8099/bib9/bibliotheque/9"/>
  // </liste>
	// - code : 
	HyperLiens<Livre> repertorier();

- Archive 
	@Path("{id}")
	@ReponsesGETNullEn404
	// Adresse de la sous-ressource : http://localhost:8091/bib1/bibliotheque/0
	// Requête sur la sous-ressource (méthode http + url) : GET http://localhost:8091/bib1/bibliotheque/0
	// Corps : x
	// Réponses (à spécifier par code) : <?xml version="1.0" encoding="UTF-8" standalone="yes"?><livre><titre>Services1.0</titre></livre>
	// - code : 
	Livre sousRessource(@PathParam("id") IdentifiantLivre id) ;

	@Path("{id}")
	@GET 
	@Produces(JAXRS.TYPE_MEDIA)
	@ReponsesGETNullEn404
	// Requête (méthode http + url) : GET http://localhost:8091/bib1/bibliotheque/0
	// Corps : x
	// Réponses (à spécifier par code) : <?xml version="1.0" encoding="UTF-8" standalone="yes"?><livre><titre>Services1.0</titre></livre>
	// - code : 
	Livre getRepresentation(@PathParam("id") IdentifiantLivre id);

	@POST
	@ReponsesPOSTEnCreated
	@Consumes(JAXRS.TYPE_MEDIA)
	@Produces(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : http://localhost:8091/bib1/bibliotheque
	// Corps : <livre> <titre>test</titre> </livre>
	// Réponses (à spécifier par code) : 201 Created
	// - code : 
	HyperLien<Livre> ajouter(Livre l);
}

- AdminAlgo
	@PUT
	@Path(JAXRS.SOUSCHEMIN_ALGO_RECHERCHE)
	@Consumes(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : PUT http://localhost:8081/PortailServeur2/portail/admin/recherche
	// Corps : <?xml version="1.0" encoding="UTF-8" standalone="yes"?><algo nom="recherche async multi"/>
	// Réponses (à spécifier par code) : 204 No Content
	// - code : 
	void changerAlgorithmeRecherche(NomAlgorithme algo);