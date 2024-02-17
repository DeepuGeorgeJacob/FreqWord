#!/bin/bash

folder_path="text"

# Use the find command to get a list of all files in the folder
files=$(find "$folder_path" -type f)

# Iterate over each file using a for loop
for file in $files; do
  echo "Processing file: $file"
  aws s3 cp "./$file" s3://freqwordbucket --endpoint-url http://localhost:4566
done