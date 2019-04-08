-- INSERTION SCRIPTS
-- 28.02.2019
-- Lou Simon




--CANTONS
INSERT INTO CANTONS (LIBELLE) VALUES ('Neuchâtel'); --1
INSERT INTO CANTONS (LIBELLE) VALUES ('Jura'); --2
INSERT INTO CANTONS (LIBELLE) VALUES ('Vaud'); --3
INSERT INTO CANTONS (LIBELLE) VALUES ('Valais'); --4
INSERT INTO CANTONS (LIBELLE) VALUES ('Berne'); --5
INSERT INTO CANTONS (LIBELLE) VALUES ('Genève'); --6
INSERT INTO CANTONS (LIBELLE) VALUES ('Fribourg'); --7
INSERT INTO CANTONS (LIBELLE) VALUES ('Autre'); --8
/

--LOCALITE
INSERT INTO LOCALITES (VILLE, NPA, CAN_NUMERO) VALUES ('Lausanne','1000',3); --1
INSERT INTO LOCALITES (VILLE, NPA, CAN_NUMERO) VALUES ('Neuchâtel','2000',1); --2
INSERT INTO LOCALITES (VILLE, NPA, CAN_NUMERO) VALUES ('Yverdon-les-bains','1400',3); --3
INSERT INTO LOCALITES (VILLE, NPA, CAN_NUMERO) VALUES ('Genève','1200',6); --4
INSERT INTO LOCALITES (VILLE, NPA, CAN_NUMERO) VALUES ('Fribourg','1700',7); --5
INSERT INTO LOCALITES (VILLE, NPA, CAN_NUMERO) VALUES ('Berne','3000',5); --6
INSERT INTO LOCALITES (VILLE, NPA, CAN_NUMERO) VALUES ('Sion','1950',4); --7
INSERT INTO LOCALITES (VILLE, NPA, CAN_NUMERO) VALUES ('Autre','0000',8); --8
INSERT INTO LOCALITES (VILLE, NPA, CAN_NUMERO) VALUES ('Delémont','2800',2); --9
INSERT INTO LOCALITES (VILLE, NPA, CAN_NUMERO) VALUES ('Les Ecorcheresses','2748',5); --10
INSERT INTO LOCALITES (VILLE, NPA, CAN_NUMERO) VALUES ('Tavannes','2710',5); --11
/

--ECOLES
INSERT INTO ECOLES (LIBELLE, LOC_NUMERO) VALUES ('HE-ARC',2);
INSERT INTO ECOLES (LIBELLE, LOC_NUMERO) VALUES ('HEIG-VD',3);
INSERT INTO ECOLES (LIBELLE, LOC_NUMERO) VALUES ('EPFL',1);
INSERT INTO ECOLES (LIBELLE, LOC_NUMERO) VALUES ('UNINE',2);
INSERT INTO ECOLES (LIBELLE, LOC_NUMERO) VALUES ('UNILE',1);
INSERT INTO ECOLES (LIBELLE, LOC_NUMERO) VALUES ('Autre',8);
/

--TYPE RABAIS
INSERT INTO TYPERABAIS (LIBELLE) VALUES ('gratuit');
INSERT INTO TYPERABAIS (LIBELLE) VALUES ('pourcentage');
INSERT INTO TYPERABAIS (LIBELLE) VALUES ('réduction');
INSERT INTO TYPERABAIS (LIBELLE) VALUES ('prix fixe');
/

--CATEGORIES
INSERT INTO CATEGORIES (LIBELLE) VALUES ('Transports');
INSERT INTO CATEGORIES (LIBELLE) VALUES ('Divertissement');
INSERT INTO CATEGORIES (LIBELLE) VALUES ('Musées');
INSERT INTO CATEGORIES (LIBELLE) VALUES ('Logiciels');
INSERT INTO CATEGORIES (LIBELLE) VALUES ('Restaurants');
INSERT INTO CATEGORIES (LIBELLE) VALUES ('Shopping');
INSERT INTO CATEGORIES (LIBELLE) VALUES ('Autres');
/

