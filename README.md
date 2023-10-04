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

## 컨벤션
### 코딩컨벤션 플러그인 IntelliJ에 적용하기

1. IntelliJ 플러그인 'Checkstyle'을 설치합니다.
2. IntelliJ 설정에서 'Checkstyle'을 검색하고, 'Tools -> Checkstyle'로 들어갑니다.
3. Configuration File에 프로젝트 최상위 폴더의 custom_google_checks.xml을 추가합니다.
4. 추가한 Configuration을 체크한 뒤 OK를 누릅니다.
5. 설정 완료
- 이제 Checkstyle 플러그인을 통해 구글 코딩컨벤션 지켰는지 여부를 확인하실 수 있습니다. (노란 밑줄이 뜸)
- 좌측 하단 연필 아이콘으로 돼있는 Checkstyle 플러그인 아이콘을 클릭하고, 재생 아이콘 아래아래에 있는 Check Project 버튼으로 일괄 확인도 가능합니다.

### (권장) 파일 맨 마지막 줄 newline 자동 추가

파일 맨 마지막 줄에 newline이 없을 경우 생길 수 있는 문제
- https://jojoldu.tistory.com/673

파일 맨 마지막 줄 newline 자동 추가 설정하는 법
- https://jojoldu.tistory.com/673 
