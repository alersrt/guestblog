Contribution Guide
==================




## Requirements

- If you don't use docker-wrapped commands, make sure that tools you use have the same version as in docker-wrapped commands. It's latest version, mainly.


## Operations

Take a look at [`Makefile`][1] for commands usage details.


### Development environment

Use `docker-compose` from [`Makefile`][1] to boot up (or restart) [dockerized environment](docker-compose.yml) for development:
```bash
make up
make down

# or without Docker image rebuilding
make up rebuild=no

# or run in background mode
make up background=yes
```


### Dependencies

To preload project dependencies use docker-wrapped command from [`Makefile`][1]:
```bash
make deps
```


### Building

To build/rebuild project Docker image use docker-wrapped command from [`Makefile`][1]:
```bash
make build
```


### Documentation

To generate JavaDoc documentation of project use docker-wrapped command from [`Makefile`][1]:
```bash
make docs
# available in target/site/ dir 
```




[1]: Makefile