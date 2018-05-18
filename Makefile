###############################
# Common defaults/definitions #
###############################

comma := ,

# Checks two given strings for equality.
eq = $(if $(or $(1),$(2)),$(and $(findstring $(1),$(2)),\
                                $(findstring $(2),$(1))),1)




###############
# Git Section #
###############

MAINLINE_BRANCH := dev
CURRENT_BRANCH := $(shell git branch | grep \* | cut -d ' ' -f2)

# Squash changes of the current Git branch onto another Git branch.
#
# WARNING: You must merge `onto` branch in the current branch before squash!
#
# Usage:
#	make squash [onto=] [del=(no|yes)]

onto ?= $(MAINLINE_BRANCH)
del ?= no
upstream ?= origin

squash:
ifeq ($(CURRENT_BRANCH),$(onto))
	@echo "--> Current branch is '$(onto)' already" && false
endif
	git checkout $(onto)
	git branch -m $(CURRENT_BRANCH) orig-$(CURRENT_BRANCH)
	git checkout -b $(CURRENT_BRANCH)
	git branch --set-upstream-to $(upstream)/$(CURRENT_BRANCH)
	git merge --squash orig-$(CURRENT_BRANCH)
ifeq ($(del),yes)
	git branch -d orig-$(CURRENT_BRANCH)
endif




###########
# Aliases #
###########

build: maven.build

clean: maven.clean

# Resolve all project dependencies.
#
# Usage:
#	make deps

deps: maven.deps yarn.deps

docs: maven.docs

down: docker.down

up: docker.up




##################
# Maven commands #
##################

# Maven command.
#
# Usage:
#	make mvn [task=]
task ?=

maven:
	docker run \
		--rm \
		--name maven-worker \
		-u 1000 \
		-e MAVEN_CONFIG=/var/maven/.m2 \
		-v $(PWD):/usr/src/mymaven \
		-v $(PWD)/.m2:/var/maven/.m2 \
		-w /usr/src/mymaven \
		maven:alpine \
		mvn -Duser.home=/var/maven $(task)

# clean command
maven.clean:
	@make maven task='clean'

# deps command
maven.deps:
	@make maven task='dependency:go-offline'

# build command
maven.build:
	@make maven task='package'

# docs command
maven.docs:
	@make maven task='javadoc:javadoc'

# test command
maven.test:
	@make maven task='test jacoco:report'


####################
# Node.js commands #
####################

# Resolve Yarn project dependencies.
#
# Optional 'cmd' parameter may be used for handy usage of docker-wrapped Yarn,
# for example: make yarn.deps cmd='upgrade'
#
# Usage:
#	make yarn.deps [cmd=('install --pure-lockfile'|<yarn-cmd>)]

yarn-deps-cmd = $(if $(call eq,$(cmd),),install --pure-lockfile,$(cmd))

yarn.deps:
	docker run \
		--rm \
		-v "$(PWD)":/app -w /app \
		-e YARN_CACHE_FOLDER=/app/_cache/yarn \
		node \
			yarn $(yarn-deps-cmd) --non-interactive




###################
# Docker commands #
###################

# Stop project in Docker Compose development environment
# and remove all related containers.
#
# Usage:
#	make docker.down

docker.down:
	docker-compose down --rmi=local -v

# Run Docker Compose development environment.
#
# Usage:
#	make docker.up [rebuild=(yes|no)]
#	               [background=(no|yes)]

rebuild ?= yes
background ?= no

docker.up: docker.down
	docker-compose up \
		$(if $(call eq,$(rebuild),no),,--build) \
		$(if $(call eq,$(background),yes),-d,--abort-on-container-exit)




.PHONY: squash \
		clean deps build docs up down \
		maven maven.clean maven.docs maven.build maven.deps \
		yarn.deps \
		docker.up docker.down
