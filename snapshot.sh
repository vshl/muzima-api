#!/bin/sh
mvn -DskipTests -DaltDeploymentRepository=snapshot-repo::default::file:../muzima-maven/snapshots deploy
