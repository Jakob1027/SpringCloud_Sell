mvn clean package -Dmaven.test.skip=true

docker build -t springcloud/api-gateway .