package com.ssafy.mylifencut.user.controller;

import static com.ssafy.mylifencut.user.UserConstant.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.mylifencut.common.dto.BaseResponse;
import com.ssafy.mylifencut.user.JwtTokenProvider;
import com.ssafy.mylifencut.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/login")
	public ResponseEntity<BaseResponse> kakaoLogin(@RequestBody String accessToken) {
		JsonElement element = JsonParser.parseString(accessToken);
		accessToken = element.getAsJsonObject().get("accessToken").getAsString();
		String jwtToken = jwtTokenProvider.createToken(Integer.toString(userService.kakaoLogin(accessToken)));
		return new ResponseEntity<>(BaseResponse.from(true, KAKAO_LOGIN_SUCCESS_MESSAGE, jwtToken), HttpStatus.OK);
	}
}
