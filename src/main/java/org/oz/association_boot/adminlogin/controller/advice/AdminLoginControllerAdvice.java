package org.oz.association_boot.adminlogin.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.adminlogin.dto.ErrorResponseDTO;
import org.oz.association_boot.adminlogin.exception.AdminLoginTaskException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class AdminLoginControllerAdvice {

    @ExceptionHandler(AdminLoginTaskException.class)
    public ResponseEntity<ErrorResponseDTO> ex(AdminLoginTaskException ex) {
        log.error("============================================");
        StackTraceElement[] arr = ex.getStackTrace();
        for (StackTraceElement ste : arr) {
            log.error(ste.toString());
        }
        log.error("============================================");

        // ErrorResponseDTO에 상태 코드와 메시지를 담아서 반환
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setStatus(ex.getStatus());
        errorResponse.setMessage(ex.getMessage());

        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }
}
