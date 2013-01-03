#!/bin/sh -e

echo "Preparing release..."
mvn release:clean release:prepare

echo "Performing release..."
mvn release:perform -Darguments=-Dgpg.passphrase=

echo "Updating site..."
mvn site -DdeployGitHubSite=true

echo "*** Release completed successfully ***"
