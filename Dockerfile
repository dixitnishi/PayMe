FROM ubuntu:latest
LABEL authors="nishi"

ENTRYPOINT ["top", "-b"]