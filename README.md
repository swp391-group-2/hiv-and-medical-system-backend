# backend

### Start & setup MySQL on docker

<pre> docker run --name hiv-and-medical-system -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.36-debian </pre>
<pre> docker run --name hiv-and-medical-system -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=hiv_and_medical_system -d mysql:8.0.36-debian <pre>
