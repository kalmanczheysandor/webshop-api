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
   
   