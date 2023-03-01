#!/bin/sh
#  Start parameters:
#  1.-fileConfig
#  Example: ./build_Docker.sh ../conf/.env.dev

# Init variables
fileConfig=$1;
now="$(date +'%d-%m-%Y %H:%M:%S:%3N')"
base_dir=`pwd`

# Load environment variables
if [ -f "$fileConfig" ]
then
  echo ""
  echo " Load config from $fileConfig"
  echo ""

  while IFS='=' read -r key value
  do
    key=$(echo $key | tr '.' '_')
    eval ${key}='${value}'
  done < "$fileConfig"

  echo " Date: "${now}
  echo " ======================================================"
  echo ""
  echo "                 Initializing variables "
  echo ""
  echo " ======================================================"
  echo ""
  echo " GIT_BRANCH="${GIT_BRANCH}
  echo " REGISTRY_NAME="${REGISTRY_NAME}
  echo " NGINX_FRONTEND_TAG="${NGINX_FRONTEND_TAG}
  echo " NGINX_CONTAINER_PORT="${NGINX_CONTAINER_PORT}
  echo " FRONTEND_PROJECT_DIR="${FRONTEND_PROJECT_DIR}
  echo ''

else
  echo "$fileConfig not found."
fi

#
cd ${FRONTEND_PROJECT_DIR}

git checkout ${GIT_BRANCH}
ng build --configuration production
cp -r dist ${base_dir}
sleep 5
cd ${base_dir}
docker build -t=${REGISTRY_NAME}:nginx-frontend2-${NGINX_FRONTEND_TAG} .
