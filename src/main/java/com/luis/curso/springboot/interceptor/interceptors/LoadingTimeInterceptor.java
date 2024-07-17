package com.luis.curso.springboot.interceptor.interceptors;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("loadingTimeInterceptor")
public class LoadingTimeInterceptor implements HandlerInterceptor {

	private static final Logger log = LoggerFactory.getLogger(LoadingTimeInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod controller = ((HandlerMethod) handler);
		log.info("LoadingTimeInterceptor: preHandler() entrando..." + controller.getMethod().getName());
		long start = System.currentTimeMillis();
		request.setAttribute("start", start);

		Random random = new Random();
		int dealy = random.nextInt(500);
		Thread.sleep(dealy);

		/**
		 * Codigo para manejo del request cuando se devuelve un false
		 */
//		Map<String, String> json = new HashMap<>();
//		json.put("error", "No tienes acceso a este recurso");
//		json.put("date", new Date().toString());
//
//		ObjectMapper mapper = new ObjectMapper();
//		String jsonString = mapper.writeValueAsString(json);
//
//		response.setContentType("application/json");
//		response.setStatus(401);
//		response.getWriter().write(jsonString);
//
//		return false;
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerMethod controller = ((HandlerMethod) handler);
		long end = System.currentTimeMillis();
		long start = (long) request.getAttribute("start");

		long result = end - start;

		log.info("Tiempo transcurrido: " + result + " milisegundos");
		log.info("LoadingTimeInterceptor: postHandler() saliendo..." + controller.getMethod().getName());
	}

}
