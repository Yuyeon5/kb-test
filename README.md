# kb-test
## 사용한 라이브러리
비동기 처리를 위해 webflux 를 사용하였습니다.
테스트/문서화를 위해 springdoc (swagger) 를 사용하였습니다.

## 테스트 방법
먼저 ./gradlew bootRun 명령을 사용하여 서버를 실행합니다.
1. curl 로 테스트를 할 수 있습니다.

```
curl -X 'GET' \
  'http://localhost:8080/v1/place?q=pizza' \
  -H 'accept: */*'
```

```
curl -X 'GET' \
  'http://localhost:8080/v1/keywords' \
  -H 'accept: */*'
```

2. 스웨거로 접속하여 테스트를 할 수 있습니다.

```
http://localhost:8080/swagger-ui.html
```

## 요구사항에 대한 설명
1. 동시성 이슈

Java ConcurrentHashMap 을 사용하여 별도의 Lock 없이도 키워드 카운트를 증가시킬 수 있습니다.
다만, 10개의 Top 키워드를 관리하는 부분은 synchronized 블럭으로 처리하였습니다.

2. 검색 API 제공자의 다양한 장애 발생 상황에 대한 고려 

검색 API 호출 시에 4XX, 5XX, 타임아웃 등에 대해서는 전체 에러가 아닌 빈 응답으로 처리하였습니다. 그러므로 장애가 발생하지 않은 다른 응답들은 정상적으로 처리가 됩니다.

3. 새로운 검색 API 제공자의 추가 시 변경 영역 최소화에 대한 고려

다른 검색 API 제공자가 추가되어도 클라이언트 부분을 추가하고, 정렬 순서를 정해주기만 하면 큰 수정없이 동작이 가능합니다.

4. 대용량 트래픽 처리를 위한 반응성(Low Latency), 확장성(Scalability), 가용성(Availability)을 높이기 위한 고려

비동기 처리를 위해서 webflux 기술을 사용하였습니다. 기존의 기술로는 블로킹되는 시간이 있고, 순차적으로 처리가 되지만 webflux 는 그런 낭비 없이 비동기로 처리를 할 수가 있습니다.
다만, 확장을 위해서는 키워드 데이터를 내부 변수보다는 외부 DB 에 저장하는 것이 좋을 것으로 예상됩니다.

5. 지속적 유지 보수 및 확장에 용이한 아키텍처에 대한 설계

가능하면 클라이언트 부분과 코어 부분을 분리하려고 노력하였습니다. 
감사합니다.
