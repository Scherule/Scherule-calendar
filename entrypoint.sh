#!/bin/bash
echo Your container args are: "$@"
echo "Running $@"
exec java -jar ${ARTIFACT_NAME} $1 "$@"