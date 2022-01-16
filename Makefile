database-docker-up:
	docker run -d --rm -e MONGO_INITDB_ROOT_USERNAME=productListUser -e MONGO_INITDB_ROOT_PASSWORD=productListPassword -p 27017:27017 --name mongodb-local -v "$(shell pwd)/database":/database mongo:3.6.8

database-docker-up-with-bridge:
	docker run --net bridge -d --rm -e MONGO_INITDB_ROOT_USERNAME=productListUser -e MONGO_INITDB_ROOT_PASSWORD=productListPassword -p 27017:27017 --name mongodb-local -v "$(shell pwd)/database":/database mongo:3.6.8 --bind_ip_all

database-provision:
	docker exec mongodb-local bash -c './database/import.sh localhost'

database-up:
	make database-docker-up
	make database-provision

database-reset:
	make database-down
	make database-up

database-down:
	docker rm -f mongodb-local

client-build:
	sudo docker build -t walmart-client:latest ./walmartClient

server-build:
	sudo docker build -t walmart-server:latest ./walmartServer

do-builds:
	make client-build
	make server-build

apps-run:
	make database-docker-up-with-bridge
	make database-provision
	docker run --net bridge -d --name walmart-client -p 4200:4200 walmart-client:latest
	docker run --net bridge -d --name walmart-server -p 8080:8080 walmart-server:latest
	docker logs -f walmart-server

apps-stop:
	docker rm -f walmart-client
	docker rm -f walmart-server
	make database-down

apps-reset:
	make apps-stop
	make apps-run

apps-init:
	make do-builds
	make apps-run