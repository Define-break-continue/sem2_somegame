#!/usr/bin/env bash
mvn compile assembly:single
mv ./target/Java_server.jar ../
