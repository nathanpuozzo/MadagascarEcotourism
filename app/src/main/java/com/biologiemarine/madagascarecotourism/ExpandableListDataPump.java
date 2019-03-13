package com.biologiemarine.madagascarecotourism;


/*
public class ExpandableListDataPump extends MainActivity {
    String nuit = getIntent().getExtras().getString( "nuit" );
    public HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        FeatureCollection featureCollection;
        List<Feature> featureList = featureCollection.features();
        Feature feature = featureList.get(0);

            String energie = feature.getStringProperty( "energie verte (sur 2)" );
            String dechets = feature.getStringProperty( "traitement de dechets  (sur 2)" );
            String communaute = feature.getStringProperty( "communaute  (sur 2)" );
            String salaire = feature.getStringProperty( "salaire_employé" );
            String ipe = feature.getStringProperty( "ipe" );


            String adresse = feature.getStringProperty( "adresse" );
            String tel = feature.getStringProperty( "tel" );
            String mail = feature.getStringProperty( "email" );
            String site = feature.getStringProperty( "site web" );
            if (communaute == null) {
                communaute = "0";
            }
            if (energie == null) {
                energie = "0";
            }
            if (dechets == null) {
                dechets = "0";
            }
            if (salaire == null) {
                salaire = "0";
            }
            if (site == null) {
                site = "-";
            }
            if (mail == null) {
                mail = "-";
            }

            List <String> cricket = new ArrayList <String>();
            cricket.add( "Energie verte : " + energie + "/2" );
            cricket.add( "Traitement de déchets : " + dechets + "/2" );
            cricket.add( "Communauté : " + communaute + "/2" );
            cricket.add( "Salaire équitable : " + salaire + "/2" );
            // cricket.add("Indice d'effort sur salaire équitable : 1.3");

            List <String> football = new ArrayList <String>();
            football.add( "Booking" );
            football.add( "TripAdvisor" );


            List <String> basketball = new ArrayList <String>();
            basketball.add( "Adresse :\n " + adresse );
            basketball.add( "Téléphone : \n " + tel );
            basketball.add( "E-mail : \n " + mail );
            basketball.add( "Site web :\n " + site );


            expandableListDetail.put( "IPE (Indice de Performance Ecotouristique) : " + ipe + "/10", cricket );
            expandableListDetail.put( "Qualité des services*", football );
            expandableListDetail.put( "Contact", basketball );

        return expandableListDetail;
     }

    }

/*
    public static HashMap<String, List<String>> getData2() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("Energie verte : 2/2");
        cricket.add("Traitement de dechets : 2/2");
        cricket.add("Communauté : 0/2");
        cricket.add("Salaire équitable : 1/2");
       // cricket.add("Indice d'effort sur salaire équitable : 0.4");

        List<String> football = new ArrayList<String>();
        football.add("Lonely Planet");
        football.add("TripAdvisor");


        List<String> basketball = new ArrayList<String>();
        basketball.add("Adresse :\n Hotel Bakuba\n Route de Saint-Augustin\n Ankilibe, Toliara 601\n Madagascar");
        basketball.add("Téléphone : \n 032 51 528 97");
        basketball.add("E-mail : \n info@bakuba-lodge.com");


        expandableListDetail.put("IPE (Indice de Performance Ecotouristique) : 6,25/10", cricket);
        expandableListDetail.put("Qualité des services", football);
        expandableListDetail.put("Contact", basketball);
        return expandableListDetail;
    }
    */



