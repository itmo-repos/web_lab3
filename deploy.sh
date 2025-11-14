#!/bin/bash

echo "In process..."

mvn install

ssh helios "rm -rf wildfly-26.1.3/standalone/deployments/lab3-1.0.war"

scp ./target/lab3-1.0.war helios:wildfly-26.1.3/standalone/deployments

ssh \
  -L 46704:localhost:46704 \
  -L 46702:localhost:46702 \
  helios
