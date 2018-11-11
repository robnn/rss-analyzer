# RSS analyzer

## Develop build status

![develop build status](https://travis-ci.org/robnn/rss-analyzer.svg?branch=develop)

## Master build status

![develop build status](https://travis-ci.org/robnn/rss-analyzer.svg?branch=master)

## Development frontend server

Run `ng serve` in `rss-analyzer-ui` folder to start a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Development backend server

Install postgressql9:

`sudo apt-get install postgresql-9.6`

Install pgadmin 3 or 4

For 3:

`sudo apt-get install pgadmin3`

Start Pgadmin and create a database on the local running postgres server, with the name `rss_analyzer`.

CD into project directory and run:

`mvn spring-boot:run`