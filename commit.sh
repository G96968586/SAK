#!/bin/bash
# desc: git add and commit to local storage

# commit message
comment=$1

git add . && git commit -m $1
