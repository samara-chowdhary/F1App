package com.example.f1app

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

//session data set
@JsonIgnoreProperties(ignoreUnknown = true)
class Session {
    @JsonProperty("session_key")
    var sessionKey: Int = 0

    @JsonProperty("meeting_key")
    var meetingKey: Int = 0

    @JsonProperty("session_name")
    var sessionName: String? = null

    @JsonProperty("session_type")
    var sessionType: String? = null

    @JsonProperty("date_start")
    var dateStart: String? = null
}