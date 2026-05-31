package com.whisperspace.Whisperspace_backend.conversation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConvoReqRes {
    private String convoname;
    private String createdby;
    private Long convoid;

}
//this is for finding pending requests