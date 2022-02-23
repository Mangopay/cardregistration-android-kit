#!/bin/bash

echo -e "Starting publish to Sonatype...\n"

./gradlew publish -PnexusUsername="${MAVEN_USERNAME}" -PnexusPassword="${MAVEN_PASSWORD}" -Psigning.keyId=8F2AE56A -Psigning.password="${SIGNING_PASSWORD}" -Psigning.secretKeyRingFile=.gnupg/secring.gpg