#!/bin/bash

echo -e "Starting publish to Sonatype...\n"

./gradlew publish -PnexusUsername="${MAVEN_USERNAME}" -PnexusPassword="${MAVEN_PASSWORD}" -Psigning.keyId=93605ADB -Psigning.password="${SIGNING_PASSWORD}" -Psigning.secretKeyRingFile=.utility/signingkey.gpg