//package com.epam.esm.handler;
//
//import com.epam.esm.handler.entity.CustomMessage;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
///**
// * Controller that handles server error
// *
// * @author Denis Davydov
// * @version 2.0
// */
//@Controller
//public class CustomErrorController implements ErrorController {
//
//    @GetMapping("/error")
//    public ResponseEntity<CustomMessage> handleError() {
//        CustomMessage customException = new CustomMessage();
//        customException.setErrorMessage("Bad Request");
//        customException.setErrorCode(400);
//        return new ResponseEntity<>(
//                customException, new HttpHeaders(),
//                HttpStatus.BAD_REQUEST);
//    }
//
//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }
//}