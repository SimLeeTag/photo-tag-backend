#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ec2-user/app/photo-tag-backend
PROJECT_NAME=phog

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/zip/build/libs/*.jar $REPOSITORY/jar/"
cp $REPOSITORY/zip/build/libs/*.jar $REPOSITORY/jar/

echo "> 새 어플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/jar/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> JAR_NAME에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

echo "> $JAR_NAME을 profile=$IDLE_PROFILE 실행"
nohup java -jar \
    -Dspring.config.location=classpath:/application.properties,classpath:/application-$IDLE_PROFILE.properties \
    -Dspring.profiles.active=$IDLE_PROFILE \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
