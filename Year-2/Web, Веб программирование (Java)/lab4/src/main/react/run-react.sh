#!/bin/bash
podman kill --all && podman build . -t dockerized-react && podman run -p 3000:3000 -d dockerized-react
