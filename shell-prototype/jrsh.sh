#!/bin/bash

CLI_NAME="jrsh"

function help(){
cat  <<XXX
  ${CLI_NAME} usage advice

  Parameters:
    -username  <JRS username>
    -p <JRS password>
    -o <organization name>
    -s <JRS url>

  Actions:
     help
     version
     import
     export
              all
              repositories
              users
              roles
              events
                     access
                     audit
                     monitoring
XXX
}

help
