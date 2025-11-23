# 🚀 Navodila za zagon aplikacije

## 🗄️ 1) Zagon podatkovne baze (PostgreSQL prek Dockerja)

Najprej zaženite podatkovno bazo:

```bash
docker run --name measurements-db \
  -e POSTGRES_USER=postgres_user \
  -e POSTGRES_PASSWORD=postgres_pwd \
  -e POSTGRES_DB=measdb \
  -p 5432:5432 -d postgres:15
```

- Podatkovna baza bo dostopna na `localhost:5432`.
- Prijavne podatke in povezovalne nastavitve najdete v datoteki **`backend/src/main/resources/application.properties`**.
- V primeru, da je port že zaseden, zaprite obstoječo instanco PostgreSQL ali spremenite mapiranje porta (npr. `-p 5433:5432`) ter ustrezno posodobite nastavitve v `application.properties`.

---

## ⚙️ 2) Zagon backenda (Quarkus)

V novi terminalski seji zaženite Quarkus aplikacijo:

```bash
cd backend
./mvnw quarkus:dev **_ali_** ./mvnw -Dnet.bytebuddy.experimental=true quarkus:dev
```

- Backend bo dosegljiv na [http://localhost:8280](http://localhost:8280).
- Ob zagonu se izvede razred `AddTestData`, ki v podatkovno bazo vnese testne podatke (npr. demo produkte).

---

## 🌐 3) Zagon frontenda (React)

V ločeni terminalski seji zaženite React aplikacijo:

```bash
cd frontend
npm install   # samo ob prvem zagonu
npm start
```

- Frontend bo dostopen na [http://localhost:3000](http://localhost:3000).
- Aplikacijo lahko nato odprete v brskalniku in interaktivno uporabljate funkcionalnosti (produkti in meritve).

---

## 🧪 4) Izvajanje Cypress testov

Za testiranje uporabite orodje Cypress, ki ga lahko zaženete v dveh načinih:

### a) Interaktivni način (Cypress UI)

```bash
cd frontend
npx cypress open
```

- Odpre se Cypress uporabniški vmesnik.
- Izberite **E2E Testing**, nato brskalnik (Chrome/Electron) in željeno testno datoteko (`testSuite1.cy.js`, `testSuite2.cy.js`, `testSuite3.cy.js`).
- Testi se izvajajo proti tekoči frontend aplikaciji na `http://localhost:3000`.

### b) Headless način (terminal / CI-pipeline)

```bash
cd frontend
npx cypress run
```

- Izvedejo se vsi testi v terminalu.
- Rezultati (posnetki zaslona, videoposnetki) se shranijo v mapo `frontend/cypress/`.

---

## ✅ Kontrolni seznam pred zagonom testov

Pred izvajanjem Cypress testov preverite:

- [ ] Backend (`./mvnw quarkus:dev`) teče na portu **8080**
- [ ] Frontend (`npm start`) teče na portu **3000**
- [ ] Cypress cilja na `http://localhost:3000`

---

## 🧰 (Neobvezno) Dodajanje skript v `package.json`

V `frontend/package.json` lahko dodate priročne skripte:

```json
{
  "scripts": {
    "start": "react-scripts start",
    "cypress:open": "cypress open",
    "cypress:run": "cypress run",
    "test:e2e": "cypress run",
    "test:e2e:ui": "cypress open"
  }
}
```

Od tu naprej lahko Cypress zaganjate enostavneje:

```bash
npm run cypress:open   # interaktivni način
npm run cypress:run    # headless način
```

---

## 🔁 Upravljanje podatkovne baze

- **Zaustavitev baze:**
  ```bash
  docker stop measurements-db
  ```

- **Ponovni zagon baze:**
  ```bash
  docker start measurements-db
  ```

- **Ponastavitev baze (POZOR: izbriše vse podatke):**
  ```bash
  docker rm -f measurements-db
  docker volume prune   # po želji, če so bili uporabljeni volumni
  # nato ponovno zaženite 'docker run ...'
  ```

---
## 📊 SonarQube analiza kode ukaz za MacOS/Linux terminal:

```bash
     ./mvnw clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
       -Dnet.bytebuddy.experimental=true \ # na Windows, Linux odstranite to vrstico
       -Dsonar.projectKey=YOUR_PROJECT_KEY \
       -Dsonar.projectName='YOUR_PROJECT_NAME' \
       -Dsonar.host.url=http://localhost:9001 \
       -Dsonar.token=YOUR_SONARQUBE_TOKEN
```