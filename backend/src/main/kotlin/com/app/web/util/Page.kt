package com.app.web.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.ALWAYS)
data class Page<T>(
    @JsonProperty("data")
    val data: List<T>
)