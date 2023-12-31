# Webshop Admin 1.0


## Az alkalmazás célja
### Mi a webshop
- Az online térben történő kereskedés alapvető komponensei a webshopok.
- Ezek olyan weblapok, amelyeken az adott vállalkozás által forgalmazott termékek vásárolhatóak meg.
- A teljes üzleti működtetéshez nem csak a vásárlási felületre van szükség, hanem az adatok kezelését biztosító adminisztrációhoz felületre is.

### Mi a "Webshop Admin 1.0"
- Az elkészült szoftver egy adminisztrációs felületet biztosít az adott webshopban levő
  termékek, ügyfelek és a megrendelések adminisztrálására.
- Az admin alkalmazást a webshopot üzemeltető cég erre feljogosított alkalmazottai használják.
- Az alkalmazás RESTful technológiára lett kialakítva, vagyis az adatmanipulációkkal kapcsolatos kérések és válaszok JSON formátumban történnek.
- Az alkalmazott RESTful technológiából adódóan a szoftver újrafelhasználható, mert nem szabja meg az adminisztrációs felület vizuális megjelenését.

## Az alkalmazás bemutatása
### Végezhető műveletek
- Termékek
	- létrehozás
	- törlés
	- módosítás
	- listázás
	- elérés

- Ügyfelek
	- létrehozás
	- törlés
	- módosítás
	- listázás
	- elérés

- Megrendelések
	- létrehozás (tartalmazza a megvásárolt tételeket is)
	- törlés
	- módosítás (Kiszállítási státusz)
	- listázás
	- elérés
	- Megrendelési tételek
		- hozzáadása adott vásárláshoz
		- törlése adott vásárlásból
		- módosítása
		- listázása (csak az adott vásárláshoz tartozóakat)
		- elérés

## Üzembe helyezés
### Docker image létrehozása
mvn clean package
docker build -t webshop_api_image .

### Konténer hálózat létrehozása
docker network create webshop_network

### Az adatbázis indítása docker konténerben
docker run --name webshop_db_container --network webshop_network -e MYSQL_ROOT_PASSWORD=AbC12347 -e MYSQL_DATABASE=webshop -d -p 3310:3306 mysql:latest

### Az alkalmazás indítása docker konténerben
docker run --name webshop_api_container --network webshop_network -p 8080:8080 -d webshop_api_image
	
   
   