--PROFILS

--Utilisateurs
INSERT INTO PROFILS (EMAIL,
					MOTDEPASSE,
					PRENOM,
					NOM,
					DATENAISSANCE,
					SEXE,
					NOTIFICATION,
					ADMIN
					)
					VALUES(
						'mirko.muller@bluewin.ch',
						1234,
						'Mirko',
						'Müller',
						'08.11.1992',
						'N',
						'N',
						'0'
					); 
/					
INSERT INTO PROFILS (EMAIL,
					MOTDEPASSE,
					PRENOM,
					NOM,
					DATENAISSANCE,
					SEXE,
					NOTIFICATION,
					ADMIN
					)
					VALUES(
						'lou.simon@gmail.ch',
						1234,
						'Lou',
						'Simon',
						'24.12.1994',
						'Y',
						'Y',
						'0'
					); 
/
INSERT INTO PROFILS (EMAIL,
					MOTDEPASSE,
					PRENOM,
					NOM,
					DATENAISSANCE,
					SEXE,
					NOTIFICATION,
					ADMIN
					)
					VALUES(
						'cyril.wuethrich@hotmail.ch',
						1234,
						'Cyril',
						'Wüthrich',
						'28.02.1993',
						'N',
						'N',
						'0'
					);
/
INSERT INTO PROFILS (EMAIL,
					MOTDEPASSE,
					PRENOM,
					NOM,
					DATENAISSANCE,
					SEXE,
					NOTIFICATION,
					ADMIN
					)
					VALUES(
						'Nicolas.sibille@gmail.com',
						1234,
						'Nicolas',
						'Sibile',
						'1.02.1993',
						'N',
						'N',
						'0'
					);
/
--Admins
INSERT INTO PROFILS (EMAIL,
					MOTDEPASSE,
					PRENOM,
					NOM,
					DATENAISSANCE,
					SEXE,
					NOTIFICATION,
					ADMIN
					)
					VALUES(
						'mirko.muller@offresEtudiants.ch',
						1234,
						'Mirko',
						'Müller',
						'08.11.1992',
						'N',
						'N',
						'1'
					); 
/					
INSERT INTO PROFILS (EMAIL,
					MOTDEPASSE,
					PRENOM,
					NOM,
					DATENAISSANCE,
					SEXE,
					NOTIFICATION,
					ADMIN
					)
					VALUES(
						'lou.simon@offresEtudiants.ch',
						1234,
						'Lou',
						'Simon',
						'24.12.1994',
						'Y',
						'Y',
						'1'
					);
/					
INSERT INTO PROFILS (EMAIL,
					MOTDEPASSE,
					PRENOM,
					NOM,
					DATENAISSANCE,
					SEXE,
					NOTIFICATION,
					ADMIN
					)
					VALUES(
						'cyril.wuethrich@offresEtudiants.ch',
						1234,
						'Cyril',
						'Wüthrich',
						'28.02.1993',
						'N',
						'N',
						'1'
					);
/
INSERT INTO PROFILS (EMAIL,
					MOTDEPASSE,
					PRENOM,
					NOM,
					DATENAISSANCE,
					SEXE,
					NOTIFICATION,
					ADMIN
					)
					VALUES(
						'Nicolas.sibile@offresEtudiants.ch',
						1234,
						'Nicolas',
						'Sibile',
						'1.02.1993',
						'N',
						'N',
						'1'
					);
/					
					
--OFFRES
INSERT INTO  OFFRES (LIBELLE, ORGANISATION, RUE, NUMERORUE, LIEN, IMAGE, DATEDEBUT, DATEFIN, ESTVALIDEE)
              VALUES('Europa-Park', 'CFF', ' Europa-Park-Straße', 2, 'https://www.europapark.de/fr', null, '10-04-2019', '10-05-2019', '1' );
			  
