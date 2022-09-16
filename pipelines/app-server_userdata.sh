#!/bin/bash
sudo apt-get update
sudo apt-get upgrade -y
sudo apt-get install docker.io git -y
sudo usermod -aG docker ubuntu
sudo reboot