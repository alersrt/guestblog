###########################
### Git Section
###########################

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

###########################
### Docker section
###########################

# Maven command.
#
# Usage:
#	make mvn [task=]
task =?

mvn:
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

#clean command
clean:
	@make mvn task='clean'

# build command
build:
	@make mvn task='package'

# docs command
docs:
	@make mvn task='javadoc:javadoc'

.PHONY: clean docs build squash
