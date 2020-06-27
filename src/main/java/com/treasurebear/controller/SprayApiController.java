package com.treasurebear.controller;

import com.treasurebear.code.ErrorCode;
import com.treasurebear.domain.*;
import com.treasurebear.service.SplitService;
import com.treasurebear.service.SprayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SprayApiController {

    private final SprayService sprayService;
    private final SplitService splitService;

    /**
     * 뿌리기 API
     * @param userId
     * @param roomId
     * @param requestDto
     * @param error
     * @return
     * @throws IllegalAccessException
     */
    @PostMapping("/spray")
    public SprayResponseDto spray(@RequestHeader("X-USER-ID") Long userId,
                                  @RequestHeader("X-ROOM-ID") String roomId,
                                  @RequestBody @Valid SprayRequestDto requestDto,
                                  BindingResult error) throws IllegalAccessException {
        if(userId == null || !StringUtils.hasText(roomId)) throw new IllegalAccessException("사용자 정보가 없습니다.");
        if(error.hasErrors()) throw new IllegalArgumentException();

        User user = User.builder().roomId(roomId).userId(userId).build();
        Spray spray = sprayService.createSpray(user, requestDto);

        SprayResponseDto responseDto = SprayResponseDto.builder()
                .resultCode(ErrorCode.OK)
                .token(spray.getToken())
                .build();

        return responseDto;
    }

    /**
     * 받기 API
     * @param userId
     * @param roomId
     * @param token
     * @return
     * @throws IllegalAccessException
     * @throws CloneNotSupportedException
     */
    @GetMapping("/get/{token}")
    public SplitGetResponseDto get(@RequestHeader("X-USER-ID") Long userId,
                                   @RequestHeader("X-ROOM-ID") String roomId,
                                   @PathVariable String token) throws IllegalAccessException, CloneNotSupportedException {
        if(userId == null || !StringUtils.hasText(roomId)) throw new IllegalAccessException("사용자 정보가 없습니다.");
        if(!StringUtils.hasText(token)) throw new IllegalArgumentException();

        User receiveUser = User.builder().roomId(roomId).userId(userId).build();

        Split split = splitService.getSplit(receiveUser, token);
        return SplitGetResponseDto.builder()
                .resultCode(ErrorCode.OK)
                .receiveMoney(split.getMoney())
                .build();
    }

    /**
     * 조회 API
     * @param userId
     * @param roomId
     * @param token
     * @return
     */
    @GetMapping("/list/{token}")
    public SprayQueryDto list(@RequestHeader("X-USER-ID") Long userId,
                            @RequestHeader("X-ROOM-ID") String roomId,
                            @PathVariable String token) {
        User user = User.builder().roomId(roomId).userId(userId).build();
        SprayQueryDto sprayQueryDto = splitService.sprayAllInfo(user, token);
        sprayQueryDto.setResultCode(ErrorCode.OK);

        return sprayQueryDto;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDto handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseDto(ErrorCode.INVALID_REQUEST);
    }

    @ExceptionHandler(IllegalAccessException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDto handleIllegalAccessException(IllegalAccessException e) {
        return new ResponseDto(ErrorCode.INVALID_USER);
    }

    @ExceptionHandler(CloneNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDto handleCloneNotSupportedException(CloneNotSupportedException e) {
        return new ResponseDto(ErrorCode.CANNOT_DUPLICATE);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDto handleException(Exception e) {
        return new ResponseDto(ErrorCode.ERROR);
    }

}
