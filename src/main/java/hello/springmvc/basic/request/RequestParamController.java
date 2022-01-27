package hello.springmvc.basic.request;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RequestParamController {
	
	@RequestMapping("/request-param-v1")
	public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		int age = Integer.parseInt(request.getParameter("age"));
		log.info("username={}, age={}", username, age);
		
		response.getWriter().write("ok");
	}
	
	@ResponseBody
	@RequestMapping("/request-param-v2")
	public String requestParamV2(
			@RequestParam("username") String memberName,
			@RequestParam("age") int memberAge) {
		
		log.info("username={}, age={}", memberName, memberAge);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/request-param-v3")
	public String requestParamV3(
			@RequestParam String username,
			@RequestParam int age) {
		
		log.info("username={}, age={}", username, age);
		return "ok";
	}

	@ResponseBody
	@RequestMapping("/request-param-v4")
	public String requestParamV4(String username, int age) {	// 해당 파라미터 필수 아님
		log.info("username={}, age={}", username, age);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/request-param-required")
	public String requestParamRequired(
			@RequestParam(required = true) String username,		// ?username= 시 빈문자가 들어옴
			@RequestParam(required = false) Integer age) {		// int 설정 시 null 이 들어오면 500 에러
		
		log.info("username={}, age={}", username, age);
		return "ok";
	}

	@ResponseBody
	@RequestMapping("/request-param-default")
	public String requestParamDefualt(
			@RequestParam(required = true, defaultValue = "guest") String username,	// 빈문자여도 defaultValue 를 사용
			@RequestParam(required = false, defaultValue = "-1") Integer age) {
		
		log.info("username={}, age={}", username, age);
		return "ok";
	}

	@ResponseBody
	@RequestMapping("/request-param-map")
	public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
		
		log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/model-attribute-v1")
	public String modelAttributeV1(@ModelAttribute HelloData helloData) {
		log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
		log.info("helloDate={}", helloData);
		return "ok";
	}
	
	/*
	 * @ModelAttribute 생략 가능
	 * 그런데 @RequestParam 도 생략 가능
	 * 스프링 규칙
	 * 	- String, int, Integer 같은 단순 타입 = @RequestParam
	 *  - 나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외)
	 */
	@ResponseBody
	@RequestMapping("/model-attribute-v2")
	public String modelAttributeV2(HelloData helloData) {
		log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
		return "ok";
	}
	
}