INSERT INTO  OFFRES (LIBELLE,
					DESCRIPTION,
					ORGANISATION,
					RUE,
					NUMERORUE,
					LIEN,
					IMAGE,
					DATEDEBUT,
					DATEFIN,
					ESTVALIDEE,
					MONTANT,
					TRAB_NUMERO,
					LOC_NUMERO,
					PRO_NUMERO,
					PRO_NUMERO2)
					VALUES('Réduction cinéma Cinevital',
						'En tant qu’étudiant Bénéficiez de 2 CHF de réduction par séance',
						'Cinevital',
						'Fbg du Lac',
						21,
						'https://www.cinevital.ch/fr/neuchatel',
						null,
						'23-04-2019',
						'23-05-2020',
						'1',
						15,
                        3,
                        2,
						70,
						65
					);

/
INSERT INTO  OFFRES (LIBELLE,
					DESCRIPTION,
					ORGANISATION,
					RUE,
					NUMERORUE,
					LIEN,
					IMAGE,
					DATEDEBUT,
					DATEFIN,
					ESTVALIDEE,
					TRAB_NUMERO,
					LOC_NUMERO,
					PRO_NUMERO,
					PRO_NUMERO2)
					VALUES('Réduction cinéma Rex',
						'En tant qu’étudiant Bénéficiez  de 35% par séance',
						'Rex',
						'Rue central',
						11,
						'https://www.cineman.ch/fr/seances/cinema/rex-neuchatel',
						null,
						'23-04-2019',
						'23-05-2020',
						'1',
						2,
						2,
						71,
						65
					);
/
INSERT INTO  OFFRES (LIBELLE,
					DESCRIPTION,
					ORGANISATION,
					RUE,
					NUMERORUE,
					LIEN,
					IMAGE,
					ESTVALIDEE,
					TRAB_NUMERO,
					LOC_NUMERO,
					PRO_NUMERO,
					PRO_NUMERO2)
					VALUES('Réduction cinéma Studio',
						'En tant qu’étudiant Bénéficiez  de 1 CHF  de reduction par séance, et de Popcorn gratuit tout les mardi soir ',
						'cinéma Studio',
						'Fbg du Lac',
						11,
						'https://www.cinepel.ch/fr/neuchatel.html',
						null,
						'1',
						2,
						2,
						71,
						65
					);
/				  
INSERT INTO  OFFRES (LIBELLE,
					DESCRIPTION,
					ORGANISATION,
					LIEN,
					IMAGE,
					ESTVALIDEE,
					CONDITION,
					TRAB_NUMERO,
					PRO_NUMERO,
					PRO_NUMERO2)
					VALUES('BCN Tour 2019 Inscription offert ',
						'Pour les coureurs, nous formerons au maximum 12 équipes de cinq personnes qui porteront haut les couleurs de notre Ecole!, Toutes les informations sont disponible sur le site du bcn-tour',
						'HE-ARC',
						'https://www.sportplus.ch/bcn-tour/',
						null,
						'0',
						'Par votre inscription, vous vous engagez à courir au minimum cinq des six étapes et en portant le T-shirt HE-Arc. Les personnes qui n’auront pas respecté cet engagement devront rembourser à l’école les frais d’inscription.',
						4,
						69,
						64
					);  
/				  
-- lien entre Offres et Categorie 
 --autre  
 INSERT INTO OFFRECATEGORIES (CAT_NUMERO, OFF_NUMERO)
							VALUES(7, 48);
/
--Divertisement 
INSERT INTO OFFRECATEGORIES (CAT_NUMERO, OFF_NUMERO)
							VALUES(2, 41);
/
INSERT INTO OFFRECATEGORIES (CAT_NUMERO, OFF_NUMERO)
							VALUES(2, 42);
/
INSERT INTO OFFRECATEGORIES (CAT_NUMERO, OFF_NUMERO)
							VALUES(2, 43);
/		
INSERT INTO OFFRECATEGORIES (CAT_NUMERO, OFF_NUMERO)
							VALUES(1, 45);
/
INSERT INTO OFFRECATEGORIES (CAT_NUMERO, OFF_NUMERO)
							VALUES(2, 28);							
							
							
							


COMMIT;