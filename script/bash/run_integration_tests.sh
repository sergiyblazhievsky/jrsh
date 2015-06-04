#!/bin/bash
cd ../..
mvn install -DrunSuite=**/IntegrationTestSuit.class -DfailIfNoTests=false