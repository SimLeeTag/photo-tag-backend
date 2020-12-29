#!/usr/bin/env bash

# 쉬고 있는 profile 찾기: deploy1 사용중 -> deploy2 쉬는중
function find_idle_profile() {
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

  if [ ${RESPONSE_CODE} -ge 400 ] # 400보다 크면 => 40x, 50x
  then
    CURRENT_PROFILE=deploy2
  else
    CURRENT_PROFILE=$(curl -s http://localhost/profile)
  fi

  if [ ${CURRENT_PROFILE} == deploy1 ]
  then
    IDLE_PROFILE=deploy2
  else
    IDLE_PROFILE=deploy1
  fi

  echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile port 찾기
function find_idle_port() {
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == deploy1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}
