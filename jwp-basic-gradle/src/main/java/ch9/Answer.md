#### 1. 프로젝트의 초기화 과정
1. 톰캣 실행
2. webapp 폴더 스캔(?)
3. @WebServlet이나 @WebFilter 등 어노테이션 읽고 설정 정보 저장
4. 애플리케이션 하나당 하나의 ServletContext 객체 생성
5. 애노테이션을 통해 등록된 리스너 실행(ContextLoaderListener 같은 것)
   - contextInitialized() 메서드 호출
6. `@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)` loadOnStartUp 값이 지정되어 있기에, 서블릿 컨테이너가 시작하는 시점에 인스턴스를 생성
7. `init()` 메서드 호출로 초기화 작업 실행


#### 2. `http://localhost:8080`으로 접근해서 질문 목록이 보이기까지 소스코드의 호출 순서 및 흐름
1. 요청으로 들어온 url이 필터과정을 거친다 (ResourceFilter, CharacterEncodingFilter)
2. 정적 자원 요청이 아니기에 서블릿으로 요청을 위임
3. / 으로 매핑되어 있는 DispatcherServlet으로 이동
4. DispatcherServlet이 해당 url에 매핑되는 Controller가 있는지 RequestMapping을 확인
5. HomeController가 있으니, 이동 후 QuestionDao(jdbc)로 question 목록 가져오기
6. HttpServletResponse에 가져온 목록을 set하고 jsp로 출력


