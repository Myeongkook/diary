package com.coogie.diary.controller;

import com.coogie.diary.dto.Message;
import com.coogie.diary.entity.Member;
import com.coogie.diary.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    HttpHeaders httpHeaders = new HttpHeaders();

    public MemberController(MemberService memberService){
        this.memberService = memberService;
        this.httpHeaders.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
    }

    @PostMapping("")
    public ResponseEntity signupUser(@RequestBody Member member){
        try {
            memberService.saveMember(member);
            return new ResponseEntity(new Message(HttpStatus.OK, "success", true), httpHeaders, HttpStatus.OK);
        }catch (DuplicateKeyException e){
            return new ResponseEntity(new Message(HttpStatus.BAD_REQUEST, e.getMessage(), false), httpHeaders, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), false), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/check/{info}")
    public ResponseEntity checkDuplicateInfo(@PathVariable("info")String info){
        try {
            memberService.checkUserId(info);
            return new ResponseEntity(new Message(HttpStatus.OK, "success", true), httpHeaders, HttpStatus.OK);
        }catch (DuplicateKeyException e){
            return new ResponseEntity(new Message(HttpStatus.BAD_REQUEST, e.getMessage(), false), httpHeaders, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), false), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/send")
    public ResponseEntity sendAuthKey(@RequestParam("phone")String phone){
        try {
            memberService.sendAuthKeyBySms(phone);
            return new ResponseEntity(new Message(HttpStatus.OK, "success", true), httpHeaders, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), false), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/auth")
    public ResponseEntity authenticationKey(@RequestParam("key")String key){
        try {
            memberService.findByAuthKey(key);
            return new ResponseEntity(new Message(HttpStatus.OK, "success", true), httpHeaders, HttpStatus.OK);
        }catch (IllegalAccessException e){
            return new ResponseEntity(new Message(HttpStatus.BAD_REQUEST, e.getMessage(), true), httpHeaders, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), false), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
