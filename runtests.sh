#!/bin/bash
#
# Copyright (c) 2021 Eclipse Foundation and/or its affiliates. All rights reserved.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Public License v. 2.0, which is available at
# http://www.eclipse.org/legal/epl-2.0.
#
# This Source Code may also be made available under the following Secondary
# Licenses when the conditions for such availability set forth in the
# Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
# version 2 with the GNU Classpath Exception, which is available at
# https://www.gnu.org/software/classpath/license.html.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
#

set -e

echo "This script can be temporarily used for local testing until we integrate current set of tests to Maven";
echo "First argument is a version of GlassFish used for testing. It will be downloaded by Maven command";
echo "Second argument is a test set id, one of:
cdi_all, ql_gf_full_profile_all, \n
\n
web_jsp, deployment_all, ejb_group_1
ejb_group_2, ejb_group_3, ejb_web_all, cdi_all, ql_gf_full_profile_all, ql_gf_nucleus_all, \
ql_gf_web_profile_all, nucleus_admin_all, jdbc_all, batch_all, persistence_all, \
connector_group_1, connector_group_2, connector_group_3, connector_group_4";

echo "If you need to use a different JAVA_HOME than is used by your system, export it or edit this script.";
echo "";

install_glassfish() {
  mvn clean -N org.apache.maven.plugins:maven-dependency-plugin:3.2.0:copy \
  -Dartifact=org.glassfish.main.distributions:glassfish:${GF_VERSION}:zip \
  -Dmdep.stripVersion=true \
  -DoutputDirectory=${WORKSPACE}/bundles
}

install_jacoco() {
  mvn -N org.apache.maven.plugins:maven-dependency-plugin:3.2.0:copy \
  -Dartifact=org.jacoco:org.jacoco.agent:0.8.7:jar:runtime \
  -Dmdep.stripVersion=true \
  -Dmdep.stripClassifier=true \
  -DoutputDirectory=${WORKSPACE}/bundles
}

####################################

export GF_VERSION="$1"
export JACOCO_ENABLED="true"
test="${2}"

# uncomment and edit this line if your system uses another version.
export JAVA_HOME=/usr/lib/jvm/jdk11

if [ -z "${GF_VERSION}" ]
  then echo "No version supplied."
  exit 1;
fi
if [ -z "${test}" ]
  then echo "No test supplied."
  exit 2;
fi

export WORKSPACE="$(pwd)/target"
export TEST_RUN_LOG="${WORKSPACE}/tests-run.log"
export CLASSPATH="${WORKSPACE}/glassfish6/javadb"
export APS_HOME="$(pwd)/appserver/tests/appserv-tests"
export S1AS_HOME="${WORKSPACE}/glassfish6/glassfish"
export PORT_ADMIN="4848"
export PORT_HTTP="8080"
export PORT_HTTPS="8181"
install_glassfish;
install_jacoco;

./appserver/tests/gftest.sh run_test "${test}"

