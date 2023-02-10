package com.wgdetective.pactexample.musicgrant.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class SongAlreadyExistsException extends RuntimeException {
}
