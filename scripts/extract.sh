#!/usr/bin/env bash
set -e


extract() {
    local FULL_FILENAME=$1
    local FULL_FILENAME_NO_EXT="${FULL_FILENAME%.*}"
    local LOG_FILENAME="${FULL_FILENAME_NO_EXT}".log.txt
    local EXTRACTED_FILENAME="${FULL_FILENAME_NO_EXT}".extracted.txt

    echo "extracting from: $FULL_FILENAME"
    echo "log file: $LOG_FILENAME"
    echo "extracted file: $EXTRACTED_FILENAME"

    java -jar target/simpleExtractor-bundled-0.0.1-SNAPSHOT.jar "$FULL_FILENAME" > "$EXTRACTED_FILENAME" 2> "$LOG_FILENAME"
}


SRCS="target"

[ -d "$SRCS" ] || (echo "Run this script from project root"; exit 1)

if [ "$#" -lt 1 ]; then
    echo "Illegal number of parameters: "
    echo $0 FILE
    exit -1
fi

for var in "$@"
do
    extract "$var"
done


