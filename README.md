# OTS2025 — Pametna kakovost: od kode do produkcije (AI, SonarQube, pragovi kakovosti)

Ta repozitorij je pripravljena **učna aplikacija** za delavnico OTS 2025. Cilj je prikazati pot **od kode do produkcije** s poudarkom na **pragovih kakovosti** in avtomatizaciji.

---

## Kaj aplikacija počne
Gre za enostaven sistem za **nadzor temperaturnih meritev proizvodov** (npr. živila):
- **Products**: CRUD upravljanje izdelkov z mejami (`minMeasure`, `maxMeasure`).
- **Measurements**: oddaja novih meritev za izbran izdelek (`avgTemperature`) in samodejna presoja **OK / NOT OK** glede na dovoljene meje.
- **History**: pregled zgodovine meritev za zadnjih *N* dni (konfiguracija).

> Ob zagonu se v razvojnem profilu doda nekaj **testnih podatkov** (glej `backend/src/main/java/si/um/feri/measurements/AddTestData.java`).

---

## Tehnologije
- **Backend**: Quarkus (RESTEasy Reactive, Hibernate Reactive Panache, Mutiny), PostgreSQL
- **Frontend**: React 18, React Router, MUI, Axios
- **Testi**: Cypress (E2E), React Testing Library (osnove)
- **Kakovost**: Checkstyle, Qodana (SARIF), SonarQube (lokalno), GitHub Actions (CI)
- **Kontejnerji**: Docker (+ Dockerfile za FE in več Dockerfile-ov za Quarkus JVM/native)

---

## Struktura
```
OTS2025-main/
  ├─ backend/                     # Quarkus (Maven)
  │  ├─ src/main/java/si/um/feri/measurements/
  │  │   ├─ rest/                 # REST kontrolerji (products, history, measurements)
  │  │   ├─ dao/                  # Panache repo-ji
  │  │   ├─ dto/                  # DTO in request/response zapisi
  │  │   └─ vao/                  # JPA entitete
  │  ├─ src/main/resources/
  │  │   └─ application.properties
  │  ├─ src/main/docker/          # Quarkus Dockerfile-i
  │  └─ pom.xml
  ├─ frontend/                    # React (CRA)
  │  ├─ src/components/           # Pogledi: Measurements, Products, ...
  │  ├─ src/api/api.js            # Axios klient (REACT_APP_BACKEND_URL)
  │  ├─ .env                      # privzeti URL do backenda
  │  └─ Dockerfile + nginx.conf
  ├─ cypress/                     # E2E testi (3 test suite-i)
  ├─ .github/workflows/           # CI konfiguracije
  ├─ NAVODILA_ZA_ZAGON_APLIKACIJE.md
  ├─ NAVODILA_ZA_SONAR_QG.md
  └─ UNIT_TEST_PROMPTS.md         # primeri »AI-pomoč pri testih«
```

---

## Hiter začetek (dev)
### 1) PostgreSQL (Docker)
```bash
docker run --name measurements-db   -e POSTGRES_USER=postgres_user   -e POSTGRES_PASSWORD=postgres_pwd   -e POSTGRES_DB=measdb   -p 5432:5432 -d postgres:15
```
> Povezava in prijava sta nastavljeni v `backend/src/main/resources/application.properties`  
> Backend teče na **`http://localhost:8280/api/v1`** (CORS omogočen).

### 2) Backend (Quarkus dev)
```bash
cd backend
chmod +x mvnw
./mvnw compile quarkus:dev
```
- Dev UI: `http://localhost:8280/q/dev`
- Ob zagonu se dodajo testni podatki (2 produkta + 2 meritvi).

### 3) Frontend (React dev)
```bash
cd frontend
npm install
# .env že vsebuje:
# REACT_APP_BACKEND_URL=http://127.0.0.1:8280/api/v1
npm start
```
- Odpri `http://localhost:3000`
- Navigacija: **Measurements** (zgodovina), **Products** (CRUD).

> Podrobnejša navodila: glej **`NAVODILA_ZA_ZAGON_APLIKACIJE.md`** (vključno s Cypress navodili).

---

## API (osnove)
Vsi endpointi so pod `/{root}/api/v1` → privzeto `http://localhost:8280/api/v1`.

### Products
- `GET /products` → seznam produktov
- `POST /products` → ustvari produkt  
  Body (JSON):
  ```json
  { "name": "Tartufi", "maxMeasure": 4.0, "minMeasure": 1.0 }
  ```
- `PUT /products/{id}` → uredi produkt (ista shema kot POST)
- `DELETE /products/{id}` → izbriši produkt

### Measurements
- `GET /history` → zgodovina meritev zadnjih *N* dni (nastavljivo z `history.dayslimit`)
- `POST /product_measurement` → oddaja meritve za izdelek  
  Body (JSON):
  ```json
  { "id": 1, "avgTemperature": 12.3 }
  ```
  Response (JSON):
  ```json
  { "result": "ok" }   // ali "not ok" / "product-not-found"
  ```

> DTO zapisi: `backend/src/main/java/si/um/feri/measurements/dto/*`  
> Entitete: `backend/src/main/java/si/um/feri/measurements/vao/*`

---

## E2E testi (Cypress)
V repozitoriju so trije primeri E2E testov za Products/Measurements:  
`cypress/e2e/testSuite{1,2,3}.cy.js`

**Zagon (UI način):**
```bash
# Frontend mora teči na http://localhost:3000
cd frontend
npx cypress open
```
**Headless (npr. CI):**
```bash
cd frontend
npx cypress run
```

---

## CI/CD (GitHub Actions)
V `.github/workflows/` sta primera:
- **CI Pipeline** (`workflow.yml`): gradnja backenda (Java 20 + Maven), priprava PostgreSQL baze, gradnja frontenda.
- **PR Checkstyle** (`PR_workflow.yml`): Checkstyle lint ob PR-jih, varnostna analiza in E2E testi.
---

## Docker (produkcijski buildi)
**Frontend**
```bash
cd frontend
docker build -t measurements-frontend:latest .
docker run --rm -p 3000:80 measurements-frontend:latest
```
**Backend (JVM način)**
```bash
cd backend
./mvnw -DskipTests package
docker build -f src/main/docker/Dockerfile.jvm -t measurements-backend:latest .
docker run --rm -p 8280:8280 --env-file ./.env measurements-backend:latest
```
> Poskrbite, da backend vidi PostgreSQL na `localhost:5432` (ali uporabite `--add-host`/docker network).  
> Za **native** build glejte komentarje v `backend/README.md` in Dockerfile-e.

---

## Konfiguracija
- **Backend**: `backend/src/main/resources/application.properties`
    - `quarkus.http.port=8280`
    - `quarkus.http.root-path=api/v1`
    - `history.dayslimit=10` (za `/history`)
    - Reactive Postgres URL, user, pwd …
- **Frontend**: `frontend/.env`
    - `REACT_APP_BACKEND_URL=http://127.0.0.1:8280/api/v1`
