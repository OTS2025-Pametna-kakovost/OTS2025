Step to install SonarQube

```bash
docker pull sonarqube
```

```bash
docker run -d --name sonarqube-db -e POSTGRES_USER=sonar -e POSTGRES_PASSWORD=sonar -e POSTGRES_DB=sonarqube postgres:alpine
```

Run SonarQube

```bash
docker run -d --name sonarqube -p 9000:9000 --link sonarqube-db:db -e SONAR_JDBC_URL=jdbc:postgresql://db:5432/sonarqube -e SONAR_JDBC_USERNAME=sonar -e SONAR_JDBC_PASSWORD=sonar sonarqube
```

This command sets up the SonarQube container with a link to the PostgreSQL database.

Set Sonar Scanner Home
```bash
nano ~/.bashrc

export SONAR_SCANNER_HOME=/path/to/sonar-scanner

Add Sonar Scanner to PATH
export PATH=$SONAR_SCANNER_HOME/bin:$PATH
```