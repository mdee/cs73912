function debug {
  mvn gae:debug
}

function compile {
  cp src/main/webapp/WEB-INF/$1.web.xml src/main/webapp/WEB-INF/web.xml
  mvn clean verify
}

function run {
  if [ -z "$1" ]
  then
    PORT=8080
    echo "Running on default port $PORT"
  else
    PORT=$1
    echo "Running on port $PORT"
  fi
  ./src/main/resources/appengine/bin/dev_appserver.sh --port=$PORT target/plopbox-1.0-SNAPSHOT
}

# $2 could be a port to run on, or a word like 'master' or 'replicant'
$1 $2
