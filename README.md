# Welcome!

Below are the instructions to launch the three docker containers that correspond to an angular application, a java microservice, and a mongo database.

### Installation

If you want to launch the apps for the first time, use this command:
```sh
$ make apps-init
```

Then you just have to use this command to run the apps:
```sh
$ make apps-run
```

And if you want to stop the apps use this command:
```sh
$ make apps-stop
```

Command to reset running apps:
```sh
$ make apps-reset
```

### Database

If you want to set the mongodb and import the products to start with the challenge, use this command:
```sh
$ make database-up
```

If you only want to run the mongodb image, use this one:
```sh
$ make database-docker-up
```

If you only want to import the products in the running mongodb image, use this:
```sh
$ make database-provision
```
### Something went wrong?

If something went wrong, you can stop and remove the container with this:
```sh
$ make database-down
```

If you want to reset the container:
```sh
$ make database-reset
```

Anything else?, you are always welcome to have a look at the Makefile ;)
