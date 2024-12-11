package org.oz.association_boot.security.filter;


import com.google.gson.Gson;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.adminlogin.exception.AdminLoginException;
import org.oz.association_boot.security.auth.AdminLoginPrincipal;
import org.oz.association_boot.security.util.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class JWTCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        log.info("----Should NOT Filter----");

        String uri = request.getRequestURI();
        // Kafka테스트 uri
        if(uri.equals("/send")){
            log.info("----카프카 지나가용----");
            return true;
        }
        if(uri.equals("/api/association/login/makeToken")){
            return true;
        }
        if(uri.equals("/api/association/login/refreshToken")){
            return true;
        }
        if(uri.equals("/api/association/applier/registry")){
            return true;
        }
        if(uri.equals("/api/association/applier/auth")){
            return true;
        }
        if(uri.equals("/api/association/applier/auth/email")){
            return true;
        }
        if(uri.equals("/api/association/applier/auth/emailchk")){
            return true;
        }
        if(uri.startsWith("/api/board")){
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("doFilterInternal");

        log.info(request.getRequestURI());

        String authHeader = request.getHeader("Authorization");

        String token = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
        }else {
            makeError(response, Map.of("status",401, "msg","No Access Token") );
            return;
        }

        //JWT validate
        try{

            Map<String, Object> claims = jwtUtil.validateToken(token);
            log.info(claims);

            String adminId = (String) claims.get("adminId");

            Principal userPrincipal = new AdminLoginPrincipal(adminId);

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userPrincipal, null,
                    List.of());

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);

        }catch(JwtException e){
            AdminLoginException exception = AdminLoginException.ACCESSTOKEN_EXPIRED;
            String errorMessage = exception.getMessage();
            int statusCode = exception.getStatus();
            log.info("STATUS CODE:::: " + statusCode);
            makeError(response, Map.of("status", statusCode, "msg", errorMessage));

            e.printStackTrace();
        }
    }

    private void makeError(HttpServletResponse response, Map<String, Object> map) {
        log.info("=====================makeError========================");

        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);

        response.setContentType("application/json");
        response.setStatus((int)map.get("status"));
        try{
            PrintWriter out = response.getWriter();
            out.println(jsonStr);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private void makeError(HttpServletResponse response, Map<String, Object> map) {
//        try {
//            // Jackson의 ObjectMapper를 사용하여 Map을 JSON 문자열로 변환
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonStr = objectMapper.writeValueAsString(map);
//
//            // 응답의 Content-Type을 JSON으로 설정
//            response.setContentType("application/json");
//            response.setStatus((int) map.get("status"));
//
//            // PrintWriter로 JSON 출력
//            try (PrintWriter out = response.getWriter()) {
//                out.println(jsonStr);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
