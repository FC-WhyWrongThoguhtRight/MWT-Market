# MWT Market

## 로컬에서 스프링부트 구동하는 방법

아래 명령어로 MongoDB 서버를 20719 포트로 띄운 뒤 구동하시면 됩니다.
```shell
docker run -d --name my-mongodb \
  -p 27019:27017 \
  -e MONGO_INITDB_DATABASE=MWT_MARKET \
  -e MONGO_INITDB_ROOT_USERNAME=username \
  -e MONGO_INITDB_ROOT_PASSWORD=password \
  mongo
```
