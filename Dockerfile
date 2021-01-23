FROM rabbitmq:3-management
ADD target/rent-a-apartment-0.0.1-SNAPSHOT.jar .
EXPOSE 15672
CMD java -jar rent-a-apartment-0.0.1-SNAPSHOT.jar