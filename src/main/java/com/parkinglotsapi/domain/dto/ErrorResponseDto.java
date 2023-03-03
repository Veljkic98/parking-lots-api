package com.parkinglotsapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

@Getter
public class ErrorResponseDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE MMM dd HH:mm:ss zzz yyyy")
    private final Date date;

    @JsonIgnore
    private final HttpStatus httpStatus;

    private final int status;

    private final String error;

    private final String message;

    private final String path;

    public ErrorResponseDto(HttpServletRequest request, String message, HttpStatus httpStatus) {
        this.date = new Date();
        this.httpStatus = httpStatus;
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.path = extractPath(request);
    }

    private String extractPath(HttpServletRequest request) {
        return ObjectUtils.firstNonNull(
                Objects.toString(request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE), null),
                request.getServletPath()
        );
    }

}
