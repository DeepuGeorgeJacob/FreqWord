#!/bin/bash
awslocal s3api create-bucket \
--bucket freqwordbucket \
--create-bucket-configuration LocationConstraint=eu-central-1